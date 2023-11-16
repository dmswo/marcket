package carrotMarcket.marcket.board.request;

import carrotMarcket.marcket.board.entity.QuestionStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionListDto {
    private String title;
    private QuestionStatus status;
}
