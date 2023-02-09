package com.jeongho.board;

import com.jeongho.board.domain.Board;
import com.jeongho.board.domain.User;
import com.jeongho.board.dto.BoardUpdateDto;
import com.jeongho.board.repository.BoardMapper;
import com.jeongho.board.repository.UserMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BoardLogicTest {

    @Autowired
    private BoardMapper boardMapper;
    @Autowired
    private UserMapper userMapper;

    private User user;

    @BeforeEach
    void initData() {
        user = new User("테스트", "테스트", "테스트");
    }

    @Test
    @DisplayName("board insert시 pk값이 객체에 넘어오는지 확인")
    void saveLogicTest() {
        // given
        userMapper.save(user);
        Board board = new Board("테스트게시글", "테스트 게시글 내용입니다.", user.getNo());
        // when
        boardMapper.save(board);
        // then
        assertThat(board.getNo()).isNotNull();
    }

    @Test
    @DisplayName("board update시 영향을 받는 칼럼 수 1이 넘어오는지 확인")
    void updateLogicTest() {
        // given
        userMapper.save(user);
        Board board = new Board("테스트", "테스트내용", user.getNo());
        boardMapper.save(board);
        BoardUpdateDto boardUpdateDto = new BoardUpdateDto(board.getNo(), "수정테스트", "수정테스트내용");
        // when
        int count = boardMapper.update(boardUpdateDto);
        // then
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("board delete시 영향을 받는 칼럼 수 1이 넘어오는지 확인")
    void deleteLogicTest() {
        //given
        userMapper.save(user);
        Board board = new Board("테스트", "테스트내용", user.getNo());
        boardMapper.save(board);
        // when
        int count = boardMapper.delete(board.getNo());
        // then
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("board findByNo시 해당하는 board가 맞는지 확인")
    void findByNoLogicTest() {
        // given
        userMapper.save(user);
        Board board = new Board("테스트", "테스트내용", user.getNo());
        boardMapper.save(board);
        // when
        Board findBoard = boardMapper.findByNo(board.getNo());
        // then
        assertThat(board.getTitle()).isEqualTo(findBoard.getTitle());
        assertThat(board.getContent()).isEqualTo(findBoard.getContent());
        assertThat(board.getUserNo()).isEqualTo(findBoard.getUserNo());
    }

    @Test
    @DisplayName("board findAll시 추가한 데이터 수가 맞는지 확인")
    void findAllLogicTest() {
        // given
        userMapper.save(user);
        int preCount = boardMapper.findAll().size();
        boardMapper.save(new Board("테스트", "테스트내용", user.getNo()));
        // when
        int postCount = boardMapper.findAll().size();
        // then
        assertThat(postCount).isEqualTo(preCount+1);
    }
}
