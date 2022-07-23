package br.com.llduran.controller;

import br.com.llduran.exception.UnsuportedMathOperationException;
import br.com.llduran.math.SimpleMath;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static br.com.llduran.converters.NumberConverter.convertToDouble;
import static br.com.llduran.converters.NumberConverter.isNumeric;

@RestController
public class MathController
{
	@RequestMapping(value="/sum/{numberOne}/{numberTwo}", method= RequestMethod.GET)
	public Double sum(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo)
			throws Exception
	{
		if(!isNumeric(numberOne)||!isNumeric(numberTwo))
		{
			throw new UnsuportedMathOperationException("Please, set a numeric value");
		}
		
		Double operation = SimpleMath.sum(numberOne, numberTwo);
		return operation;
	}

	@RequestMapping(value="/subtraction/{numberOne}/{numberTwo}", method= RequestMethod.GET)
	public Double subtract(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo)
			throws Exception
	{
		if(!isNumeric(numberOne)||!isNumeric(numberTwo))
		{
			throw new UnsuportedMathOperationException("Please, set a numeric value");
		}

		Double operation = SimpleMath.subtract(numberOne, numberTwo);
		return operation;
	}

	@RequestMapping(value="/multiplication/{numberOne}/{numberTwo}", method= RequestMethod.GET)
	public Double multiply(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo)
			throws Exception
	{
		if(!isNumeric(numberOne)||!isNumeric(numberTwo))
		{
			throw new UnsuportedMathOperationException("Please, set a numeric value");
		}

		Double operation = SimpleMath.multiply(numberOne, numberTwo);
		return operation;
	}

	@RequestMapping(value="/division/{numberOne}/{numberTwo}", method= RequestMethod.GET)
	public Double division(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo)
			throws Exception
	{
		if(!isNumeric(numberOne)||!isNumeric(numberTwo))
		{
			throw new UnsuportedMathOperationException("Please, set a numeric value");
		}

		if(convertToDouble(numberTwo) ==0)
		{
			throw new UnsuportedMathOperationException("Division by zero is not allowed");
		}

		Double operation = SimpleMath.divide(numberOne, numberTwo);
		return operation;
	}

	@RequestMapping(value="/average/{numberOne}/{numberTwo}", method= RequestMethod.GET)
	public Double average(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo)
			throws Exception
	{
		if(!isNumeric(numberOne)||!isNumeric(numberTwo))
		{
			throw new UnsuportedMathOperationException("Please, set a numeric value");
		}

		Double operation = SimpleMath.average(numberOne, numberTwo);
		return operation;
	}

	@RequestMapping(value="/squareRoot/{number}", method= RequestMethod.GET)
	public Double squareRoot(@PathVariable("number") String number)
			throws Exception
	{
		if(!isNumeric(number))
		{
			throw new UnsuportedMathOperationException("Please, set a numeric value");
		}

		Double operation = SimpleMath.squareRoot(number);
		return operation;
	}
}