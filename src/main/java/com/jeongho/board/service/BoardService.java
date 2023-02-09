package com.jeongho.board.service;

import com.jeongho.board.domain.Board;
import com.jeongho.board.domain.User;
import com.jeongho.board.dto.BoardDto;
import com.jeongho.board.dto.BoardUpdateDto;
import com.jeongho.board.repository.BoardRepository;
import com.jeongho.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public List<BoardDto> findAll(int offset, int limit, String searchQuery) {
        return boardRepository.findAll(offset, limit, searchQuery).stream()
                .map(board -> new BoardDto(board.getNo(), board.getTitle(), board.getContent(), userRepository.findByNo(board.getUserNo()).getName()))
                .collect(Collectors.toList());
    }

    public Board findByNo(Long boardNo) {
        Board findBoard = boardRepository.findByNo(boardNo);
        if (findBoard == null) {
            throw new IllegalArgumentException("해당하는 번호를 지닌 게시글이 존재하지 않습니다.");
        }
        return findBoard;
    }

    public void save(Board board) {
        boardRepository.save(board);
    }

    public void update(BoardUpdateDto boardUpdateDto, User user) {
        Board findBoard = findByNo(boardUpdateDto.getNo());
        checkUpdateDto(boardUpdateDto);
        writerCheck(user, findBoard);
        boardRepository.update(boardUpdateDto);
    }

    private void writerCheck(User user, Board findBoard) {
        Long writerNo = findBoard.getUserNo();
        User findUser = userRepository.findByNo(writerNo);
        if (findUser.getNo() != user.getNo()) {
            throw new IllegalArgumentException("작성자가 일치하지 않아 해당 작업을 수행할 수 없습니다.");
        }
    }

    private void checkUpdateDto(BoardUpdateDto boardUpdateDto) {
        if (boardUpdateDto.getTitle() == null && boardUpdateDto.getContent() == null) {
            throw new IllegalArgumentException("업데이트 항목은 최소 1개 이상 입력해야 합니다");
        }
    }

    public void delete(Long boardNo, User user) {
        Board findBoard = findByNo(boardNo);
        writerCheck(user, findBoard);
        boardRepository.delete(boardNo);
    }

    public long getTotalCount(String searchQuery) {
        return boardRepository.getTotalCount(searchQuery);
    }
}
