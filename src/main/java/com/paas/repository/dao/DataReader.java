package com.paas.repository.dao;

import java.nio.file.Path;
import java.util.List;

import com.paas.utils.FileReader;
import com.paas.utils.StringToPath;

public abstract class DataReader<T> {

	protected String pathToFile;

	protected List<String> records;

	private boolean needReload;

	public void setUpdateNeeded() {
		needReload = true;
	}

	public void setDataSource(String path) {
		this.pathToFile = path;
	}

	private synchronized void reloadDataIfNeeded() {
		if (needReload == true) {
			reloadDataFromDataSource();
			needReload = false;
		}
	}

	private void reloadDataFromDataSource() {
		Path path = StringToPath.getPath(pathToFile);
		FileReader fr = new FileReader();
		records = fr.readFileInList(path);
	}

	public T readData() {
		reloadDataIfNeeded();
		return getRecords();
	}

	abstract T getRecords();
}
