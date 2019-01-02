package com.paas.repository;

import java.util.List;

import com.paas.PaaSApplicationException;

public abstract class BaseRepositoryImpl<PK, T> implements BaseRepository<PK, T> {

	public abstract List<T> findAll() throws PaaSApplicationException;

	public List<T> findAny(T criteria) throws PaaSApplicationException {
		List<T> records = findAll();
		return applyFilter(records, criteria);
	}

	protected abstract List<T> applyFilter(List<T> records, T criteria);

	public T findBy(PK pk) throws PaaSApplicationException {
		List<T> records = findAll();
		return filterByPk(pk, records);
	}

	abstract T filterByPk(PK pk, List<T> records);
}
