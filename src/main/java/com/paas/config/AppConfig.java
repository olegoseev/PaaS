package com.paas.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	public static final String USER_NAME = "name";
	public static final String USER_UID = "uid";
	public static final String USER_GID = "gid";
	public static final String USER_COMMENT = "comment";
	public static final String USER_HOME = "home";
	public static final String USER_SHELL = "shell";
	
	
	public static final String GROUP_NAME = "name";
	public static final String GROUP_GID = "gid";
	public static final String GROUP_MEMBER = "member";
	
	public static final String EMPTY_STRING = "";

	public static final int UID_NOT_DEFINED = -1;
	public static final int GID_NOT_DEFINED = -1;
}
