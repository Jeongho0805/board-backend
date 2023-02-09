package com.jeongho.board.repository;

import com.jeongho.board.domain.Board;
import com.jeongho.board.dto.BoardUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {

    void save(Board board);

    int update(BoardUpdateDto boardUpdateDto);

    int delete(Long boardNo);

    Board findByNo(Long boardNo);

    List<Board> findAll(@Param("offset") int offset, @Param("limit") int limit, @Param("searchQuery") String searchQuery);

    long getTotalCount(String searchQuery);
}
