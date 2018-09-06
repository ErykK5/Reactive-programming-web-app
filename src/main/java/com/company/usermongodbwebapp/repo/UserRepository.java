package com.company.usermongodbwebapp.repo;

import com.company.usermongodbwebapp.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
