package carrotMarcket.marcket.board.repository;

import carrotMarcket.marcket.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository {
}
