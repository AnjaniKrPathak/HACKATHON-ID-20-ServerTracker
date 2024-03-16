package com.example.servertracker.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerAppLiveStatusReport {
    private  int totalServer;
    private int liveServeCount;
    private int downServerCount;

}
