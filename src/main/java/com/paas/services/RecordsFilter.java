package com.paas.services;

import java.util.Collections;
import java.util.List;

public abstract class RecordsFilter<MODEL> {

	protected List<MODEL> dataRecords = Collections.emptyList();
	protected MODEL filterCriteria;
	
	public void setCriteria(MODEL criteria) {
		filterCriteria = criteria;
	}
	
	abstract public List<MODEL> appplyFor(List<MODEL> records);
}
