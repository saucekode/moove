package com.shoplyst.user.data.payload.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
