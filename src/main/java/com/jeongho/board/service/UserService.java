package com.jeongho.board.service;

import com.jeongho.board.domain.User;
import com.jeongho.board.dto.UserUpdateDto;
import com.jeongho.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String userId) {
        User findUser = userRepository.findById(userId);
        if (findUser == null) {
            throw new IllegalArgumentException("해당하는 아이디를 지닌 사용자가 존재하지 않습니다.");
        }
        return findUser;
    }

    public void save(User user) {
        if (isDuplicatedUser(user.getId())) {
            throw new IllegalArgumentException("중복된 사용자 아이디가 존재합니다.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }

    private boolean isDuplicatedUser(String userId) {
        User findUser = userRepository.findById(userId);
        if (findUser != null) {
            return true;
        }
        return false;
    }

    public void updateUser(UserUpdateDto userUpdateDto) {
        checkUpdateDto(userUpdateDto);
        if (userUpdateDto.getPassword() != null) {
            userUpdateDto.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        }
        userRepository.update(userUpdateDto);
    }

    private void checkUpdateDto(UserUpdateDto userUpdateDto) {
        if (userUpdateDto.getId() == null) {
            throw new IllegalArgumentException("아이디는 필수 입력 값입니다.");
        }
        if (userUpdateDto.getName() == null && userUpdateDto.getPassword() == null) {
            throw new IllegalArgumentException("업데이트 항목은 최소 1개 이상 입력해야 합니다.");
        }
    }

    public void deleteUser(String userId) {
        if (!isDuplicatedUser(userId)) {
            throw new IllegalArgumentException("다음과 같은 유저는 존재하지 않습니다.");
        }
        userRepository.deleteById(userId);
    }
}
