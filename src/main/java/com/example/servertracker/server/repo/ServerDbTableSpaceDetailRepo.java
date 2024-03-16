package com.example.servertracker.server.repo;

import com.example.servertracker.server.entity.ServerDbTableSpaceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerDbTableSpaceDetailRepo extends JpaRepository<ServerDbTableSpaceDetail,Long> {
}
