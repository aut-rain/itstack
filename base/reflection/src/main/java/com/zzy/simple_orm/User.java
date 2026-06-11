package com.zzy.simple_orm;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    private String username;
    private int age;
    private String email;
}
