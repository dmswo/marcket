package carrotMarcket.marcket.board.controller;

import carrotMarcket.marcket.board.entity.Board;
import carrotMarcket.marcket.board.entity.Comment;
import carrotMarcket.marcket.board.repository.CommentRepository;
import carrotMarcket.marcket.board.repository.BoardRepository;
import carrotMarcket.marcket.board.request.BoardSaveDto;
import carrotMarcket.marcket.board.request.CommentSaveDto;
import carrotMarcket.marcket.board.service.CommentService;
import carrotMarcket.marcket.board.service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    void clean() {
        commentRepository.deleteAll();
        boardRepository.deleteAll();
    }

    @Test
    @DisplayName("답변 작성 성공")
    public void saveSuccess() throws Exception {
        // given
        BoardSaveDto boardSave = BoardSaveDto.builder()
                .text("text1")
                .title("title1")
                .build();

        Long save = boardService.save(boardSave);

        CommentSaveDto replySave = CommentSaveDto.builder()
                .boardId(save)
                .text("comment")
                .build();

        String json = objectMapper.writeValueAsString(replySave);

        // when
        mockMvc.perform(post("/comment/save")
                .contentType(APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk())
                .andDo(print());

        // then
        Assertions.assertEquals(1L, commentRepository.count());
        Comment comment = commentRepository.findAll().get(0);
        Assertions.assertEquals("comment", comment.getText());
        Assertions.assertEquals(save, comment.getBoard().getId());
    }

    @Test
    @DisplayName("답변 삭제 성공")
    public void deleteSuccess() throws Exception {
        // given
        Board board = Board.builder()
                .text("text")
                .title("title")
                .build();

        Comment comment = Comment.builder()
                .text("comment")
                .build();

        boardRepository.save(board);
        commentRepository.save(comment);
        Comment result = commentRepository.findAll().get(0);

        // when
        mockMvc.perform(delete("/comment/delete?commentId="+result.getId())
                .contentType(APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print());

        // then
        Assertions.assertEquals(0L, commentRepository.count());
    }
}