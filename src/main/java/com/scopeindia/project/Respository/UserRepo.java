package com.scopeindia.project.Respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scopeindia.project.Model.User;

public interface UserRepo extends JpaRepository<User, Integer> {


User findByemail(String email);
}
