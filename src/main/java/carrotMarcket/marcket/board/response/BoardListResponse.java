package carrotMarcket.marcket.board.response;

import carrotMarcket.marcket.board.constant.BoardStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListResponse {

    private final Long id;
    private final String title;
    private final BoardStatus status;
    private final LocalDateTime regDate;

    @QueryProjection
    public BoardListResponse(Long id, String title, BoardStatus status, LocalDateTime regDate) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.regDate = regDate;
    }
}
