package hrms.attendance.services;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import hrms.attendance.dto.MonthlyAttendanceDto;

@Service
public interface MonthlyAttendanceService {

	List<MonthlyAttendanceDto> monthlyAttendance(Integer monthName);

	List<String> uploadCsvHeaders();

	String uploadMonthlyAttendance(InputStream inputStream);
}
