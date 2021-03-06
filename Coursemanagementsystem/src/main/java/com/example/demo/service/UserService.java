package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.domain.User;
import com.example.demo.repositories.UserRepo;

import java.util.List;
import java.util.Optional;

@Service
public class UserService<T extends User> {
    private final UserRepo<T> userRepo;

    @Autowired
    public UserService(UserRepo<T> userRepo) {
        this.userRepo = userRepo;
    }

    public T findOne(T user) {
        Optional<T> op = userRepo.findById(user.getUserId());
        return op.orElse(null);
    }

    public T findOne(String userId) {
        Optional<T> op = userRepo.findById(userId);
        return op.orElse(null);
    }

    public List<T> findAll() {
        return userRepo.findAll();
    }

    public T insert(T user) {
        if (userRepo.existsById(user.getUserId())) {
            throw new IllegalArgumentException("This user has existed.");
        }
        return userRepo.save(user);
    }

    public T update(T user) {
        if (userRepo.existsById(user.getUserId())) {
            return userRepo.save(user);
        }
        throw new IllegalArgumentException("Cannot find such user.");
    }

    public T save(T user) {
        return userRepo.save(user);
    }
}
