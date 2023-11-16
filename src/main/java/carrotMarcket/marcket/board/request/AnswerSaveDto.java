package carrotMarcket.marcket.board.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class AnswerSaveDto {

    private Long questionId;

    @NotBlank(message = "텍스트를 입력해주세요.")
    private String text;
}
