package carrotMarcket.marcket.board.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentSaveDto {

    private Long boardId;

    @NotBlank(message = "텍스트를 입력해주세요.")
    private String text;
}
