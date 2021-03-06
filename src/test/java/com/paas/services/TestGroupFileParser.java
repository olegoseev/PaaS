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
import com.paas.model.Group;
import com.paas.services.parser.GroupRecordsParser;
import com.paas.utils.FileReader;
import com.paas.utils.StringToPath;

//@RunWith(JUnitPlatform.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestPropertySource(properties = { "group.records = src/test/resources/group",
		"dummy.file = src/test/resources/missing" })
public class TestGroupFileParser {

	@Value("${group.records}")
	private String groupFile;

	@Value("${dummy.file}")
	private String dummyFile;

	@Test
	public void testGetMembers() throws PaaSApplicationException {

		GroupRecordsParser parser = new GroupRecordsParser();

		Path path = StringToPath.getPath(groupFile);

		FileReader fr = new FileReader();

		List<String> records = fr.readFileInList(path);

		List<Group> list = parser.parseRecords(records);

		Assertions.assertEquals(3, list.get(1).getMembers().size(),
				"daemon:x:1:member1,member2,member3 group member test");

	}

	@Test
	public void testParse() throws PaaSApplicationException {

		GroupRecordsParser parser = new GroupRecordsParser();

		Path path = StringToPath.getPath(groupFile);

		FileReader fr = new FileReader();

		List<String> records = fr.readFileInList(path);

		List<Group> list = parser.parseRecords(records);

		Assertions.assertEquals(7, list.size(), "group file loading test");
	}

	@SuppressWarnings("unused")
	@Test
	public void fileParseErrorExceptionThrown() {

		PaaSApplicationException assertThrows = Assertions.assertThrows(PaaSApplicationException.class, () -> {
			GroupRecordsParser parser = new GroupRecordsParser();
			Path path = StringToPath.getPath(dummyFile);

			FileReader fr = new FileReader();

			List<String> records = fr.readFileInList(path);
			List<Group> list = parser.parseRecords(records);
		});
	}
}
