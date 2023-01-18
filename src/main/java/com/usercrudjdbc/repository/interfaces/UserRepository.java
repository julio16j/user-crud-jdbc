package com.usercrudjdbc.repository.interfaces;

import java.util.List;
import java.util.Optional;

import com.usercrudjdbc.model.entity.User;

public interface UserRepository {
    User save(User user);
    Optional<User> findOne(Long id);
    List<User> findAll();
    void delete(Long id);
    User update(User user);
    boolean existsByEmail(String email);
}