package com.example.firstproject.repository;

import com.example.firstproject.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByLoginIdAndLoginPw(String loginId, String loginPw);

    User findByLoginId(String loginId);
}
