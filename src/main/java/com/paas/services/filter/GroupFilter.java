package com.paas.services.filter;

import static com.paas.model.ModelDefaults.GID_NOT_DEFINED;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.paas.model.Group;

public class GroupFilter {

	@Component
	public static class Builder {

		private Group criteria;

		private Filter<Group> filter;

		Predicate<Group> byName = (g) -> g.getName().equals(criteria.getName());
		Predicate<Group> name = (g) -> !g.getName().isBlank();
		Predicate<Group> byGid = (g) -> g.getGid() == criteria.getGid();
		Predicate<Group> gid = (g) -> g.getGid() != GID_NOT_DEFINED;
		Predicate<Group> byMembers = (g) -> g.getMembers().containsAll(criteria.getMembers());
		Predicate<Group> members = (g) -> !g.getMembers().isEmpty();

		private Builder() {
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public GroupFilter build() {
			filter = new FilterImpl<Group>(byName, name);

			Filter<Group> gf = new FilterImpl<Group>(byGid, gid);
			filter.setNextFilter(gf);

			Filter<Group> mf = new FilterImpl<Group>(byMembers, members);
			gf.setNextFilter(mf);

			return new GroupFilter(this);
		}

		public Builder setCriteria(Group criteria) {
			this.criteria = criteria;
			return this;
		}
	}

	private GroupFilter(Builder builder) {
		this.filter = builder.filter;
		this.group = builder.criteria;
	}

	private final Filter<Group> filter;
	private final Group group;

	public List<Group> apply(List<Group> records) {
		return filter.meet(records, group);
	}
}
