package hrms.attendance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hrms.attendance.entity.MonthlyAttedance;
import hrms.attendance.enums.MonthName;

@Repository
public interface MonthlyAttendanceRepository extends JpaRepository<MonthlyAttedance, Long> {
	List<MonthlyAttedance> findByMonthName(MonthName monthName);

	List<MonthlyAttedance> findByEmployeeIdAndMonthName(Long employeeId, MonthName monthName);
}
