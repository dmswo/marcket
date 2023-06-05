package carrotMarcket.marcket.service;

import carrotMarcket.marcket.entity.Question;
import carrotMarcket.marcket.entity.QuestionStatus;
import carrotMarcket.marcket.repository.QuestionRepository;
import carrotMarcket.marcket.request.QuestionEdit;
import carrotMarcket.marcket.request.QuestionListDto;
import carrotMarcket.marcket.request.QuestionSaveDto;
import carrotMarcket.marcket.response.QuestionListResponse;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void clean() {
        questionRepository.deleteAll();
    }

    @Test
    @DisplayName("질문글 여러개 조회")
    public void list() {
        //given
        List<Question> list = IntStream.range(0, 25)
                .mapToObj(i -> Question.builder()
                        .text("text" + i)
                        .title("title" + i)
                        .build()
                ).collect(Collectors.toList());

        questionRepository.saveAll(list);

        Pageable pageable = PageRequest.of(0, 10);

        //when
        QuestionListDto search = QuestionListDto.builder()
                .build();
        Page page = questionService.questionList(search, pageable);

        //then
        List<QuestionListResponse> content = page.getContent();
        assertEquals(10L, page.getSize());
        assertEquals(3L, page.getTotalPages());
        assertEquals(10L, content.size());
        assertEquals("title9", content.get(9).getTitle());
    }

    @Test
    @DisplayName("질문글 작성")
    public void save() {
        //given
        QuestionSaveDto save = QuestionSaveDto.builder()
                .text("text1")
                .title("title1")
                .build();

        //when
        questionService.save(save);

        //then
        Assertions.assertEquals(1L, questionRepository.count());
        Question question = questionRepository.findAll().get(0);
        Assertions.assertEquals("text1", question.getText());
        Assertions.assertEquals("title1", question.getTitle());
        Assertions.assertEquals(QuestionStatus.WAIT, question.getQuestionStatus());
    }

    @Test
    @DisplayName("질문글 수정")
    public void update() {
        //given
        Question save = Question.builder()
                .text("saveText1")
                .title("saveTitle1")
                .build();

        questionRepository.save(save);

        QuestionEdit update = QuestionEdit.builder()
                .text("updateText1")
                .title("updateTitle1")
                .build();

        //when
        questionService.edit(save.getId(), update);

        //then
        Question question1 = questionRepository.findById(save.getId()).orElseThrow(() -> new RuntimeException("질문글이 존재하지 않습니다."));
        Assertions.assertEquals("updateText1", question1.getText());
        Assertions.assertEquals("updateTitle1", question1.getTitle());
    }

    @Test
    @DisplayName("질문글 삭제")
    public void delete() {
        //given
        QuestionSaveDto save = QuestionSaveDto.builder()
                .text("text1")
                .title("title1")
                .build();

        //when
        questionService.save(save);
        Question question = questionRepository.findAll().get(0);
        questionService.deleteById(question.getId());

        //then
        Assertions.assertEquals(0L, questionRepository.count());
    }
}