package com.tiyironyard;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Taylor on 6/17/16.
 */
public interface UserRepository extends CrudRepository<User, Integer>{

    User findByUserEmail(String userEmail);
}
