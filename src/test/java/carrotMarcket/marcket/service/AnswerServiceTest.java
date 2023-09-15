package carrotMarcket.marcket.service;

import carrotMarcket.marcket.entity.Answer;
import carrotMarcket.marcket.entity.Question;
import carrotMarcket.marcket.entity.QuestionStatus;
import carrotMarcket.marcket.repository.AnswerRepository;
import carrotMarcket.marcket.repository.QuestionRepository;
import carrotMarcket.marcket.request.AnswerSaveDto;
import carrotMarcket.marcket.request.QuestionSaveDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AnswerServiceTest {
    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void clean() {
        answerRepository.deleteAll();
        questionRepository.deleteAll();
    }

//    @Test
//    @DisplayName("답변 작성")
//    public void save() {
//        //given
//        QuestionSaveDto questionSave = QuestionSaveDto.builder()
//                .title("title")
//                .text("text")
//                .build();
//        Long save = questionService.save(questionSave);
//
//        AnswerSaveDto answerSave = AnswerSaveDto.builder()
//                .text("reply1")
//                .questionId(save)
//                .build();
//        answerService.save(answerSave);
//
//        //when
//
//        //then
//        Assertions.assertEquals(1L, answerRepository.count());
//        Answer answer = answerRepository.findAll().get(0);
//        Assertions.assertEquals("reply1", answer.getText());
//        Assertions.assertEquals(save, answer.getId());
//    }

//    @Test
//    @DisplayName("답변 삭제")
//    public void delete() {
//        //given
//        QuestionSaveDto save = QuestionSaveDto.builder()
//                .text("text")
//                .title("title")
//                .build();
//
//        AnswerSaveDto answerSave = AnswerSaveDto.builder()
//                .text("reply")
//                .questionId(1L)
//                .build();
//
//        //when
//        questionService.save(save);
//        answerService.save(answerSave);
//
//        Answer answer = answerRepository.findAll().get(0);
//        answerService.deleteById(answer.getId());
//
//        //then
//        Assertions.assertEquals(0L, answerRepository.count());
//    }

}