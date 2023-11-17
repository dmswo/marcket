package carrotMarcket.marcket.board.service;

import carrotMarcket.marcket.board.entity.Board;
import carrotMarcket.marcket.board.repository.CommentRepository;
import carrotMarcket.marcket.board.repository.BoardRepository;
import carrotMarcket.marcket.board.entity.Comment;
import carrotMarcket.marcket.board.exception.BoardBusinessException;
import carrotMarcket.marcket.board.exception.BoardExceptionCode;
import carrotMarcket.marcket.board.request.CommentSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentSaveDto commentSaveDto) {
        Board board = boardRepository.findById(commentSaveDto.getBoardId())
                .orElseThrow(() -> new BoardBusinessException(BoardExceptionCode.NOT_EXIST_BOARD));

        Comment comment = Comment.builder()
                .text(commentSaveDto.getText())
                .board(board)
                .build();
        Comment save = commentRepository.save(comment);

        return save.getId();
    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
