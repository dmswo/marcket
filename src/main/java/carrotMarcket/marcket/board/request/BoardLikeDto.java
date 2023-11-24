package carrotMarcket.marcket.board.request;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class BoardLikeDto {

    private Long boardId;
    private String userId;
    private Boolean like;
}
