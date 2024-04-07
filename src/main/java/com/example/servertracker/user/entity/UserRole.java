package com.example.servertracker.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ROLE_DETAIL")
public class UserRole {
    @Id
    private Integer id;
    @Column(name = "ROLE_NAME")
    private String roleName;
}
