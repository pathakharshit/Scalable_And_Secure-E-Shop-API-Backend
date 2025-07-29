package com.major_project.e_commerce.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.major_project.e_commerce.enums.UserType;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    @NaturalId
    private String email;
    private String password;
    private String userType;
}
