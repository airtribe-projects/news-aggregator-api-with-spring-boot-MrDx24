package com.airtribe.NewAggregator.repository;


import com.airtribe.NewAggregator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);

}
