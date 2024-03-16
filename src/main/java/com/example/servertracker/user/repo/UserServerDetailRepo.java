package com.example.servertracker.user.repo;

import com.example.servertracker.user.entity.UserServerDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserServerDetailRepo extends JpaRepository<UserServerDetail,Long> {

    List<UserServerDetail> findByUserId(Long userId);
}
