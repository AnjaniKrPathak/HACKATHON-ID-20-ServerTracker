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
public class UserDetail {
    @Id
    @NonNull
    @SequenceGenerator(name="user_generator", sequenceName="USER_DETAIL_SEQ", allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="user_generator")
    private Long id;
    private String name;
    private String email;
    private String project;
    private boolean status;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<UserServerDetail> userServerDetails;


}
