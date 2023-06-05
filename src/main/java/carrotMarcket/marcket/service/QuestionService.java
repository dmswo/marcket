package carrotMarcket.marcket.service;

import carrotMarcket.marcket.entity.Question;
import carrotMarcket.marcket.entity.QuestionStatus;
import carrotMarcket.marcket.exeption.CustomException;
import carrotMarcket.marcket.exeption.ExceptionCode;
import carrotMarcket.marcket.repository.QuestionRepository;
import carrotMarcket.marcket.request.QuestionEdit;
import carrotMarcket.marcket.request.QuestionListDto;
import carrotMarcket.marcket.request.QuestionSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Page questionList(QuestionListDto questionListDto, Pageable pageable) {
        return questionRepository.questionAllList(questionListDto, pageable);
    }

    public Long save(QuestionSaveDto questionSaveDto) {
        Question question = Question.builder()
                .title(questionSaveDto.getTitle())
                .text(questionSaveDto.getText())
                .questionStatus(QuestionStatus.WAIT)
                .build();

        Question save = questionRepository.save(question);
        return save.getId();
    }

    public void edit(Long id, QuestionEdit questionEdit) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionCode.EXCEPTION_CODE));
        question.edit(questionEdit.getTitle(), questionEdit.getText());
    }

    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }

}
