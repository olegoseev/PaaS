package com.paas.services.filter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import static com.paas.model.ModelDefaults.*;
import com.paas.model.User;

@Service
public class UserRecordsFilter extends RecordsFilter<User> {

	@Override
	public List<User> applyFor(List<User> records) {
		if (records.isEmpty()) {
			return Collections.emptyList();
		}
		dataRecords = records;
		userNameFilter();
		userUidFilter();
		userGidFilter();
		userCommentsFilter();
		userHomeFilter();
		userShellFilter();
		return dataRecords;
	}

	private void userNameFilter() {
		if (isNameInCriteria()) {
			dataRecords = dataRecords.stream().filter(u -> u.getName().equals(filterCriteria.getName()))
					.collect(Collectors.toList());
		}
	}

	private boolean isNameInCriteria() {
		return filterCriteria.getName().isBlank() == false;
	}

	private void userUidFilter() {
		if (isUidInCriteria()) {
			dataRecords = dataRecords.stream().filter(u -> u.getUid() == filterCriteria.getUid())
					.collect(Collectors.toList());
		}
	}

	private boolean isUidInCriteria() {
		return filterCriteria.getUid() != UID_NOT_DEFINED;
	}

	private void userGidFilter() {
		if (isGidInCriteria()) {
			dataRecords = dataRecords.stream().filter(u -> u.getGid() == filterCriteria.getGid())
					.collect(Collectors.toList());
		}
	}

	private boolean isGidInCriteria() {
		return filterCriteria.getGid() != GID_NOT_DEFINED;
	}

	private void userCommentsFilter() {
		if (isUserCommentsInCriteria()) {
			dataRecords = dataRecords.stream().filter(u -> u.getComment().equals(filterCriteria.getComment()))
					.collect(Collectors.toList());
		}
	}

	private boolean isUserCommentsInCriteria() {
		return filterCriteria.getComment().isBlank() == false;
	}

	private void userHomeFilter() {
		if (isUserHomeInCriteria()) {
			dataRecords = dataRecords.stream().filter(u -> u.getHome().equals(filterCriteria.getHome()))
					.collect(Collectors.toList());
		}
	}

	private boolean isUserHomeInCriteria() {
		return filterCriteria.getHome().isBlank() == false;
	}

	private void userShellFilter() {
		if (isUserShellInCriteria()) {
			dataRecords = dataRecords.stream().filter(u -> u.getShell().equals(filterCriteria.getShell()))
					.collect(Collectors.toList());
		}
	}

	private boolean isUserShellInCriteria() {
		return filterCriteria.getShell().isBlank() == false;
	}
}
