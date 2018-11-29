package com.paas.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {"real.file = src/test/resources/passwd", "dummy.file = src/test/resources/missing"})
public class TestReadFileInList {
	
	@Value("${real.file}")
	private String realFile;
	
	@Value("${dummy.file}")
	private String dummyFile;
	
	private static	FileReader fileReader;
	
	@BeforeAll
	public static void init() {
		fileReader = new FileReader();
	}
	

	@Test
	public void testReadTileInList() {
		
		try {

			File file = ResourceUtils.getFile(realFile);
			
			Path path = Paths.get(file.getAbsolutePath());
			
			List<String> list = fileReader.readFileInList(path);
			
			assertEquals(list.size(), 10, "passwd file loading test");
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (PaaSApplicationException e) {
			
			e.printStackTrace();
		} 
	}
	
	@SuppressWarnings("unused")
	@Test
	public void fileReadErrorExceptionThrown() {
			
		Assertions.assertThrows(PaaSApplicationException.class, () -> {
			try {
				File file = ResourceUtils.getFile(dummyFile);
					
				Path path = Paths.get(file.getAbsolutePath());
	
				List<String> list = fileReader.readFileInList(path);
						
			} catch (FileNotFoundException e) {
	
				e.printStackTrace();
			}
		});
	}	
}
