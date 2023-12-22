package hrms.attendance.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import hrms.attendance.dto.GenericErrorResponse;

@RestControllerAdvice
public class CustomExceptionController {

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	public GenericErrorResponse illegalArgumentException(IllegalArgumentException exception) {
		return GenericErrorResponse.builder().status(HttpStatus.NOT_ACCEPTABLE.value()).error(exception.getMessage())
				.build();
	}
}
