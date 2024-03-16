package com.example.servertracker.server.controller;

import com.example.servertracker.server.entity.ServerDbTableSpaceDetail;
import com.example.servertracker.server.entity.ServerPocAmCacheDetail;
import com.example.servertracker.server.service.IServerService;
import com.example.servertracker.user.entity.UserServerDetail;
import com.example.servertracker.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/server")
public class ServerController {
    @Autowired
    IUserService userService;
    @Autowired
    IServerService serverService;
    @PostMapping("/saveServerPocAMCacheDetail")
    public ResponseEntity<?> savePocAMCacheStatus(@RequestBody ServerPocAmCacheDetail serverPocAmCacheDetail){


              ServerPocAmCacheDetail caheDetail= serverService.saveServerPocAMCachedetail(serverPocAmCacheDetail);
               return new ResponseEntity<>(caheDetail, HttpStatus.OK);

    }
    @PostMapping("/saveServerDbTableSpaceDetail")
    public ResponseEntity<?> saveServerDbTableSpaceDetail(@RequestBody ServerDbTableSpaceDetail serverDbTableSpaceDetail){
        ServerDbTableSpaceDetail dbTableSpaceDetail=serverService.saveServerDbTableSpaceDetail(serverDbTableSpaceDetail);
        return new ResponseEntity<>(dbTableSpaceDetail,HttpStatus.OK);

    }



}
