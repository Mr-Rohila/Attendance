package hrms.attendance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DailyAttendanceDto {

	@NotNull(message = "Employee Id can not be Empty")
	private Long employeeId;

	@NotBlank(message = "Employee Name can not be Empty")
	private String employeeName;

	@NotBlank(message = "Status can not be Empty")
	private String status;

	@NotBlank(message = "Status can not be Empty")
	private String date;

	private String checkInTime;

	private String checkOutTime;

}
