package com.example.servertracker.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SERVER_DASHBORD_DETAIL")
@Entity
public class ServerDashbordDetail {
    @Id
    @SequenceGenerator(name="server_dashbord_detail_generator", sequenceName="SERVER_DASHBORD_DETAIL_SEQ", allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="server_dashbord_detail_generator")
    private Long id;
    @Column(name = "DB_TABLE_SPACE_OCCUPY_PERC")
    private String dbTableSpaceOccupyPerc;
    @Column(name = "APP_SPACE_USED_PERC")
    private String appSpaceUsedPerc;
    @Column(name = "SERVER_CACHE_STATUS")
    private String serverCacheStatus;
    @Column(name = "SERVER_IP")
    private String serverIp;

    public ServerDashbordDetail(String serverIp) {
        this.serverIp = serverIp;
    }

    public ServerDashbordDetail(String serverIp, String appServerStatus) {

        this.serverIp = serverIp;
        this.appServerStatus = appServerStatus;
    }

    @Column(name = "LIVE_BILLING_CATALOG")
    private String liveBillingCatalog;

    @Column(name = "APP_SERVER_STATUS")
    private String appServerStatus;


    public String getDbTableSpaceOccupyPerc() {
        return dbTableSpaceOccupyPerc;
    }

    public void setDbTableSpaceOccupyPerc(String dbTableSpaceOccupyPerc) {
        this.dbTableSpaceOccupyPerc = dbTableSpaceOccupyPerc;
    }

    public String getAppSpaceUsedPerc() {
        return appSpaceUsedPerc;
    }

    public void setAppSpaceUsedPerc(String appSpaceUsedPerc) {
        this.appSpaceUsedPerc = appSpaceUsedPerc;
    }

    public String getServerCacheStatus() {
        return serverCacheStatus;
    }

    public void setServerCacheStatus(String serverCacheStatus) {
        this.serverCacheStatus = serverCacheStatus;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getLiveBillingCatalog() {
        return liveBillingCatalog;
    }

    public void setLiveBillingCatalog(String liveBillingCatalog) {
        this.liveBillingCatalog = liveBillingCatalog;
    }

    public String getAppServerStatus() {
        return appServerStatus;
    }

    public void setAppServerStatus(String appServerStatus) {
        this.appServerStatus = appServerStatus;
    }

}
