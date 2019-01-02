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

public interface BaseRepository<PK, T> {
	public List<T> findAll() throws PaaSApplicationException;

	public List<T> findAny(T model) throws PaaSApplicationException;

	T findBy(PK pk) throws PaaSApplicationException;
}
