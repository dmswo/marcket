package carrotMarcket.marcket.service;

import carrotMarcket.marcket.board.entity.Answer;
import carrotMarcket.marcket.board.repository.AnswerRepository;
import carrotMarcket.marcket.board.repository.QuestionRepository;
import carrotMarcket.marcket.board.request.AnswerSaveDto;
import carrotMarcket.marcket.board.request.QuestionSaveDto;
import carrotMarcket.marcket.board.service.AnswerService;
import carrotMarcket.marcket.board.service.QuestionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    @DisplayName("답변 작성")
    public void save() {
        //given
        QuestionSaveDto questionSave = QuestionSaveDto.builder()
                .title("title")
                .text("text")
                .build();
        Long save = questionService.save(questionSave);

        AnswerSaveDto answerSave = AnswerSaveDto.builder()
                .text("reply1")
                .questionId(save)
                .build();
        answerService.save(answerSave);

        //when

        //then
        Assertions.assertEquals(1L, answerRepository.count());
        Answer answer = answerRepository.findAll().get(0);
        Assertions.assertEquals("reply1", answer.getText());
        Assertions.assertEquals(save, answer.getQuestion().getId());
    }

    @Test
    @DisplayName("답변 삭제")
    public void delete() {
        //given
        QuestionSaveDto save = QuestionSaveDto.builder()
                .text("text")
                .title("title")
                .build();
        Long questionId = questionService.save(save);

        AnswerSaveDto answerSave = AnswerSaveDto.builder()
                .text("reply")
                .questionId(questionId)
                .build();
        answerService.save(answerSave);

        //when
        Answer answer = answerRepository.findAll().get(0);
        answerService.deleteById(answer.getId());

        //then
        Assertions.assertEquals(0L, answerRepository.count());
    }

}