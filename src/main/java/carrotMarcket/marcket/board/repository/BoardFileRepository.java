package carrotMarcket.marcket.board.repository;

import carrotMarcket.marcket.board.entity.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {
}
