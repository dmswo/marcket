package carrotMarcket.marcket.board.service;

import carrotMarcket.marcket.board.entity.Board;
import carrotMarcket.marcket.board.entity.Comment;
import carrotMarcket.marcket.board.exception.CommentBusinessException;
import carrotMarcket.marcket.board.exception.CommentExceptionCode;
import carrotMarcket.marcket.board.repository.CommentRepository;
import carrotMarcket.marcket.board.repository.BoardRepository;
import carrotMarcket.marcket.board.request.*;
import carrotMarcket.marcket.board.response.BoardListResponse;
import carrotMarcket.marcket.board.response.CommentListResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    @DisplayName("댓글 여러개 조회")
    public void list() {
        //given
        List<Comment> list = IntStream.range(0, 25)
                .mapToObj(i -> Comment.builder()
                        .text("text" + i)
                        .build()
                ).collect(Collectors.toList());

        commentRepository.saveAll(list);

        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page page = commentService.commentList(pageable);

        //then
        List<CommentListResponse> content = page.getContent();
        assertEquals(10L, page.getSize());
        assertEquals(3L, page.getTotalPages());
        assertEquals(10L, content.size());
        assertEquals("text9", content.get(9).getText());
    }

    @Test
    @DisplayName("댓글 작성")
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
    @DisplayName("댓글 수정")
    public void update() {
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
        Long commentId = commentService.save(commentSave);

        CommentUpdateDto update = CommentUpdateDto.builder()
                .text("updateText1")
                .build();

        //when
        commentService.update(commentId, update);

        //then
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentBusinessException(CommentExceptionCode.NOT_EXIST_COMMENT));
        Assertions.assertEquals("updateText1", comment.getText());
    }

    @Test
    @DisplayName("댓글 삭제")
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