package com.example.firstproject.dto;

import com.example.firstproject.entity.User;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    private String loginId;
    private String loginPw;

    public User toEntity() {
        return new User(null, loginId, loginPw);
    }
}
