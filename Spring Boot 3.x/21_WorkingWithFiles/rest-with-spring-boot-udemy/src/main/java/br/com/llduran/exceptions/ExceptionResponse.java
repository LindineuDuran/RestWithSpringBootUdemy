package br.com.llduran.exceptions;

import java.io.Serializable;
import java.util.Date;

public class ExceptionResponse implements Serializable
{
	private static final long serialVersionUID = 1L;

	private final Date timestamp;
	private final String message;
	private final String details;

	public ExceptionResponse(final Date timestamp, final String message, final String details)
	{
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	public Date getTimestamp()
	{
		return this.timestamp;
	}

	public String getMessage()
	{
		return this.message;
	}

	public String getDetails()
	{
		return this.details;
	}
}