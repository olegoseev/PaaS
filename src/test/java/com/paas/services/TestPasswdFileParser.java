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
import com.paas.model.User;

//@RunWith(JUnitPlatform.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {"user.records = src/test/resources/passwd", "dummy.file = src/test/resources/missing"})
public class TestPasswdFileParser {

	
	//final String pathStr = "src/test/resources/passwd";
	@Value("${user.records}")
	private String userFile;
	
	@Value("${dummy.file}")
	private String dummyFile;
	
	private Path getPathToFile() throws FileNotFoundException {
		
		File file = ResourceUtils.getFile(userFile);
		
		Path path = Paths.get(file.getAbsolutePath());
		
		return path;
	}
	
	@Test
	public void testParse() {
		
		PasswdFileParser parser = new PasswdFileParser();
		
		try {

			Path path = getPathToFile();
	
			List<User> list = parser.parse(path);
			
			Assertions.assertEquals(list.size(), 10, "passwd file loading and parsing test");
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (PaaSApplicationException e) {
			
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	@Test
	public void fileParseErrorExceptionThrown() {
			
		Assertions.assertThrows(PaaSApplicationException.class, () -> {
			PasswdFileParser parser = new PasswdFileParser();

			try {
					File file = ResourceUtils.getFile(dummyFile);
					Path path = Paths.get(file.getAbsolutePath());
					List<User> list = parser.parse(path);
					
			} catch (FileNotFoundException e) {

					e.printStackTrace();
			}
		});
	}	
}
