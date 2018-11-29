package com.paas.repository.dao;

import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;

import com.paas.utils.StringToPath;

public abstract class DataReader <T> {

	protected String pathToFile;
	
	static AtomicBoolean updateNeeded;
	
	public DataReader() {
		DataReader.updateNeeded.set(true);
	}
	
	public DataReader(String pathToFile) {
		DataReader.updateNeeded.set(true);
		this.pathToFile = pathToFile;
	}
	
	protected void refresh() {
		if(updateNeeded.get() == true) {
			Path path = StringToPath.getPath(pathToFile);
		}
	}
}
