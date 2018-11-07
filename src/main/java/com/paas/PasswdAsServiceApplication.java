package com.paas;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PasswdAsServiceApplication implements ApplicationRunner{
	
	private static final Logger LOG = LoggerFactory.getLogger(PasswdAsServiceApplication.class);

	public static void main(String[] args) throws Exception {
		SpringApplication.run(PasswdAsServiceApplication.class, args);
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		LOG.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
	 }
}
