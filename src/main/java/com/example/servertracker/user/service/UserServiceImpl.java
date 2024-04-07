package com.example.servertracker.user.service;

import com.example.servertracker.server.entity.ServerDashbordDetail;
import com.example.servertracker.server.service.IServerService;
import com.example.servertracker.user.entity.UserDetail;
import com.example.servertracker.user.entity.UserRole;
import com.example.servertracker.user.entity.UserServerDetail;
import com.example.servertracker.user.repo.UserDetailRepo;
import com.example.servertracker.user.repo.UserRoleRepo;
import com.example.servertracker.user.repo.UserServerDetailRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor

@Service
public class UserServiceImpl implements IUserService{


  @Autowired
    UserDetailRepo userDetailRepo;
    @Autowired
    UserServerDetailRepo userServerDetailRepo;
    @Autowired
    UserRoleRepo userRoleRepo;
    @Autowired
    IUserService userService;


    @Override
    public UserDetail addUserDetail(UserDetail userDetail) {
        UserDetail ud=new UserDetail();
        ud.setName(userDetail.getName());
        userDetail.setEmail(userDetail.getEmail());
        userDetail.setPassword(userDetail.getPassword());
        UserRole userRole=userRoleRepo.findByRoleName("ROLE_ADMIN");
        if(userRole==null){
            userRole = checkRoleExist();

        }

        userDetail.setUserRoles(Arrays.asList(userRole));
        return userDetailRepo.saveAndFlush(userDetail);
    }

    private UserRole checkRoleExist() {
      UserRole userRole=new UserRole();
        userRole.setRoleName("ROLE_ADMIN");
        return userRoleRepo.save(userRole);
    }

    @Override
    public UserDetail updateUserDetail(UserDetail userDetail) {
        return userDetailRepo.saveAndFlush(userDetail);
    }

    @Override
    public UserServerDetail addUserServerDetail(UserServerDetail userServerDetail,Long userId) {
        UserDetail userDetail=userService.findUserById(userId);
        UserServerDetail serverDetail=new UserServerDetail();
        serverDetail.setUser(userDetail);
        serverDetail.setDbServerPort(userServerDetail.getDbServerPort());
        serverDetail.setServerIp(userServerDetail.getServerIp());
        serverDetail.setAppUserPassword(userServerDetail.getAppUserPassword());
        serverDetail.setDbUserName(userServerDetail.getDbUserName());
        serverDetail.setDbUserName(userServerDetail.getDbUserName());
        serverDetail.setDbUserPassword(userServerDetail.getDbUserPassword());

        return userServerDetailRepo.save(serverDetail);
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

    @Override
    public List<ServerDashbordDetail> getServerDashbordDetail(Long userId) {
        List<UserServerDetail> userDetailList=getUserServerBasedOnUserId(userId);
        for(UserServerDetail userServerDetail:userDetailList){

        }
        return null;
    }

    @Override
    public UserDetail findUserByEmail(String email) {

        return userDetailRepo.findByEmail(email);
    }
    public UserDetail findUserById(Long userId){
        return userDetailRepo.findById(userId).get();
    }

    @Override
    public UserRole findRoleByRoleName(String roleName) {
        return userRoleRepo.findByRoleName(roleName);
    }

    @Override
    public UserDetail findUserByEmailAndPassword(String userName, String password) {
        return userDetailRepo.findByEmailAndPassword(userName,password);
    }
}
