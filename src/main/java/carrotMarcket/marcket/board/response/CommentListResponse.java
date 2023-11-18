package carrotMarcket.marcket.board.response;

import carrotMarcket.marcket.board.entity.Comment;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.stream.Stream;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentListResponse {

    private Long id;
    private String text;
    private String regId;

    public Page<CommentListResponse> toDto(Page<Comment> list) {
        Page<CommentListResponse> commentList = list.map(m -> CommentListResponse.builder()
                .id(m.getId())
                .text(m.getText())
                .regId(m.getRegID())
                .build());
        return commentList;
    }
}
