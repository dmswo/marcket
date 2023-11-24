package carrotMarcket.marcket.board.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardLikeResponse {

    private Boolean like;
    private Long likeCnt;
}
