package com.example.servertracker.user.service;

import com.example.servertracker.user.entity.UserDetail;
import com.example.servertracker.user.entity.UserServerDetail;
import com.example.servertracker.user.repo.UserDetailRepo;
import com.example.servertracker.user.repo.UserServerDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService{
   @Autowired
    UserDetailRepo userDetailRepo;
   @Autowired
    UserServerDetailRepo userServerDetailRepo;
    @Override
    public UserDetail addUserDetail(UserDetail userDetail) {
        return userDetailRepo.saveAndFlush(userDetail);
    }

    @Override
    public UserDetail updateUserDetail(UserDetail userDetail) {
        return userDetailRepo.saveAndFlush(userDetail);
    }

    @Override
    public UserServerDetail addUserServerDetail(UserServerDetail userServerDetail) {

        return userServerDetailRepo.save(userServerDetail);
    }

    @Override
    public List<UserServerDetail> getAllUserServer() {
        return userServerDetailRepo.findAll();
    }

    @Override
    public List<UserServerDetail> getUserServerBasedOnUserId(Long userId) {
        List<UserServerDetail> userServerDetails=userServerDetailRepo.findByUserId(userId);

        return userServerDetails;
    }
}
