package carrotMarcket.marcket.board.service;

import carrotMarcket.marcket.board.entity.Comment;
import carrotMarcket.marcket.board.repository.CommentRepository;
import carrotMarcket.marcket.board.repository.BoardRepository;
import carrotMarcket.marcket.board.request.CommentSaveDto;
import carrotMarcket.marcket.board.request.BoardSaveDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommentServiceTest {
    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    void clean() {
        commentRepository.deleteAll();
        boardRepository.deleteAll();
    }

    @Test
    @DisplayName("답변 작성")
    public void save() {
        //given
        BoardSaveDto boardSave = BoardSaveDto.builder()
                .title("title")
                .text("text")
                .build();
        Long save = boardService.save(boardSave);

        CommentSaveDto commentSave = CommentSaveDto.builder()
                .text("comment1")
                .boardId(save)
                .build();
        commentService.save(commentSave);

        //when

        //then
        Assertions.assertEquals(1L, commentRepository.count());
        Comment comment = commentRepository.findAll().get(0);
        Assertions.assertEquals("comment1", comment.getText());
        Assertions.assertEquals(save, comment.getBoard().getId());
    }

    @Test
    @DisplayName("답변 삭제")
    public void delete() {
        //given
        BoardSaveDto save = BoardSaveDto.builder()
                .text("text")
                .title("title")
                .build();
        Long boardId = boardService.save(save);

        CommentSaveDto answerSave = CommentSaveDto.builder()
                .text("comment")
                .boardId(boardId)
                .build();
        commentService.save(answerSave);

        //when
        Comment comment = commentRepository.findAll().get(0);
        commentService.deleteById(comment.getId());

        //then
        Assertions.assertEquals(0L, commentRepository.count());
    }

}