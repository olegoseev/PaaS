/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2018 the original author Oleg Oseev.
 * <p>
 */
/**
 *  Repository interface
 */
package com.paas.repository;

import java.util.List;

import com.paas.PaaSApplicationException;

public interface BaseRepository<PK, MODEL> {
	public List<MODEL> findAll() throws PaaSApplicationException;

	public List<MODEL> findAny(MODEL model) throws PaaSApplicationException;

	MODEL findBy(PK pk) throws PaaSApplicationException;
}
