package com.paas.repository.dao;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.paas.PaaSApplicationException;
import com.paas.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestPropertySource (properties = {"user.records = src/test/resources/passwd","dummy.file = src/test/resources/missing"})
public class TestUserRecordsDataReader {
	
	@Value("${dummy.file}")
	private String dummyFile;
	
	@Value("${user.records}")
	private String realFile;
	
	@Autowired
	UserRecordsDataReader dataReader;
	
	@Test
	public void testReadData() throws PaaSApplicationException {
		ReflectionTestUtils.setField(dataReader, "path", realFile);
		List<User> records = dataReader.readData();
		Assertions.assertEquals(10, records.size(), "read user records from file");
	}
}
