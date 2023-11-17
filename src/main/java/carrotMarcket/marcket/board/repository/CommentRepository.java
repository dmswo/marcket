package carrotMarcket.marcket.board.repository;

import carrotMarcket.marcket.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
