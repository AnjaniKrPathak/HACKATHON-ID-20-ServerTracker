package com.example.servertracker.server.entity;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ServerAppLiveStatusReport implements Serializable {

    private String serverStaus;
    private int count;
    private int totalCount;


    public ServerAppLiveStatusReport(String serverStaus) {
        this.serverStaus = serverStaus;
    }
}
