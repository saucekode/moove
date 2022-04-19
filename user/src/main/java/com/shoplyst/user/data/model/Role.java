package com.shoplyst.user.data.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length=25)
    private UserRoles userRoles;

    public Role(){}

    public Role(UserRoles userRoles){
        this.userRoles = userRoles;
    }
}
