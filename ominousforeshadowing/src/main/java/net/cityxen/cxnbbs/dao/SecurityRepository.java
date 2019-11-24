package net.cityxen.cxnbbs.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import net.cityxen.cxnbbs.domain.Security;

public interface SecurityRepository extends MongoRepository<Security, String> {
	Security findByName(String name);

	Security findByLevel(int level);

	@Query("{level:{$gte:'?0'}}")
	List<Security> findHigherLevels(int level);

	@Query("{level:{$lte:'?0'}}")
	List<Security> findLowerLevels(int level);

}
