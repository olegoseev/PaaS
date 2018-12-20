package com.paas.repository;

import java.util.List;

import com.paas.repository.dao.DataReader;
import com.paas.services.filter.RecordsFilter;

public abstract class BaseRepositoryImpl <PK, MODEL> implements BaseRepository<PK, MODEL> {
	
	protected DataReader<MODEL> reader;
	
	protected RecordsFilter<MODEL> filter;
	
	public BaseRepositoryImpl(DataReader<MODEL> reader, RecordsFilter<MODEL> filter) {
		this.reader = reader;
		this.filter = filter;
	}
	
	public List<MODEL> findAll() {
		return reader.readData();
	}

	public List<MODEL> findAny(MODEL modelToMatch) {
		List<MODEL> models = findAll();
		return applyFilter(modelToMatch, models);
	}

	List<MODEL> applyFilter(MODEL modelToMatch, List<MODEL> models) {
		filter.setCriteria(modelToMatch);
		return filter.applyFor(models);
	}
	
	public MODEL findBy(PK pk) {
		List<MODEL> models = findAll();
		return filterByPk(pk, models);
	}
	
	abstract MODEL filterByPk(PK pk, List<MODEL> models);
}
