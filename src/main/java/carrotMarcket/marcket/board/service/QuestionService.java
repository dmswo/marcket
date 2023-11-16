package carrotMarcket.marcket.board.service;

import carrotMarcket.marcket.board.repository.QuestionRepository;
import carrotMarcket.marcket.board.entity.Question;
import carrotMarcket.marcket.board.entity.QuestionStatus;
import carrotMarcket.marcket.board.exception.BoardBusinessException;
import carrotMarcket.marcket.board.exception.BoardExceptionCode;
import carrotMarcket.marcket.board.request.QuestionEdit;
import carrotMarcket.marcket.board.request.QuestionListDto;
import carrotMarcket.marcket.board.request.QuestionSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Question question = questionRepository.findById(id).orElseThrow(() -> new BoardBusinessException(BoardExceptionCode.NOT_EXIST_BOARD));
        question.edit(questionEdit.getTitle(), questionEdit.getText());
    }

    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }

}
