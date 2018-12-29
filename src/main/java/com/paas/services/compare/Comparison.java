package com.paas.services.compare;

import java.util.function.BiFunction;

import com.paas.model.User;

public class Comparison {

	public static BiFunction<User, User, Boolean> byName = (a, b) -> {return a.getName().equals(b.getName());};
	public static BiFunction<User, User, Boolean> byUid = (a, b) -> {return a.getUid() == b.getUid();};
	public static BiFunction<User, User, Boolean> byGid = (a, b) -> {return a.getGid() == b.getGid();};
	public static BiFunction<User, User, Boolean> byComments = (a, b) -> {return a.getComment().equals(b.getComment());};
	public static BiFunction<User, User, Boolean> byHome = (a, b) -> {return a.getHome().equals(b.getHome());};
	public static BiFunction<User, User, Boolean> byShell = (a, b) -> {return a.getShell().equals(b.getShell());};
}
