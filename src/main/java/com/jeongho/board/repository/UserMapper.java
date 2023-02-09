package com.jeongho.board.repository;

import com.jeongho.board.domain.Board;
import com.jeongho.board.domain.User;
import com.jeongho.board.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface UserMapper {

    void save(User user);

    int update(UserUpdateDto userUpdateDto);

    int deleteById(String id);

    User findById(String id);

    User findByNo(Long no);

    List<User> findAll();
}
