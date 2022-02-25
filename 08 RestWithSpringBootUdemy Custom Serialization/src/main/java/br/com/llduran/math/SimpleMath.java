package br.com.llduran.math;

import static br.com.llduran.converters.NumberConverter.convertToDouble;

public class SimpleMath
{
	public static double sum(String numberOne, String numberTwo)
	{
		return convertToDouble(numberOne) + convertToDouble(numberTwo);
	}

	public static double subtract(String numberOne, String numberTwo)
	{
		return convertToDouble(numberOne) - convertToDouble(numberTwo);
	}

	public static double multiply(String numberOne, String numberTwo)
	{
		return convertToDouble(numberOne) * convertToDouble(numberTwo);
	}

	public static double divide(String numberOne, String numberTwo)
	{
		return convertToDouble(numberOne) / convertToDouble(numberTwo);
	}

	public static double average(String numberOne, String numberTwo)
	{
		return (convertToDouble(numberOne) + convertToDouble(numberTwo)) / 2;
	}

	public static Double squareRoot(String number)
	{
		return (Double) Math.sqrt(convertToDouble(number));
	}
}
