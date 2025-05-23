package com.example.firstproject.dto;

import com.example.firstproject.entity.User;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegistRequestDto {
    private String registId;
    private String registPw;

    public User toEntity() {
        return new User(null, registId, registPw);
    }
}
