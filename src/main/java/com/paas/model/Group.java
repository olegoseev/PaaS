/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2018 the original author Oleg Oseev.
 * <p>
 */
/**
 *  Model for group records in /etc/group file
 */

package com.paas.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.paas.model.ModelDefaults.*;

public class Group implements Serializable {

	private static final long serialVersionUID = 3000633563828136079L;

	// Group name
	private String name;

	// Group Id
	private int gid;

	// Group members
	private List<String> members;

	public Group() {
		name = EMPTY_STRING;
		gid = GID_NOT_DEFINED;
		members = Collections.emptyList();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public List<String> getMembers() {
		return members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}

	@Override
	public int hashCode() {
		return Objects.hash(gid, members, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Group)) {
			return false;
		}
		Group other = (Group) obj;
		return gid == other.gid && Objects.equals(members, other.members) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Group [name=" + name + ", gid=" + gid + ", members=" + members + "]";
	}
}
