package com.paas.services.filter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.paas.model.Group;
import static com.paas.model.ModelDefaults.*;

@Service
public class GroupRecordsFilter extends RecordsFilter<Group> {

	@Override
	public List<Group> applyFor(List<Group> records) {
		if (records.isEmpty()) {
			return Collections.emptyList();
		}
		dataRecords = records;
		groupNameFilter();
		groupGidFilter();
		groupMembers();
		return dataRecords;
	}

	private void groupNameFilter() {
		if (isGroupNameInCriteria()) {
			dataRecords = dataRecords.stream().filter(g -> g.getName().equals(template.getName()))
					.collect(Collectors.toList());
		}
	}

	private boolean isGroupNameInCriteria() {
		return template.getName().isBlank() == false;
	}

	private void groupGidFilter() {
		if (isGroupGidInCriteria()) {
			dataRecords = dataRecords.stream().filter(g -> g.getGid() == template.getGid())
					.collect(Collectors.toList());
		}
	}

	private boolean isGroupGidInCriteria() {
		return template.getGid() != GID_NOT_DEFINED;
	}

	private void groupMembers() {
		if (isGroupMembersInCriteria()) {
			dataRecords = dataRecords.stream().filter(g -> g.getMembers().containsAll(template.getMembers()))
					.collect(Collectors.toList());
		}
	}

	private boolean isGroupMembersInCriteria() {
		return template.getMembers().isEmpty() == false;
	}
}
