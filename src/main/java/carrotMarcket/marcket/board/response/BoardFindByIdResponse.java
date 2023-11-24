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
}
