package carrotMarcket.marcket.request;

import carrotMarcket.marcket.entity.QuestionStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionListDto {

    private String title;
    private QuestionStatus status;


}
