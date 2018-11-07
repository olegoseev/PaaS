/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2018 the original author Oleg Oseev.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
/*
 *  Model for user record in /etc/passwd file
 */
package com.paas.model;

import java.io.Serializable;
import java.util.Objects;

import com.paas.config.AppConfig;


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
		name	= AppConfig.EMPTY_STRING;
		uid		= AppConfig.UID_NOT_DEFINED;
		gid		= AppConfig.GID_NOT_DEFINED;
		comment = AppConfig.EMPTY_STRING;
		home	= AppConfig.EMPTY_STRING;
		shell	= AppConfig.EMPTY_STRING;
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
