package carrotMarcket.marcket.board.response;

import carrotMarcket.marcket.board.constant.BoardStatus;
import carrotMarcket.marcket.board.entity.Board;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
public class BoardListResponse {

    private Long id;
    private String title;
    private BoardStatus status;
    private LocalDateTime regDate;
    private Long views;
    private List<BoardFileResponse> boardFileResponseList;


    public BoardListResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.status = board.getBoardStatus();
        this.regDate = board.getRegDate();
        this.views = board.getViews();
        this.boardFileResponseList = board.getBoardFile().stream()
                .map(o -> new BoardFileResponse(o)).collect(Collectors.toList());
    }

    public void addViews(Long views) {
        this.views = views;
    }
}
