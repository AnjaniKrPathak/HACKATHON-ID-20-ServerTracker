package com.example.servertracker.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Set;

@Data
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Table(name = "USER_DETAIL")
public class UserDetail {
    @Id
    @NonNull
    @SequenceGenerator(name="user_generator", sequenceName="USER_DETAIL_SEQ", allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="user_generator")
    @Column(name = "ID")
    private Long id;
    @Column(name = "USER_NAME")
    private String name;
    @Column(name = "USER_PASS")
    private String pass;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PROJECT")
    private String project;
    @Column(name = "STATUS")
    private boolean status;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<UserServerDetail> userServerList;


}
