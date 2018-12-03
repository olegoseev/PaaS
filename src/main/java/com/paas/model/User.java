/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2018 the original author Oleg Oseev.
 * <p>
 */
/**
 *  Model for user record in /etc/passwd file
 */
package com.paas.model;

import java.io.Serializable;
import java.util.Objects;

import static com.paas.model.ModelDefaults.*;

public class User implements Serializable {

	private static final long serialVersionUID = -5224168770465494929L;

	// User name
	private String name;

	// User Id
	private int uid;

	// Group Id
	private int gid;

	// User comments
	private String comment;

	// User home directory
	private String home;

	// User shell
	private String shell;

	public User() {
		name = EMPTY_STRING;
		uid = UID_NOT_DEFINED;
		gid = GID_NOT_DEFINED;
		comment = EMPTY_STRING;
		home = EMPTY_STRING;
		shell = EMPTY_STRING;
	}

	public String getName() {
		return name;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getShell() {
		return shell;
	}

	public void setShell(String shell) {
		this.shell = shell;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", uid=" + uid + ", gid=" + gid + ", comment=" + comment + ", home=" + home
				+ ", shell=" + shell + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(comment, gid, home, name, shell, uid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		return Objects.equals(comment, other.comment) && gid == other.gid && Objects.equals(home, other.home)
				&& Objects.equals(name, other.name) && Objects.equals(shell, other.shell) && uid == other.uid;
	}

}
