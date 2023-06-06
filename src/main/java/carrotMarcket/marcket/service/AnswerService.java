package carrotMarcket.marcket.service;

import carrotMarcket.marcket.entity.Answer;
import carrotMarcket.marcket.entity.Question;
import carrotMarcket.marcket.exeption.CustomException;
import carrotMarcket.marcket.exeption.ExceptionCode;
import carrotMarcket.marcket.repository.AnswerRepository;
import carrotMarcket.marcket.repository.QuestionRepository;
import carrotMarcket.marcket.request.AnswerSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public Long save(AnswerSaveDto answerSaveDto) {
        Question question = questionRepository.findById(answerSaveDto.getQuestionId())
                .orElseThrow(() -> new CustomException(ExceptionCode.EXCEPTION_CODE));

        Answer answer = Answer.builder()
                .text(answerSaveDto.getText())
                .question(question)
                .build();
        Answer save = answerRepository.save(answer);

        return save.getId();
    }
}
