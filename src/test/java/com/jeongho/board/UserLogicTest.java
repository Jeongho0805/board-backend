package com.jeongho.board;

import com.jeongho.board.domain.User;
import com.jeongho.board.dto.UserUpdateDto;
import com.jeongho.board.repository.BoardRepository;
import com.jeongho.board.repository.UserMapper;
import com.jeongho.board.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserLogicTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    @DisplayName("save 작업 시 pk 값이 정상적으로 반환되는지 테스트")
    void saveLogicTest() {
        // given
        User user = new User("박정호","pjhpjh0805","wjddms1004");
        // when
        userMapper.save(user);
        // then
        assertThat(user.getNo()).isNotNull();
    }

    @Test
    @DisplayName("update 작업 성공 여부 테스트")
    void updateLogicTest() {
        // given
        UserUpdateDto userUpdateDto = new UserUpdateDto("수정된이름", "ppjje1105", "수정된비밀번호");
        // when
        int count = userMapper.update(userUpdateDto);
        // then
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("delete 작업 성공 여부 테스트")
    void deleteLogicTest() {
        //given
        String id = "pjhpjh0805";
        // when
        userMapper.save(new User("박정호","pjhpjh0805", "wjddms1004"));
        int count = userMapper.deleteById(id);
        // then
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("findById 작업 성공 여부 테스트")
    void findByIdLogicTest() {
        // given
        User user = new User("박정호", "pjhpjh0805", "wjddms1004");
        // when
        userMapper.save(user);
        User findUser = userMapper.findById("pjhpjh0805");
        // then
        assertThat(findUser.getId()).isEqualTo(user.getId());
        assertThat(findUser.getName()).isEqualTo(user.getName());
        assertThat(findUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    @DisplayName("findAll 작업 성공 여부 테스트")
    void findAllLogicTeset() {
        // given
        User user1 = new User("123","123","123");
        User user2 = new User("123","124","123");
        User user3 = new User("123","125","123");
        // when
        int preSize = userMapper.findAll().size();
        userMapper.save(user1);
        userMapper.save(user2);
        userMapper.save(user3);
        // then
        int postSize = userMapper.findAll().size();
        assertThat(postSize - 3).isEqualTo(preSize);
    }
}
