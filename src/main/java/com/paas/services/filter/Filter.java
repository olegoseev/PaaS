package com.paas.services.filter;

import java.util.List;

public interface Filter<T> {

	public void setNextFilter(Filter<T> filter);

	public List<T> meet(List<T> records, T template);
}
