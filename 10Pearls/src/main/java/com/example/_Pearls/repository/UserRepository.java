package com.example._Pearls.repository;

import com.example._Pearls.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
