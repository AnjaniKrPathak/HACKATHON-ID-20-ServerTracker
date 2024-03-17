package com.example.servertracker.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SERVER_DB_TABLE_SAPCE_DETAIL")
public class ServerDbTableSpaceDetail {
    @Id
    @SequenceGenerator(name="server_table_space_generator", sequenceName="DB_TABLE_SPACE_DETAIL_SEQ", allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="server_table_space_generator")
    private Long id;
    @Column(name = "DB_TABLE_SPACE_NAME")
    private String dbTableSpaceName;
    @Column(name ="SERVER_IP")
    private String serverIp;
    @Column(name = "PCT_USED")
    private double pctUsed;
    @Column(name = "SPACE_ALLOCATED")
    private double spaceAllocated;
    @Column(name = "SPACE_USED")
    private double spaceUsed;
    @Column(name = "SPACE_FREE")
    private double spaceFree;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDbTableSpaceName() {
        return dbTableSpaceName;
    }

    public void setDbTableSpaceName(String dbTableSpaceName) {
        this.dbTableSpaceName = dbTableSpaceName;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public double getPctUsed() {
        return pctUsed;
    }

    public void setPctUsed(double pctUsed) {
        this.pctUsed = pctUsed;
    }

    public double getSpaceAllocated() {
        return spaceAllocated;
    }

    public void setSpaceAllocated(double spaceAllocated) {
        this.spaceAllocated = spaceAllocated;
    }

    public double getSpaceUsed() {
        return spaceUsed;
    }

    public void setSpaceUsed(double spaceUsed) {
        this.spaceUsed = spaceUsed;
    }

    public double getSpaceFree() {
        return spaceFree;
    }

    public void setSpaceFree(double spaceFree) {
        this.spaceFree = spaceFree;
    }


}
