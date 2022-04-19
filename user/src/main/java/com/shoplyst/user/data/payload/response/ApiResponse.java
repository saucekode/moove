package com.shoplyst.user.data.payload.response;

import lombok.Data;

@Data
public class ApiResponse {
    private Boolean isSuccessful;
    private String message;

    public ApiResponse(Boolean isSuccessful, String message){
        this.isSuccessful = isSuccessful;
        this.message = message;
    }
}
