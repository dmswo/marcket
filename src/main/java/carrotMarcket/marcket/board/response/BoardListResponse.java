package carrotMarcket.marcket.board.response;

import carrotMarcket.marcket.board.constant.BoardStatus;
import carrotMarcket.marcket.board.entity.Comment;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
public class BoardListResponse {

    private Long id;
    private String title;
    private BoardStatus status;
    private LocalDateTime regDate;
    private Long views;

    @QueryProjection
    public BoardListResponse(Long id, String title, BoardStatus status, LocalDateTime regDate, Long views) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.regDate = regDate;
        this.views = views;
    }

    public void addViews(Long views) {
        this.views = views;
    }
}
