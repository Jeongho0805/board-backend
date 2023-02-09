package com.jeongho.board.service;

import com.jeongho.board.domain.Reply;
import com.jeongho.board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;


    public List<Reply> findAll() {
        return new ArrayList<>();
    }

    public Reply findByNo(Long replyNo) {
        return new Reply();
    }
}

