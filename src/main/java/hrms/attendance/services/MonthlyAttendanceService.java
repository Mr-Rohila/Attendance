package hrms.attendance.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import hrms.attendance.dto.MonthlyAttendanceDto;
import hrms.attendance.entity.MonthlyAttedance;
import hrms.attendance.exception.CSVErrorException;

@Service
public interface MonthlyAttendanceService {

	List<MonthlyAttendanceDto> monthlyAttendance(Integer monthName);

	List<String> uploadCsvHeaders();

	String uploadMonthlyAttendance(InputStream inputStream) throws CSVErrorException, IOException;

	MonthlyAttedance saveMonthlyAttedance(MonthlyAttendanceDto dto) throws Exception;
}
