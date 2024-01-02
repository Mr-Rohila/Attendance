package hrms.attendance.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import hrms.attendance.dto.GenericErrorResponse;
import hrms.attendance.exception.CSVErrorException;

@RestControllerAdvice
public class CustomExceptionController {

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	public GenericErrorResponse illegalArgumentException(IllegalArgumentException exception) {
		return GenericErrorResponse.builder().status(HttpStatus.NOT_ACCEPTABLE.value()).error(exception.getMessage())
				.build();
	}

	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	@ExceptionHandler(CSVErrorException.class)
	public ResponseEntity<byte[]> downloadErrorFile(CSVErrorException exception) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		headers.setContentDispositionFormData("attachment", "Response.txt");
		return ResponseEntity.status(406).headers(headers).body(exception.getMessage().getBytes());
	}
}
