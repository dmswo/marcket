package carrotMarcket.marcket.repository;

import carrotMarcket.marcket.request.QuestionListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionCustomRepository {
    Page questionAllList(QuestionListDto questionListDto, Pageable pageable);
}
