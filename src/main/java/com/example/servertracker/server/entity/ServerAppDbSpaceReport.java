package com.example.servertracker.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerAppDbSpaceReport {
    private int totalServer;
    private int dbSpaceGreen;
    private int dbSpaceYellow;
    private int dbSpaceRed;

}
