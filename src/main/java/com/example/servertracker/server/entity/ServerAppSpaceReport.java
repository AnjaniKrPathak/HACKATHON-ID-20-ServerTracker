package com.example.servertracker.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerAppSpaceReport {
    private int totalServer;
    private int appSpaceGreen;
    private int appSpaceYellow;
    private int appSpaceRed;
}
