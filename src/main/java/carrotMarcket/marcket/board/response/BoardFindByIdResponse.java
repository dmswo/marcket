package carrotMarcket.marcket.board.response;

import carrotMarcket.marcket.board.constant.BoardStatus;
import carrotMarcket.marcket.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardFindByIdResponse {

    private Long id;
    private String title;
    private String text;
    private BoardStatus boardStatus;
    private String regID;
    private LocalDateTime regDate;
    private Long views;

    public BoardFindByIdResponse(Board board, Long views) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.text = board.getText();
        this.boardStatus = board.getBoardStatus();
        this.regID = board.getRegID();
        this.regDate = board.getRegDate();
        this.views = views;
    }
}
