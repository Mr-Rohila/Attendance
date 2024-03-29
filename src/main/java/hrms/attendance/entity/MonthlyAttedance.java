package hrms.attendance.entity;

import hrms.attendance.dto.MonthlyAttendanceDto;
import hrms.attendance.enums.MonthName;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MonthlyAttedance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long employeeId;

	private String employeeName;

	@Enumerated(EnumType.STRING)
	private MonthName monthName;

	private int publicHoliday;

	private int totalWorkingDay;

	private int paidLeave;

	private int employeeWorkingDay;

	public MonthlyAttedance() {
	}

	public MonthlyAttedance(MonthlyAttendanceDto dto) {
		this.employeeId = dto.getEmployeeId();
		this.employeeName = dto.getEmployeeName();
		this.monthName = dto.getMonthName();
		this.publicHoliday = dto.getPublicHoliday();
		this.totalWorkingDay = dto.getTotalWorkingDay();
		this.paidLeave = dto.getPaidLeave();
		this.employeeWorkingDay = dto.getEmployeeWorkingDay();

	}

}
