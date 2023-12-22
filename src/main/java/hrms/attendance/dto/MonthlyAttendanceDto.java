package hrms.attendance.dto;

import hrms.attendance.entity.MonthlyAttedance;
import hrms.attendance.enums.MonthName;
import lombok.Data;

@Data
public class MonthlyAttendanceDto {

	private Long employeeId;

	private String employeeName;

	private MonthName monthName;

	private int publicHoliday;

	private int totalWorkingDay;

	private int paidLeave;

	private int employeeWorkingDay;

	public MonthlyAttendanceDto() {
	}

	public MonthlyAttendanceDto(MonthlyAttedance monthlyAttedance) {
		this.employeeId = monthlyAttedance.getEmployeeId();
		this.employeeName = monthlyAttedance.getEmployeeName();
		this.monthName = monthlyAttedance.getMonthName();
		this.publicHoliday = monthlyAttedance.getPublicHoliday();
		this.totalWorkingDay = monthlyAttedance.getTotalWorkingDay();
		this.paidLeave = monthlyAttedance.getPaidLeave();
		this.employeeWorkingDay = monthlyAttedance.getEmployeeWorkingDay();
	}

}
