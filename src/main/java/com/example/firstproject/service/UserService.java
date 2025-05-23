package com.example.firstproject.service;

import com.example.firstproject.entity.User;
import com.example.firstproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean tryLogin(User login){
        User findUser = userRepository.findByLoginIdAndLoginPw(login.getLoginId(), login.getLoginPw());

        return findUser != null;
    }

    public boolean tryRegister(User register){
        User findUser = userRepository.findByLoginId(register.getLoginId());

        if(findUser != null){
            return false;
        }

        userRepository.save(register);
        return true;
    }
}
