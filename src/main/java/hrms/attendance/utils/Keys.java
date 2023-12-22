package hrms.attendance.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Data;

@Service
@Data
public class Keys {

	@Value("#{'${attendance.monthly.csv.header}'.split(',')}")
	private String[] monthlyCsvHeader;
}
