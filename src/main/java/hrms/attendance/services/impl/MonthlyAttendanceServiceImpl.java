package hrms.attendance.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import hrms.attendance.dto.GenericResponse;
import hrms.attendance.dto.MonthlyAttendanceDto;
import hrms.attendance.entity.MonthlyAttedance;
import hrms.attendance.enums.MonthName;
import hrms.attendance.exception.CSVErrorException;
import hrms.attendance.repository.MonthlyAttendanceRepository;
import hrms.attendance.rest.RestEmployeeService;
import hrms.attendance.services.MonthlyAttendanceService;
import hrms.attendance.utils.Keys;
import hrms.attendance.utils.MonthUtils;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MonthlyAttendanceServiceImpl implements MonthlyAttendanceService {

	private final Keys keys;
	private final MonthlyAttendanceRepository monthlyAttendanceRepository;
	private final RestEmployeeService restEmployeeService;

	@Override
	public List<MonthlyAttendanceDto> monthlyAttendance(Integer month) {
		final MonthName monthName = month != null && month != 0 ? MonthUtils.convertMonthName(month)
				: MonthUtils.getCurrentMonthName(1);
		List<MonthlyAttedance> findByMonthName = monthlyAttendanceRepository.findByMonthName(monthName);
		return findByMonthName.stream().map(MonthlyAttendanceDto::new).toList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public MonthlyAttedance saveMonthlyAttedance(MonthlyAttendanceDto dto) throws Exception {
		MonthlyAttedance attedance = new MonthlyAttedance(dto);

		GenericResponse employeeData = restEmployeeService.getEmployeById(attedance.getEmployeeId());

		Map<String, Object> employee = (Map<String, Object>) employeeData.getData();

		Map<String, Object> personalDetails = (Map<String, Object>) employee.get("personalDetails");

		String fullName = personalDetails.get("fullName").toString();

		if (!fullName.equalsIgnoreCase(attedance.getEmployeeName())) {
			throw new Exception("Employee name invalid, The right name is " + fullName);
		}

		// check duplicate
		List<MonthlyAttedance> findData = this.monthlyAttendanceRepository
				.findByEmployeeIdAndMonthName(attedance.getEmployeeId(), attedance.getMonthName());
		if (!findData.isEmpty())
			throw new Exception("Allready Uploaded attendance data for Employee " + attedance.getEmployeeName()
					+ " and month " + attedance.getMonthName());

		// save to the database
		return this.monthlyAttendanceRepository.save(attedance);
	}

	@Override
	public String uploadMonthlyAttendance(InputStream inputStream) {

		StringBuffer errorTxt = new StringBuffer();

		try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
			String[] headers = Arrays.stream(keys.getMonthlyCsvHeader()).map(String::trim).toArray(String[]::new);

			CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(headers).setIgnoreHeaderCase(true).setTrim(true)
					.build();
			CSVParser csvParser = csvFormat.parse(bufferedReader);

			Iterable<CSVRecord> iterable = csvParser.getRecords();
			int rowCount = 0;
			for (CSVRecord record : iterable) {
				rowCount++;
				if (rowCount == 1) {
					// Validate headers
					try {
						validateHeaders(record.toMap());
						continue;
					} catch (CSVErrorException e) {
						// closed all resources
						errorTxt.append("\t" + e.getMessage());
						csvParser.close();
						bufferedReader.close();
						return errorTxt.toString();
					}
				} // header check end

				try {
					// read data
					final MonthlyAttendanceDto dto = csvToMonthlyAttendanceDto(record);
					// save data into database
					saveMonthlyAttedance(dto);
				} catch (Exception ex) {
					errorTxt.append("\t Data  Error, row = " + (rowCount - 1) + " : " + ex.getMessage() + "\n");
				}

			} // loop closed

		} catch (IOException exception) {
			exception.printStackTrace();
			errorTxt.append("\t" + exception.getMessage()
					+ " :  If you are face same issue multiple time contact Administrator");
		}

		return errorTxt.toString();
	}

	private MonthlyAttendanceDto csvToMonthlyAttendanceDto(CSVRecord record) throws Exception {

		MonthlyAttendanceDto dto = new MonthlyAttendanceDto();

		String employeeId = record.get("Employee Id");
		dto.setEmployeeId(Long.parseLong(employeeId));

		String employeeName = record.get("Employee Name");
		dto.setEmployeeName(employeeName);

		String monthName = record.get("Month Name");
		dto.setMonthName(MonthUtils.convertMonthName(monthName));

		String publicHolidays = record.get("Total Public Holiday");
		dto.setPublicHoliday(Integer.parseInt(publicHolidays));

		String workingDays = record.get("Total Working Day");
		dto.setTotalWorkingDay(Integer.parseInt(workingDays));

		String paidLeave = record.get("Paid Leave");
		dto.setPaidLeave(Integer.parseInt(paidLeave));

		String employeeWorkingDay = record.get("Employee Working Day");
		dto.setEmployeeWorkingDay(Integer.parseInt(employeeWorkingDay));

		return dto;

	}

	@Override
	public List<String> uploadCsvHeaders() {
		return List.of(keys.getMonthlyCsvHeader()).stream().map(String::trim).toList();
	}

	private void validateHeaders(Map<String, String> actualHeaders) throws CSVErrorException {
		// Check if headers are present and compare them with expected headers
		if (actualHeaders == null || !actualHeaders.keySet()
				.equals(actualHeaders.values().stream().map(String::trim).collect(Collectors.toSet()))) {
			throw CSVErrorException.builder()
					.message("CSV file headers do not match the expected headers " + actualHeaders.keySet()).build();
		}
	}
}
