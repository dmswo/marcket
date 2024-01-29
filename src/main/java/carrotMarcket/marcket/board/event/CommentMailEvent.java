package carrotMarcket.marcket.board.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentMailEvent {
    private String title;
    private String email;
}
