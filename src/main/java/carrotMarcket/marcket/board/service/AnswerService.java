package carrotMarcket.marcket.board.service;

import carrotMarcket.marcket.board.entity.Board;
import carrotMarcket.marcket.board.repository.AnswerRepository;
import carrotMarcket.marcket.board.repository.BoardRepository;
import carrotMarcket.marcket.board.entity.Answer;
import carrotMarcket.marcket.board.exception.BoardBusinessException;
import carrotMarcket.marcket.board.exception.BoardExceptionCode;
import carrotMarcket.marcket.board.request.AnswerSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final BoardRepository boardRepository;

    public Long save(AnswerSaveDto answerSaveDto) {
        Board board = boardRepository.findById(answerSaveDto.getQuestionId())
                .orElseThrow(() -> new BoardBusinessException(BoardExceptionCode.NOT_EXIST_BOARD));

        Answer answer = Answer.builder()
                .text(answerSaveDto.getText())
                .board(board)
                .build();
        Answer save = answerRepository.save(answer);

        return save.getId();
    }

    public void deleteById(Long id) {
        answerRepository.deleteById(id);
    }
}
