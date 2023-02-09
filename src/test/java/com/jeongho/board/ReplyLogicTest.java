package com.jeongho.board;

import com.jeongho.board.domain.Board;
import com.jeongho.board.domain.Reply;
import com.jeongho.board.domain.User;
import com.jeongho.board.dto.BoardUpdateDto;
import com.jeongho.board.dto.ReplyUpdateDto;
import com.jeongho.board.repository.BoardMapper;
import com.jeongho.board.repository.ReplyMapper;
import com.jeongho.board.repository.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReplyLogicTest {

    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private BoardMapper boardMapper;
    @Autowired
    private UserMapper userMapper;

    private Board board;
    private User user;

    @BeforeEach
    void initData() {
        user = new User("테스트", "테스트", "테스트");
    }

    @Test
    @DisplayName("reply insert시 pk값이 객체에 넘어오는지 확인")
    void saveLogicTest() {
        // given
        userMapper.save(user);
        board = new Board("테스트", "테스트", user.getNo());
        boardMapper.save(board);
        Reply reply = new Reply("테스트댓글", user.getName(), board.getNo());
        // when
        replyMapper.save(reply);
        // then
        assertThat(reply.getNo()).isNotNull();
    }

    @Test
    @DisplayName("reply update시 영향을 받는 칼럼 수 1이 넘어오는지 확인")
    void updateLogicTest() {
        // given
        userMapper.save(user);
        Board board = new Board("테스트", "테스트내용", user.getNo());
        boardMapper.save(board);
        Reply reply = new Reply("테스트", user.getName(), board.getNo());
        replyMapper.save(reply);
        ReplyUpdateDto replyUpdateDto = new ReplyUpdateDto(reply.getNo(), "댓글 수정 테스트");
        // when
        int count = replyMapper.update(replyUpdateDto);
        // then
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("reply delete시 영향을 받는 칼럼 수 1이 넘어오는지 확인")
    void deleteLogicTest() {
        //given
        userMapper.save(user);
        Board board = new Board("테스트", "테스트내용", user.getNo());
        boardMapper.save(board);
        Reply reply = new Reply("테스트", user.getName(), board.getNo());
        replyMapper.save(reply);
        // when
        int count = replyMapper.delete(reply.getNo());
        // then
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("reply findByNo시 해당하는 reply가 맞는지 확인")
    void findByNoLogicTest() {
        // given
        userMapper.save(user);
        Board board = new Board("테스트", "테스트내용", user.getNo());
        boardMapper.save(board);
        Reply reply = new Reply("테스트", user.getName(), board.getNo());
        replyMapper.save(reply);
        // when
        Reply findReply = replyMapper.findByNo(reply.getNo());
        // then
        assertThat(reply.getContent()).isEqualTo(findReply.getContent());
        assertThat(reply.getWriter()).isEqualTo(findReply.getWriter());
        assertThat(reply.getBoardNo()).isEqualTo(findReply.getBoardNo());
    }

    @Test
    @DisplayName("reply findAll시 추가한 데이터 수가 맞는지 확인")
    void findAllLogicTest() {
        // given
        int preCount = replyMapper.findAll().size();
        userMapper.save(user);
        Board board = new Board("테스트", "테스트내용", user.getNo());
        boardMapper.save(board);
        Reply reply = new Reply("테스트", user.getName(), board.getNo());
        replyMapper.save(reply);
        // when
        int postCount = replyMapper.findAll().size();
        // then
        assertThat(postCount).isEqualTo(preCount+1);
    }
}
