package com.paas.services;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.paas.PaaSApplicationException;
import com.paas.model.User;
import com.paas.services.parser.PasswdRecordsParser;
import com.paas.utils.FileReader;
import com.paas.utils.StringToPath;

//@RunWith(JUnitPlatform.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestPropertySource(properties = { "user.records = src/test/resources/passwd",
		"dummy.file = src/test/resources/missing" })
public class TestPasswdFileParser {

	// final String pathStr = "src/test/resources/passwd";
	@Value("${user.records}")
	private String userFile;

	@Value("${dummy.file}")
	private String dummyFile;

	@Test
	public void testParse() throws PaaSApplicationException {

		PasswdRecordsParser parser = new PasswdRecordsParser();

		Path path = StringToPath.getPath(userFile);

		FileReader fr = new FileReader();

		List<String> records = fr.readFileInList(path);

		List<User> list = parser.parseRecords(records);

		Assertions.assertEquals(list.size(), 10, "passwd file loading and parsing test");

	}

	@SuppressWarnings("unused")
	@Test
	public void fileParseErrorExceptionThrown() {

		Assertions.assertThrows(PaaSApplicationException.class, () -> {
			PasswdRecordsParser parser = new PasswdRecordsParser();

			Path path = StringToPath.getPath(dummyFile);

			FileReader fr = new FileReader();

			List<String> records = fr.readFileInList(path);

			List<User> list = parser.parseRecords(records);

		});
	}
}
