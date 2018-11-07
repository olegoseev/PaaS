package com.paas.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.util.ResourceUtils;

import com.paas.PaaSApplicationException;
import com.paas.model.Group;

//@RunWith(JUnitPlatform.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {"group.records = src/test/resources/group", "dummy.file = src/test/resources/missing"})
public class TestGroupFileParser {

	@Value("${group.records}")
	private String groupFile;
	
	@Value("${dummy.file}")
	private String dummyFile;
	
	private Path getPathToFile() throws FileNotFoundException {
		
		File file = ResourceUtils.getFile(groupFile);
		
		Path path = Paths.get(file.getAbsolutePath());
		
		return path;
	}
	
	@Test
	public void testGetMembers() {
		
		GroupFileParser parser = new GroupFileParser();
		
		try {

			Path path = getPathToFile();
	
			List<Group> list = parser.parse(path);
			
			Assertions.assertEquals(list.get(1).getMembers().size(), 3, "daemon:x:1:member1,member2,member3 group member test");
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}  catch (PaaSApplicationException e) {
			
			e.printStackTrace();
		}
	}
	
	@Test
	public void testParse() {
		
		GroupFileParser parser = new GroupFileParser();
		
		try {
			
			Path path = getPathToFile();
	
			List<Group> list = parser.parse(path);
			
			Assertions.assertEquals(list.size(), 7, "group file loading test");
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}  catch (PaaSApplicationException e) {
			
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	@Test
	public void fileParseErrorExceptionThrown() {
			
		Assertions.assertThrows(PaaSApplicationException.class, () -> {
			GroupFileParser parser = new GroupFileParser();

			try {
					File file = ResourceUtils.getFile(dummyFile);
					Path path = Paths.get(file.getAbsolutePath());
					List<Group> list = parser.parse(path);
					
			} catch (FileNotFoundException e) {

					e.printStackTrace();
			}
		});
	}
}
