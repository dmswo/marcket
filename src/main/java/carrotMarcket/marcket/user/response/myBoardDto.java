package carrotMarcket.marcket.user.response;

import carrotMarcket.marcket.board.constant.BoardStatus;
import carrotMarcket.marcket.board.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class myBoardDto {

    private Long id;
    private String title;
    private BoardStatus status;
    private LocalDateTime regDate;
    private List<carrotMarcket.marcket.user.response.myBoardCommentDto> myBoardCommentDto;

    public myBoardDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.status = board.getBoardStatus();
        this.regDate = board.getRegDate();
        this.myBoardCommentDto = board.getComment().stream()
                .map(o -> new myBoardCommentDto(o))
                .collect(Collectors.toList());
    }
}
