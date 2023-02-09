package com.jeongho.board.repository;

import com.jeongho.board.domain.Board;
import com.jeongho.board.domain.User;
import com.jeongho.board.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserMapper userMapper;

    public User save(User user) {
        userMapper.save(user);
        return user;
    }

    public int update(UserUpdateDto userUpdateDto) {
        return userMapper.update(userUpdateDto);
    }

    public int deleteById(String id) {
        return userMapper.deleteById(id);
    }

    public User findById(String id) {
        return userMapper.findById(id);
    }

    public User findByNo(Long no) {
        return userMapper.findByNo(no);
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }


}
