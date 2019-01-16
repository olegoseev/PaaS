package com.paas.repository.dao;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.paas.PaaSApplicationException;
import com.paas.model.User;
import com.paas.services.FileSystemWatchService;
import com.paas.services.parser.PasswdRecordsParser;

@Service
public class UserRecordsDataReader extends DataReader<User> {

	// value for the string is in the property file
	@Value("${user.records}")
	private String path;

	@Autowired
	private PasswdRecordsParser parser;

	@Autowired
	private FileSystemWatchService watcher;

	@PostConstruct
	public void init() {
		try {
			pathToFile = path;
			setUpdateNeeded();
			watcher.setFilteToWatch(path);
			watcher.setSubsciber(this);
			watcher.startWatchService();
		} catch (PaaSApplicationException pe) {
			// TODO: add logging
		}
	}

	@Override
	List<User> getRecords() throws PaaSApplicationException {
		return parser.parseRecords(records);
	}
}
