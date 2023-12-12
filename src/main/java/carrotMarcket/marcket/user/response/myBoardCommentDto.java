package carrotMarcket.marcket.user.response;


import carrotMarcket.marcket.board.entity.Comment;
import lombok.Getter;

@Getter
public class myBoardCommentDto {

    private Long id;
    private String text;

    public myBoardCommentDto(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
    }
}
