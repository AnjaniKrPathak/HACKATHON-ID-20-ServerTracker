package com.example.servertracker.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data

@AllArgsConstructor
@Entity
@Table(name = "SERVER_APP_SPACE_DETAIL")
public class ServerAppSpaceDetail {
    @Id
    @SequenceGenerator(name="server_app_space_generator", sequenceName="SERVER_APP_SPACE_DETAIL_SEQ", allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="server_app_space_generator")
    private Long id;
    @Column(name = "SERVER_IP")
    @NonNull
    private String serverIp;
    @Column(name = "FILE_SYSTEM")
    private String fileSystem;
    @Column(name="TOTAL_SIZE")
    private String totalSize;
    @Column(name="SPACE_USED")
    private String used;
    @Column(name="SPACE_AVAIL")
    private String avail;
    @Column(name="USED_PERC")
    private String usedPerc;
    @Column(name="MOUNTED_ON")
    private String mountedOn;

}
