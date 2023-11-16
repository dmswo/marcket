package carrotMarcket.marcket.board.repository;

import carrotMarcket.marcket.board.request.QuestionListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionCustomRepository {
    Page questionAllList(QuestionListDto questionListDto, Pageable pageable);
}
