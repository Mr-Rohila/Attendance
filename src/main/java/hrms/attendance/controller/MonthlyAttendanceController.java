package hrms.attendance.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hrms.attendance.dto.GenericResponse;
import hrms.attendance.dto.MonthlyAttendanceDto;
import hrms.attendance.services.MonthlyAttendanceService;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequestMapping("attendance/monthly")
@RequiredArgsConstructor
public class MonthlyAttendanceController {

	private final MonthlyAttendanceService monthlyAttendanceService;

	@GetMapping("/{monthValue}")
	public GenericResponse monthlyAttendance(@PathVariable(required = false) Integer monthValue) {
		List<MonthlyAttendanceDto> monthlyAttendance = monthlyAttendanceService.monthlyAttendance(monthValue);
		return GenericResponse.builder().data(monthlyAttendance).build();
	}

	@GetMapping("csv/header")
	public ResponseEntity<byte[]> downloadCSVHeader() {
		// Create CSV content with headers
		List<String> csvHeader = monthlyAttendanceService.uploadCsvHeaders();

		// Convert the content to bytes
		byte[] csvBytes = csvHeader.toString().replace("[", "").replace("]", "").getBytes(StandardCharsets.UTF_8);

		// Set headers for the response
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "Monthly_CSV_Template.csv");

		// Return the ResponseEntity with the CSV content and headers
		return ResponseEntity.ok().headers(headers).body(csvBytes);
	}
}
