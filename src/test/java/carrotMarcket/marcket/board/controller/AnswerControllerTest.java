package carrotMarcket.marcket.board.controller;

import carrotMarcket.marcket.board.entity.Answer;
import carrotMarcket.marcket.board.entity.Board;
import carrotMarcket.marcket.board.repository.AnswerRepository;
import carrotMarcket.marcket.board.repository.BoardRepository;
import carrotMarcket.marcket.board.request.AnswerSaveDto;
import carrotMarcket.marcket.board.request.BoardSaveDto;
import carrotMarcket.marcket.board.service.AnswerService;
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
class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerRepository answerRepository;

    @BeforeEach
    void clean() {
        answerRepository.deleteAll();
        boardRepository.deleteAll();
    }

//    @Test
//    @DisplayName("답변 작성 성공")
//    public void saveSuccess() throws Exception {
//        // given
//        BoardSaveDto questionSave = BoardSaveDto.builder()
//                .text("text1")
//                .title("title1")
//                .build();
//
//        Long save = boardService.save(questionSave);
//
//        AnswerSaveDto replySave = AnswerSaveDto.builder()
//                .questionId(save)
//                .text("reply")
//                .build();
//
//        String json = objectMapper.writeValueAsString(replySave);
//
//        // when
//        mockMvc.perform(post("/answer/save")
//                .contentType(APPLICATION_JSON)
//                .content(json)
//        )
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        // then
//        Assertions.assertEquals(1L, answerRepository.count());
//        Answer answer = answerRepository.findAll().get(0);
//        Assertions.assertEquals("reply", answer.getText());
//        Assertions.assertEquals(save, answer.getId());
//    }
//
//    @Test
//    @DisplayName("답변 삭제 성공")
//    public void deleteSuccess() throws Exception {
//        // given
//        Board board = Board.builder()
//                .text("text")
//                .title("title")
//                .build();
//
//        Answer answer = Answer.builder()
//                .text("reply")
//                .build();
//
//        boardRepository.save(board);
//        answerRepository.save(answer);
//        Answer result = answerRepository.findAll().get(0);
//
//        // when
//        mockMvc.perform(delete("/answer/delete/{answerId}", result.getId())
//                .contentType(APPLICATION_JSON)
//        )
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        // then
//        Assertions.assertEquals(0L, answerRepository.count());
//    }
}