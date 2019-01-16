package com.paas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.paas.PaaSApplicationException;
import com.paas.model.User;
import com.paas.repository.dao.UserRecordsDataReader;
import com.paas.services.filter.UserFilter;
import com.paas.services.filter.UserFilter.Builder;

@Repository
public class UserRecordsRepository extends BaseRepositoryImpl<Integer, User> {

	@Autowired
	UserRecordsDataReader reader;

	@Override
	User filterByPk(Integer id, List<User> users) {
		Optional<User> user = users.stream().filter(u -> u.getUid() == id).findFirst();

		// Make sure something found
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}

	@Override
	protected List<User> applyFilter(List<User> records, User criteria) {
		UserFilter uf = Builder.newInstance().setCriteria(criteria).build();
		return uf.apply(records);
	}

	@Override
	public List<User> findAll() throws PaaSApplicationException {
		return reader.readData();
	}

}
