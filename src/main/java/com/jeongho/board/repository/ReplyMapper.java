package com.jeongho.board.repository;

import com.jeongho.board.domain.Reply;
import com.jeongho.board.dto.ReplyUpdateDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {

    void save(Reply reply);

    int update(ReplyUpdateDto replyUpdateDto);

    int delete(Long replyNo);

    Reply findByNo(Long replyNo);

    List<Reply> findAll();
}
