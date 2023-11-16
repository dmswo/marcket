package carrotMarcket.marcket.board.repository;

import carrotMarcket.marcket.board.request.BoardListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardCustomRepository {
    Page boardList(BoardListDto boardListDto, Pageable pageable);
}
