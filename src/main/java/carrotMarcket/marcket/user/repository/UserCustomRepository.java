package carrotMarcket.marcket.user.repository;

import carrotMarcket.marcket.board.entity.Board;

import java.util.List;

public interface UserCustomRepository {
    List<Board> myBoardList(Long userId);
}
