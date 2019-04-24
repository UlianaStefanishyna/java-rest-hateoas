package com.rest.hateoas.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource(path = "users")
public interface UserRepository extends CrudRepository {
}