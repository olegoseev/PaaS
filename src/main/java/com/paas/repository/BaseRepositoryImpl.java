package com.paas.repository;

import java.util.List;

import com.paas.PaaSApplicationException;

public abstract class BaseRepositoryImpl<PK, T> implements BaseRepository<PK, T> {

        @Override
	public abstract List<T> findAll() throws PaaSApplicationException;

        @Override
	public List<T> findAny(T criteria) throws PaaSApplicationException {
		List<T> records = findAll();
		return applyFilter(records, criteria);
	}

	protected abstract List<T> applyFilter(List<T> records, T criteria);

        @Override
	public T findBy(PK pk) throws PaaSApplicationException {
		List<T> records = findAll();
		return filterByPk(pk, records);
	}

	abstract T filterByPk(PK pk, List<T> records);
}
