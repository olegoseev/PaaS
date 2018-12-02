package com.paas.repository.dao;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.paas.model.Group;
import com.paas.services.FileSystemWatchService;
import com.paas.services.GroupFileParser;

@Service
public class GroupRecordsDataReader extends DataReader<List<Group>> {

	// value for the string is in the property file
	@Value("${group.records}")
	private String path;

	@Autowired
	GroupFileParser parser;

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
	List<Group> getRecords() {
		return parser.parseRecords(records);
	}

}
