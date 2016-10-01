package com.ebusato.survey.rest.resource.parser;

import org.modelmapper.ModelMapper;

public interface ResourceParser<E> {

	public default E toEntity(Class<E> entityClass) {
		return new ModelMapper().map(this, entityClass);
	}
}