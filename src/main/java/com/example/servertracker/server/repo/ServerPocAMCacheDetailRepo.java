package com.example.servertracker.server.repo;

import com.example.servertracker.server.entity.ServerPocAmCacheDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface ServerPocAMCacheDetailRepo extends JpaRepository<ServerPocAmCacheDetail,Long> {
}
