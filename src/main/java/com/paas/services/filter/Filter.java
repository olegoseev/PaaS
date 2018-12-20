package com.paas.services.filter;

import java.util.List;

abstract public class Filter<MODEL> {

	protected MODEL model;

	public Filter(MODEL model) {
		this.model = model;
	}

	abstract public List<MODEL> apply(List<MODEL> records);
}
