package hrms.attendance.services.impl;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Component;

import hrms.attendance.dto.MonthlyAttendanceDto;
import hrms.attendance.entity.MonthlyAttedance;
import hrms.attendance.enums.MonthName;
import hrms.attendance.repository.MonthlyAttendanceRepository;
import hrms.attendance.services.MonthlyAttendanceService;
import hrms.attendance.utils.Keys;
import hrms.attendance.utils.MonthUtils;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MonthlyAttendanceServiceImpl implements MonthlyAttendanceService {

	private final Keys keys;
	private final MonthlyAttendanceRepository monthlyAttendanceRepository;

	@Override
	public List<MonthlyAttendanceDto> monthlyAttendance(Integer month) {
		final MonthName monthName = month != null && month != 0 ? MonthUtils.convertMonthName(month)
				: MonthUtils.getCurrentMonthName(1);
		List<MonthlyAttedance> findByMonthName = monthlyAttendanceRepository.findByMonthName(monthName);
		return findByMonthName.stream().map(MonthlyAttendanceDto::new).toList();
	}

	@Override
	public String uploadMonthlyAttendance(InputStream inputStream) {

		return null;
	}

	@Override
	public List<String> uploadCsvHeaders() {
		return List.of(keys.getMonthlyCsvHeader()).stream().map(String::trim).toList();
	}

}
