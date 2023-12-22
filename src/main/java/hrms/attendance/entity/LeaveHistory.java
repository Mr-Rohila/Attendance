package hrms.attendance.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import hrms.attendance.enums.LeaveReason;
import hrms.attendance.enums.LeaveStatus;
import hrms.attendance.enums.LeaveType;
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
public class LeaveHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long employeeId;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date leaveDate;

	@Enumerated(EnumType.STRING)
	private LeaveType leaveType;

	@Enumerated(EnumType.STRING)
	private LeaveReason reason;

	@Enumerated(EnumType.STRING)
	private MonthName monthName;

	@Enumerated(EnumType.STRING)
	private LeaveStatus status;

	private String message;

	private Long approvedById;

	private String approvedByName;

	private String comment;

}