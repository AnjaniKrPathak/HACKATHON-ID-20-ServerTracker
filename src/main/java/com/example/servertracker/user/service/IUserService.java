package com.example.servertracker.user.service;

import com.example.servertracker.server.entity.ServerDashbordDetail;
import com.example.servertracker.user.entity.UserDetail;
import com.example.servertracker.user.entity.UserRole;
import com.example.servertracker.user.entity.UserServerDetail;

import java.util.List;

public interface IUserService {

    UserDetail addUserDetail(UserDetail userDto);

    UserDetail updateUserDetail(UserDetail userDetail);

    UserServerDetail addUserServerDetail(UserServerDetail userServerDetail);

    List<UserServerDetail> getAllUserServer();

     List<UserServerDetail> getUserServerBasedOnUserId(Long userId);

    List<ServerDashbordDetail> getServerDashbordDetail(Long userId);

    UserDetail findUserByEmail(String email);

    UserRole findRoleByRoleName(String roleName);

    UserDetail findUserByEmailAndPassword(String userName, String password);
}
