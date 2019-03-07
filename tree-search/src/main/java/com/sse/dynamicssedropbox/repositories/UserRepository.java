package com.sse.dynamicssedropbox.repositories;

import com.sse.dynamicssedropbox.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    User findByUsername(String username);

}
