package com.lz.cloud.dao;

import com.lz.cloud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Long> {
    UserDetails findByUsername(String username);
}
