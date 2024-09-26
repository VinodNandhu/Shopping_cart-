package com.newproject.dreamshops.request;

import lombok.Data;

@Data
public class CreateUserRequest {

    private String firstname;
    private String lastName;
    private String email;
    private String password;

}
