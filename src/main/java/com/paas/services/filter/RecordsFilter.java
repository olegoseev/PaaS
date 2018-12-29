package com.paas.services.filter;

import java.util.Collections;
import java.util.List;

public abstract class RecordsFilter<MODEL> {

	protected List<MODEL> dataRecords = Collections.emptyList();
	protected MODEL template;

	public void setCriteria(MODEL model) {
		template = model;
	}

	abstract public List<MODEL> applyFor(List<MODEL> records);
}
