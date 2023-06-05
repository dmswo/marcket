package carrotMarcket.marcket.response;

import carrotMarcket.marcket.entity.QuestionStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuestionListResponse {

    private final Long id;
    private final String title;
    private final QuestionStatus status;
    private final LocalDateTime regDate;

    @QueryProjection
    public QuestionListResponse(Long id, String title, QuestionStatus status, LocalDateTime regDate) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.regDate = regDate;
    }
}
