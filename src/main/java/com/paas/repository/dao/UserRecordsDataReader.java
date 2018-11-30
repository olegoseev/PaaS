package com.paas.repository.dao;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.paas.model.User;
import com.paas.services.PasswdFileParser;
import com.paas.utils.FileReader;
import com.paas.utils.StringToPath;

@Service
public class UserRecordsDataReader extends DataReader<List<User>> {

	// value for the string is in the property file
	@Value("${user.records}")
	private String pathToFile;
	
	protected static AtomicBoolean needToReload = new AtomicBoolean(true);
	
	private List<String> records;
	
	@Autowired
	private PasswdFileParser parser;
	
	@Override 
	synchronized void reloadDataIfNeeded() {
		if(needToReload.get() == true) {
			reloadDataFromDataSource();
			needToReload.set(false);
		}
	}

	@Override
	void reloadDataFromDataSource() {
		Path path = StringToPath.getPath(pathToFile);
		FileReader fr = new FileReader();
		records = fr.readFileInList(path);
	}

	@Override
	public List<User> readData() {
		reloadDataIfNeeded();
		List<User> users = parser.parseRecords(records);
		return users;
	}
}
