package com.jeongho.board.repository;

import com.jeongho.board.domain.Board;
import com.jeongho.board.domain.Reply;
import com.jeongho.board.dto.BoardUpdateDto;
import com.jeongho.board.dto.ReplyUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReplyRepository {

    private final ReplyMapper replyMapper;

    public Reply save(Reply reply) {
        replyMapper.save(reply);
        return reply;
    }

    public int update(ReplyUpdateDto replyUpdateDto) {
        return replyMapper.update(replyUpdateDto);
    }

    public int delete(Long replyNo) {
        return replyMapper.delete(replyNo);
    }

    public Reply findByNo(Long replyNo) {
        return replyMapper.findByNo(replyNo);
    }

    public List<Reply> findAll() {
        return replyMapper.findAll();
    }
}
