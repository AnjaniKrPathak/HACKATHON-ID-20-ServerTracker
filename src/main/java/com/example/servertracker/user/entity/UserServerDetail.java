package com.example.servertracker.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_SERVER_DETAIL")
public class UserServerDetail  {

    @Id
    @Column(name = "SERVER_ID")
    @SequenceGenerator(name="user_server_generator", sequenceName="USER_SERVER_DETAIL_SEQ", allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="user_server_generator")
    private Long serverId;
    @Column(name = "SERVER_IP")
    private String serverIp;
    @Column(name = "DB_USER_NAME")
    private String dbUserName;
    @Column(name = "DB_USER_PASS")
    private String dbUserPassword;
    @Column(name = "APP_USER_NAME")
    private String appUserName;
    @Column(name = "APP_USER_PASSWORD")
    private String appUserPassword;
    @Column(name = "APP_SERVER_PORT")
    private String appServerPort;
    @Column(name = "DB_SERVER_PORT")
    private String dbServerPort;
    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID" ,nullable = false)
    private UserDetail user;

    public void SetServerIp(String serverIp) {
        this.serverIp=serverIp;
    }
    public void SetDbUserName(String dbUserName) {
        this.dbUserName=dbUserName;
    }
    public void SetDbUserPassword(String dbUserPassword) {
        this.dbUserPassword=dbUserPassword;
    }

}
