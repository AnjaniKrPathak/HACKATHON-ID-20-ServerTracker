package com.example.servertracker.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
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

    public UserDetail(@NonNull Long id) {
        this.id = id;
    }

    @Column(name = "EMAIL",nullable = false,unique = true)
    private String email;
    @Column(name = "PROJECT")
    private String project;
    @Column(name = "STATUS")
    private boolean status;
    @Column(name = "PASSWORD")
    private String password;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<UserServerDetail> userServerList;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles" ,joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<UserRole> userRoles;


}
