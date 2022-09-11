package br.com.llduran.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class DozerMapper
{
	private static final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

	public static <O, D> D parseObject(final O origin, final Class<D> destination)
	{
		return DozerMapper.mapper.map(origin, destination);
	}

	public static <O, D> List<D> parseListObjects(final List<O> origin, final Class<D> destination)
	{
		final List<D> destinationObjects = new ArrayList<D>();

		origin.forEach(o -> destinationObjects.add(DozerMapper.mapper.map(o, destination)));

		return destinationObjects;
	}

}