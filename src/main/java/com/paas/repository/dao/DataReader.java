package com.paas.repository.dao;

public abstract class DataReader<T> {
	
	abstract void reloadDataIfNeeded();
	abstract void reloadDataFromDataSource();
	abstract public T readData();
}
