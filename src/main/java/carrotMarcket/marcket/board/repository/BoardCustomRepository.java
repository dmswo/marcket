package carrotMarcket.marcket.board.repository;

import carrotMarcket.marcket.board.constant.BoardStatus;
import carrotMarcket.marcket.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardCustomRepository {
    Page<Board> boardList(BoardStatus status, String title, Pageable pageable);
}
