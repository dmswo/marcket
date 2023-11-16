package carrotMarcket.marcket.board.request;

import carrotMarcket.marcket.board.constant.BoardStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardListDto {
    private String title;
    private BoardStatus status;
}
