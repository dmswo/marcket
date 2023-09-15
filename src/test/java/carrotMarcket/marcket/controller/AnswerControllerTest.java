package carrotMarcket.marcket.controller;

import carrotMarcket.marcket.entity.Answer;
import carrotMarcket.marcket.entity.Question;
import carrotMarcket.marcket.entity.QuestionStatus;
import carrotMarcket.marcket.repository.AnswerRepository;
import carrotMarcket.marcket.repository.QuestionRepository;
import carrotMarcket.marcket.request.AnswerSaveDto;
import carrotMarcket.marcket.request.QuestionSaveDto;
import carrotMarcket.marcket.service.AnswerService;
import carrotMarcket.marcket.service.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
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
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerRepository answerRepository;

    @BeforeEach
    void clean() {
        answerRepository.deleteAll();
        questionRepository.deleteAll();
    }

    @Test
    @DisplayName("답변 작성 성공")
    public void saveSuccess() throws Exception {
        // given
        QuestionSaveDto questionSave = QuestionSaveDto.builder()
                .text("text1")
                .title("title1")
                .build();

        Long save = questionService.save(questionSave);

        AnswerSaveDto replySave = AnswerSaveDto.builder()
                .questionId(save)
                .text("reply")
                .build();

        String json = objectMapper.writeValueAsString(replySave);

        // when
        mockMvc.perform(post("/answer/save")
                .contentType(APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk())
                .andDo(print());

        // then
        Assertions.assertEquals(1L, answerRepository.count());
        Answer answer = answerRepository.findAll().get(0);
        Assertions.assertEquals("reply", answer.getText());
        Assertions.assertEquals(save, answer.getId());
    }

    @Test
    @DisplayName("답변 삭제 성공")
    public void deleteSuccess() throws Exception {
        // given
        Question question = Question.builder()
                .text("text")
                .title("title")
                .build();

        Answer answer = Answer.builder()
                .text("reply")
                .build();

        questionRepository.save(question);
        answerRepository.save(answer);
        Answer result = answerRepository.findAll().get(0);

        // when
        mockMvc.perform(delete("/answer/delete/{answerId}", result.getId())
                .contentType(APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print());

        // then
        Assertions.assertEquals(0L, answerRepository.count());
    }
}