package com.paas.services.filter;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilterImpl<T> implements Filter<T> {

	protected Predicate<T> criteria;

	protected Predicate<T> field;

	public FilterImpl(Predicate<T> criteria, Predicate<T> field) {
		this.criteria = criteria;
		this.field = field;
	}

	protected Filter<T> filter;

	@Override
	public void setNextFilter(Filter<T> filter) {
		this.filter = filter;
	}

	boolean nextFilter() {
		return filter != null;
	}

	@Override
	public List<T> meet(List<T> records, T template) {
		if (field.test(template)) {
			records = records.stream().filter(record -> criteria.test(record)).collect(Collectors.toList());
		}
		if (nextFilter()) {
			return filter.meet(records, template);
		}

		return records;
	}
}
