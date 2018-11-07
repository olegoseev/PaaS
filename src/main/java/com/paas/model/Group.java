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
/**
 *  Model for group records in /etc/group file
 */

package com.paas.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.paas.PaaSApplicationException;
import com.paas.config.AppConfig;


public class Group implements Serializable {
	
	private static final long serialVersionUID = 3000633563828136079L;

	// Group name
	private String name;
	
	// Group Id
	private int gid;
	
	// Group members
	private List<String> members;
	
	public Group() {
		name = AppConfig.EMPTY_STRING;
		gid  = AppConfig.GID_NOT_DEFINED;
		members = new ArrayList<>();
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
		if (!(members instanceof ArrayList)) {
			throw new PaaSApplicationException("Object type missmatch");
		}
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
