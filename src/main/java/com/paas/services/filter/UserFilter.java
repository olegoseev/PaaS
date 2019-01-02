package com.paas.services.filter;

import static com.paas.model.ModelDefaults.GID_NOT_DEFINED;
import static com.paas.model.ModelDefaults.UID_NOT_DEFINED;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.paas.model.User;

@Service
public class UserFilter {

	@Component
	public static class Builder {

		private User criteria;

		private Filter<User> filter;

		Predicate<User> byName = (u) -> u.getName().equals(criteria.getName());
		Predicate<User> name = (u) -> !u.getName().isBlank();
		Predicate<User> byUid = (u) -> u.getUid() == criteria.getUid();
		Predicate<User> uid = (u) -> u.getUid() != UID_NOT_DEFINED;
		Predicate<User> byGid = (u) -> u.getGid() == criteria.getGid();
		Predicate<User> gid = (u) -> u.getGid() != GID_NOT_DEFINED;
		Predicate<User> byComments = (u) -> u.getComment().equals(criteria.getComment());
		Predicate<User> comments = (u) -> !u.getComment().isBlank();
		Predicate<User> byHome = (u) -> u.getHome().equals(criteria.getHome());
		Predicate<User> home = (u) -> !u.getHome().isBlank();
		Predicate<User> byShell = (u) -> u.getShell().equals(criteria.getShell());
		Predicate<User> shell = (u) -> !u.getShell().isBlank();

		private Builder() {
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public UserFilter build() {
			filter = new FilterImpl<User>(byName, name);

			Filter<User> uf = new FilterImpl<User>(byUid, uid);
			filter.setNextFilter(uf);

			Filter<User> gf = new FilterImpl<User>(byGid, gid);
			uf.setNextFilter(gf);

			Filter<User> cf = new FilterImpl<User>(byComments, comments);
			gf.setNextFilter(cf);

			Filter<User> hf = new FilterImpl<User>(byHome, home);
			cf.setNextFilter(hf);

			Filter<User> sf = new FilterImpl<User>(byShell, shell);
			hf.setNextFilter(sf);

			return new UserFilter(this);
		}

		public Builder setCriteria(User criteria) {
			this.criteria = criteria;
			return this;
		}
	}

	private UserFilter(Builder builder) {
		this.filter = builder.filter;
		this.user = builder.criteria;
	}

	private final Filter<User> filter;
	private final User user;

	public List<User> apply(List<User> records) {
		return filter.meet(records, user);
	}
}
