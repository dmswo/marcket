package carrotMarcket.marcket.repository;

import carrotMarcket.marcket.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionCustomRepository {
}
