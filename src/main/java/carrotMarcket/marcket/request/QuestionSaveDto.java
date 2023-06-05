package carrotMarcket.marcket.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class QuestionSaveDto {

    @NotBlank(message = "타이틀을 입력해주세요.")
    private String title;

    @NotBlank(message = "텍스트를 입력해주세요.")
    private String text;


}
