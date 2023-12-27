package carrotMarcket.marcket.board.response;

import carrotMarcket.marcket.board.entity.BoardFile;
import lombok.Getter;

@Getter
public class BoardFileResponse {

    private Long id;
    private String fileUrl;

    public BoardFileResponse(BoardFile boardFile) {
        this.id = boardFile.getId();
        this.fileUrl = boardFile.getFileUrl();
    }
}
