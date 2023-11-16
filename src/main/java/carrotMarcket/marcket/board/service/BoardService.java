package carrotMarcket.marcket.board.service;

import carrotMarcket.marcket.board.entity.Board;
import carrotMarcket.marcket.board.repository.BoardRepository;
import carrotMarcket.marcket.board.constant.BoardStatus;
import carrotMarcket.marcket.board.exception.BoardBusinessException;
import carrotMarcket.marcket.board.exception.BoardExceptionCode;
import carrotMarcket.marcket.board.request.BoardEdit;
import carrotMarcket.marcket.board.request.BoardListDto;
import carrotMarcket.marcket.board.request.BoardSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    public Page boardList(BoardListDto boardListDto, Pageable pageable) {
        return boardRepository.boardList(boardListDto, pageable);
    }

    public Long save(BoardSaveDto boardSaveDto) {
        Board board = Board.builder()
                .title(boardSaveDto.getTitle())
                .text(boardSaveDto.getText())
                .boardStatus(BoardStatus.WAIT)
                .build();

        Board save = boardRepository.save(board);
        return save.getId();
    }

    public void edit(Long id, BoardEdit boardEdit) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new BoardBusinessException(BoardExceptionCode.NOT_EXIST_BOARD));
        board.edit(boardEdit.getTitle(), boardEdit.getText());
    }

    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

}
