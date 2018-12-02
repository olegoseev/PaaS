package com.paas.repository.dao;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.paas.model.User;
import com.paas.services.FileSystemWatchService;
import com.paas.services.PasswdFileParser;

@Service
public class UserRecordsDataReader extends DataReader<List<User>> {

	// value for the string is in the property file
	@Value("${user.records}")
	private String path;

	@Autowired
	private PasswdFileParser parser;

	@Autowired
	private FileSystemWatchService watcher;

	@PostConstruct
	public void init() {
		pathToFile = path;
		setUpdateNeeded();
		watcher.setFilteToWatch(path);
		watcher.setSubsciber(this);
		watcher.startWatchService();
	}

	@Override
	List<User> getRecords() {
		return parser.parseRecords(records);
	}
}
