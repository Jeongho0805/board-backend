package com.jeongho.board.controller;

import com.jeongho.board.domain.User;
import com.jeongho.board.dto.UserUpdateDto;
import com.jeongho.board.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Object> getUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping ("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable String userId) {
        User user = userService.findById(userId);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<Object> saveUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError());
        }
        userService.save(user);
        return ResponseEntity.ok().body("사용자가 등록되었습니다.");
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        userService.updateUser(userUpdateDto);
        return ResponseEntity.ok().body("사용자 수정이 완료되었습니다.");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().body("사용자 삭제가 완료되었습니다.");
    }
}
