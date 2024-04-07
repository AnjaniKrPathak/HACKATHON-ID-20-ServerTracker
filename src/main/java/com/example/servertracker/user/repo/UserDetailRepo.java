package com.example.servertracker.user.repo;

import com.example.servertracker.user.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepo extends JpaRepository<UserDetail,Long> {
    UserDetail findByEmail(String email);

    UserDetail findByEmailAndPassword(String userName, String password);
}
