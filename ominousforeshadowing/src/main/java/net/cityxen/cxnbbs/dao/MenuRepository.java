package net.cityxen.cxnbbs.dao;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.cityxen.cxnbbs.domain.Menu;

public interface MenuRepository extends MongoRepository<Menu, String>  {
	
    // convenience method to provide a 'code -> object' mapping of all Foos
    default Map<String, Menu> mapAll() {
        return findAll().stream().collect(Collectors.toMap(o -> o.getAction(), o -> o));
    }
	
	
}
