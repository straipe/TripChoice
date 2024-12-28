package com.ssafy12.tripchoice.board;

import com.ssafy12.tripchoice.board.dto.BoardCreateDto;
import com.ssafy12.tripchoice.board.dto.BoardDeleteDto;
import com.ssafy12.tripchoice.board.dto.BoardSearchDto;
import com.ssafy12.tripchoice.board.dto.BoardUpdateDto;
import com.ssafy12.tripchoice.board.dto.response.BoardDto;
import com.ssafy12.tripchoice.common.exception.http.CustomHttp403Exception;
import com.ssafy12.tripchoice.user.User;
import com.ssafy12.tripchoice.user.UserRepository;
import com.ssafy12.tripchoice.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.ObjectUtils.notEqual;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardQueryRepository boardQueryRepository;
    private final UserRepository userRepository;

    @Transactional
    public void create(BoardCreateDto dto) {
        User user = userRepository.findById(dto.userId()).orElseThrow(UserNotFoundException::new);
        boardRepository.save(Board.builder()
                .title(dto.title())
                .content(dto.content())
                .userId(dto.userId())
                .userName(user.getName())
                .build());
    }

    @Transactional
    public void update(BoardUpdateDto dto) {
        Board board = boardRepository.findById(dto.boardId()).orElseThrow(UserNotFoundException::new);
        if (notEqual(dto.userId(), board.getUserId())) {
            throw new CustomHttp403Exception();
        }
        board.update(dto.title(), dto.content());
    }

    @Transactional
    public void delete(BoardDeleteDto dto) {
        Board board = boardRepository.findById(dto.boardId()).orElseThrow(UserNotFoundException::new);
        if (notEqual(dto.userId(), board.getUserId())) {
            throw new CustomHttp403Exception();
        }
        boardRepository.delete(board);
    }

    public BoardDto getById(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(UserNotFoundException::new);
        return toDto(board);
    }

    public Page<BoardDto> search(BoardSearchDto dto) {
        return boardQueryRepository.search(dto).map(this::toDto);
    }

    private BoardDto toDto(Board board) {
        return BoardDto.builder()
                .boardId(board.getId().toString())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getUserName())
                .createdAt(board.getCreatedAt().toString())
                .lastModifiedAt(board.getLastModifiedAt().toString())
                .build();
    }
}