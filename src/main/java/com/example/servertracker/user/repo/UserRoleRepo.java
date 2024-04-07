package com.example.servertracker.user.repo;

import com.example.servertracker.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepo extends JpaRepository<UserRole,Long> {

    UserRole findByRoleName(String roleName);
}
