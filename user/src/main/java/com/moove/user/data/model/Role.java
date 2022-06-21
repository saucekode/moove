package com.moove.user.data.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column(name = "role_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length=25, name = "user_roles")
    private UserRoles name;

    public Role(){}

    public Role(UserRoles name){
        this.name = name;
    }
}
