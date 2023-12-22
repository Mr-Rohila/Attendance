package hrms.attendance.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hrms.attendance.dto.AttendanceDto;
import hrms.attendance.dto.GenericResponse;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequestMapping("attendance")
@RequiredArgsConstructor
public class AttendanceController {

	@GetMapping
	public List<?> attendanceList() {
		return null;
	}

	@PostMapping
	public GenericResponse saveAttendance(@RequestBody AttendanceDto attendance) {
		return GenericResponse.builder().message("Save Successfully").data(attendance).build();
	}

}
