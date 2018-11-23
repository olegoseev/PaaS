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

public interface BaseRepository<PK, MODEL> {
	public List<MODEL> findAll();
	public List<MODEL> findAny(MODEL model);
	MODEL findBy(PK pk);
}
