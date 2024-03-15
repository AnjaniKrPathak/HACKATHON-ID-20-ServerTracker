package com.example.servertracker.user.service;

import com.example.servertracker.user.entity.UserDetail;
import com.example.servertracker.user.repo.UserDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService{
   @Autowired
    UserDetailRepo userDetailRepo;
    @Override
    public UserDetail addUserDetail(UserDetail userDetail) {
        return userDetailRepo.saveAndFlush(userDetail);
    }
}
