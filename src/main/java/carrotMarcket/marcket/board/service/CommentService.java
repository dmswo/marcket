package carrotMarcket.marcket.board.service;

import carrotMarcket.marcket.board.entity.Board;
import carrotMarcket.marcket.board.event.CommentMailEvent;
import carrotMarcket.marcket.board.exception.CommentBusinessException;
import carrotMarcket.marcket.board.exception.CommentExceptionCode;
import carrotMarcket.marcket.board.repository.CommentRepository;
import carrotMarcket.marcket.board.repository.BoardRepository;
import carrotMarcket.marcket.board.entity.Comment;
import carrotMarcket.marcket.board.exception.BoardBusinessException;
import carrotMarcket.marcket.board.exception.BoardExceptionCode;
import carrotMarcket.marcket.board.request.CommentSaveDto;
import carrotMarcket.marcket.board.request.CommentUpdateDto;
import carrotMarcket.marcket.board.response.CommentListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final ApplicationEventPublisher eventPublisher;

    public Page commentList(Pageable pageable) {
        Page<Comment> list = commentRepository.findAll(pageable);
        return new CommentListResponse().toDto(list);
    }

    @Transactional
    public Long save(CommentSaveDto commentSaveDto) {
        Board board = boardRepository.findById(commentSaveDto.getBoardId())
                .orElseThrow(() -> new BoardBusinessException(BoardExceptionCode.NOT_EXIST_BOARD));

        Comment comment = Comment.builder()
                .text(commentSaveDto.getText())
                .board(board)
                .build();
        Comment save = commentRepository.save(comment);
        CommentMailEvent email = CommentMailEvent
                .builder()
                .title(board.getTitle())
                .email(board.getRegID())
                .build();
        eventPublisher.publishEvent(email);

        return save.getId();
    }

    @Transactional
    public void update(Long id, CommentUpdateDto commentUpdateDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentBusinessException(CommentExceptionCode.NOT_EXIST_COMMENT));
        comment.update(commentUpdateDto.getText());
    }

    @Transactional
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
