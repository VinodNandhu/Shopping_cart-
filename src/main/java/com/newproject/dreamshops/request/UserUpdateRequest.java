package com.newproject.dreamshops.request;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String email;
    private String password;
}
