package com.santosh.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.santosh.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long > {

}
