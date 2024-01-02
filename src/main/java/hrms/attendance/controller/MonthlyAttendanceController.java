package hrms.attendance.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import hrms.attendance.dto.GenericResponse;
import hrms.attendance.dto.MonthlyAttendanceDto;
import hrms.attendance.exception.CSVErrorException;
import hrms.attendance.services.MonthlyAttendanceService;
import hrms.attendance.utils.CSVHelper;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequestMapping("attendance/monthly")
@RequiredArgsConstructor
public class MonthlyAttendanceController {

	private final MonthlyAttendanceService monthlyAttendanceService;

	@GetMapping("/{monthValue}")
	public GenericResponse monthlyAttendance(@PathVariable(required = false) Integer monthValue) throws Exception {

		MonthlyAttendanceDto dto = new MonthlyAttendanceDto();

		dto.setEmployeeId(1L);
		monthlyAttendanceService.saveMonthlyAttedance(dto);

		List<MonthlyAttendanceDto> monthlyAttendance = monthlyAttendanceService.monthlyAttendance(monthValue);
		return GenericResponse.builder().data(monthlyAttendance).build();
	}

	@GetMapping("csv/header")
	public ResponseEntity<byte[]> downloadCSVHeader() {

		String headers = monthlyAttendanceService.uploadCsvHeaders().stream().collect(Collectors.joining(","));

		// Convert the content to bytes
		byte[] csvBytes = headers.getBytes(StandardCharsets.UTF_8);

		// Set headers for the response
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		httpHeaders.setContentDispositionFormData("attachment", "Monthly_CSV_Template.csv");

		// Return the ResponseEntity with the CSV content and headers
		return ResponseEntity.ok().headers(httpHeaders).body(csvBytes);
	}

	@PostMapping("csv/upload")
	public GenericResponse uploadMonthlyAttendance(@RequestParam MultipartFile file)
			throws IOException, CSVErrorException {

		if (CSVHelper.hasCSVFormat(file)) {
			String message = this.monthlyAttendanceService.uploadMonthlyAttendance(file.getInputStream());
			if (message.isBlank())
				return GenericResponse.builder().message("CSV Data Upload Successfully").build();
			else
				throw CSVErrorException.builder().message(message).build();
		}
		return GenericResponse.builder().message("Invalid CSV Format").build();
	}
}
