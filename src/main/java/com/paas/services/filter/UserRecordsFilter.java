package com.paas.services.filter;

import static com.paas.model.ModelDefaults.GID_NOT_DEFINED;
import static com.paas.model.ModelDefaults.UID_NOT_DEFINED;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.paas.model.User;
import com.paas.services.compare.Comparison;

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
			dataRecords = dataRecords.stream().filter(u -> Comparison.byName.apply(u, template))
					.collect(Collectors.toList());
		}
	}

	private boolean isNameInCriteria() {
		return template.getName().isBlank() == false;
	}

	private void userUidFilter() {
		if (isUidInCriteria()) {
			dataRecords = dataRecords.stream().filter(u -> Comparison.byUid.apply(u, template))
					.collect(Collectors.toList());
		}
	}

	private boolean isUidInCriteria() {
		return template.getUid() != UID_NOT_DEFINED;
	}

	private void userGidFilter() {
		if (isGidInCriteria()) {
			dataRecords = dataRecords.stream().filter(u -> Comparison.byGid.apply(u, template))
					.collect(Collectors.toList());
		}
	}

	private boolean isGidInCriteria() {
		return template.getGid() != GID_NOT_DEFINED;
	}

	private void userCommentsFilter() {
		if (isUserCommentsInCriteria()) {
			dataRecords = dataRecords.stream().filter(u -> Comparison.byComments.apply(u, template))
					.collect(Collectors.toList());
		}
	}

	private boolean isUserCommentsInCriteria() {
		return template.getComment().isBlank() == false;
	}

	private void userHomeFilter() {
		if (isUserHomeInCriteria()) {
			dataRecords = dataRecords.stream().filter(u -> Comparison.byHome.apply(u, template))
					.collect(Collectors.toList());
		}
	}

	private boolean isUserHomeInCriteria() {
		return template.getHome().isBlank() == false;
	}

	private void userShellFilter() {
		if (isUserShellInCriteria()) {
			dataRecords = dataRecords.stream().filter(u -> Comparison.byShell.apply(u, template))
					.collect(Collectors.toList());
		}
	}

	private boolean isUserShellInCriteria() {
		return template.getShell().isBlank() == false;
	}
}
