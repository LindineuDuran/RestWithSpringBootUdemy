package br.com.llduran.restwithspringbootudemy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;
import java.util.Date;

@Getter
@AllArgsConstructor
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExceptionResponse implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Date timestamp;
	private String message;
	private String details;
}