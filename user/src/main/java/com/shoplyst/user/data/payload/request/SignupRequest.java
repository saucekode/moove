package com.shoplyst.user.data.payload.request;

import lombok.Data;

import java.util.Set;

@Data
public class SignupRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private Set<String> roles;

}
