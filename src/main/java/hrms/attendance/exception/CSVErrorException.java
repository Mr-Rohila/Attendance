package hrms.attendance.exception;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class CSVErrorException extends Exception {
	private static final long serialVersionUID = 4994192759123199122L;
	private String message;

}
