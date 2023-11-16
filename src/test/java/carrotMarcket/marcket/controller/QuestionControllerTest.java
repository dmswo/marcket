package carrotMarcket.marcket.controller;

import carrotMarcket.marcket.board.entity.Question;
import carrotMarcket.marcket.board.entity.QuestionStatus;
import carrotMarcket.marcket.board.repository.QuestionRepository;
import carrotMarcket.marcket.board.request.QuestionEdit;
import carrotMarcket.marcket.board.request.QuestionListDto;
import carrotMarcket.marcket.board.request.QuestionSaveDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void clean() {
        questionRepository.deleteAll();
    }

//    @Test
//    @DisplayName("질문글 여러개 조회")
//    void list() throws Exception {
//        // given
//        List<Question> list = IntStream.range(0, 25)
//                .mapToObj(i -> Question.builder()
//                        .title("title" + i)
//                        .text("text" + i)
//                        .build()
//                ).collect(Collectors.toList());
//        questionRepository.saveAll(list);
//
//        QuestionListDto search = QuestionListDto.builder()
//                .build();
//
//        String json = objectMapper.writeValueAsString(search);
//
//        // expected
//        mockMvc.perform(get("/question/questionList")
//                .contentType(APPLICATION_JSON)
//                .content(json)
//        )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content.length()", is(10)))
//                .andExpect(jsonPath("$.content[9].title").value("title9"))
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("질문글 작성 성공")
//    public void saveSuccess() throws Exception {
//        // given
//        QuestionSaveDto save = QuestionSaveDto.builder()
//                .text("text1")
//                .title("title1")
//                .build();
//
//        String json = objectMapper.writeValueAsString(save);
//
//        // when
//        mockMvc.perform(post("/question/save")
//                .contentType(APPLICATION_JSON)
//                .content(json)
//        )
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        // then
//        Assertions.assertEquals(1L, questionRepository.count());
//        Question question = questionRepository.findAll().get(0);
//        Assertions.assertEquals("text1", question.getText());
//        Assertions.assertEquals("title1", question.getTitle());
//        Assertions.assertEquals(QuestionStatus.WAIT, question.getQuestionStatus());
//    }
//
//    @Test
//    @DisplayName("질문글 작성 실패(text or title 비어있을경우)")
//    public void saveFail() throws Exception {
//        // given
//        QuestionSaveDto save = QuestionSaveDto.builder()
//                .text("text1")
//                .build();
//
//        String json = objectMapper.writeValueAsString(save);
//
//        // expect
//        mockMvc.perform(post("/question/save")
//                .contentType(APPLICATION_JSON)
//                .content(json)
//        )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value("400"))
//                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
//                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요."))
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("질문글 수정 성공")
//    public void updateSuccess() throws Exception {
//        // given
//        Question save = Question.builder()
//                .text("text1")
//                .title("title1")
//                .build();
//        questionRepository.save(save);
//
//        QuestionEdit questionEdit = QuestionEdit.builder()
//                .text("updateText1")
//                .title("updateTitle1")
//                .build();
//
//        String json = objectMapper.writeValueAsString(questionEdit);
//
//        // when
//        mockMvc.perform(patch("/question/update/{questionId}", save.getId())
//                .contentType(APPLICATION_JSON)
//                .content(json)
//        )
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        // then
//        Assertions.assertEquals(1L, questionRepository.count());
//        Question question = questionRepository.findAll().get(0);
//        Assertions.assertEquals("updateText1", question.getText());
//        Assertions.assertEquals("updateTitle1", question.getTitle());
//    }
//
//    @Test
//    @DisplayName("질문글 수정 실패")
//    public void updateFail() throws Exception {
//        // given
//        Question save = Question.builder()
//                .text("text1")
//                .title("title1")
//                .build();
//        questionRepository.save(save);
//
//        QuestionEdit questionEdit = QuestionEdit.builder()
//                .text("updateText1")
//                .title("")
//                .build();
//
//        String json = objectMapper.writeValueAsString(questionEdit);
//
//        // expect
//        mockMvc.perform(patch("/question/update/{questionId}", save.getId())
//                .contentType(APPLICATION_JSON)
//                .content(json)
//        )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value("400"))
//                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
//                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요"))
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("질문글 삭제 성공")
//    public void deleteSuccess() throws Exception {
//        // given
//         Question question = Question.builder()
//                .text("text1")
//                .title("title1")
//                .build();
//
//        questionRepository.save(question);
//        Question result = questionRepository.findAll().get(0);
//
//        // when
//        mockMvc.perform(delete("/question/delete?id="+result.getId())
//                .contentType(APPLICATION_JSON)
//        )
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        // then
//        Assertions.assertEquals(0L, questionRepository.count());
//    }
}