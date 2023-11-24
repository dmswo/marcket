package carrotMarcket.marcket.board.service;

import carrotMarcket.marcket.board.entity.Board;
import carrotMarcket.marcket.board.repository.BoardRepository;
import carrotMarcket.marcket.board.constant.BoardStatus;
import carrotMarcket.marcket.board.exception.BoardBusinessException;
import carrotMarcket.marcket.board.exception.BoardExceptionCode;
import carrotMarcket.marcket.board.request.BoardLikeDto;
import carrotMarcket.marcket.board.request.BoardUpdateDto;
import carrotMarcket.marcket.board.request.BoardListDto;
import carrotMarcket.marcket.board.request.BoardSaveDto;
import carrotMarcket.marcket.board.response.BoardFindByIdResponse;
import carrotMarcket.marcket.global.redis.RedisService;
import carrotMarcket.marcket.global.redis.RedisUtil;
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
    private final RedisService redisService;
    private final RedisUtil redisUtil;

    private static final String KEY_VIEW = "board:view:";
    private static final String KEY_LIKE = "board:like:";

    public Page boardList(BoardListDto boardListDto, Pageable pageable) {
        return boardRepository.boardList(boardListDto, pageable);
    }

    public BoardFindByIdResponse boardFindById(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardBusinessException(BoardExceptionCode.NOT_EXIST_BOARD));

        // redis 활용한 조회수
        String view = this.views(board);

        return new BoardFindByIdResponse(board, Long.parseLong(view));
    }

    public String views(Board board) {
        Long boardId = board.getId();
        String values = redisUtil.getValues(KEY_VIEW + boardId);
        if (values == null) {
            redisUtil.setValues(KEY_VIEW + boardId, String.valueOf(board.getViews() + 1));
        } else {
            int increment = Integer.parseInt(values) + 1;
            redisUtil.setValues(KEY_VIEW + boardId, String.valueOf(increment));
        }

        return redisUtil.getValues(KEY_VIEW + boardId);
    }

    public Long save(BoardSaveDto boardSaveDto) {
        Board board = Board.builder()
                .title(boardSaveDto.getTitle())
                .text(boardSaveDto.getText())
                .boardStatus(BoardStatus.WAIT)
                .views(0L)
                .build();

        Board save = boardRepository.save(board);
        return save.getId();
    }

    public void update(Long id, BoardUpdateDto boardUpdateDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new BoardBusinessException(BoardExceptionCode.NOT_EXIST_BOARD));
        board.update(boardUpdateDto.getTitle(), boardUpdateDto.getText());
    }

    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    public boolean like(BoardLikeDto boardLikeDto) {
        boardRepository.findById(boardLikeDto.getBoardId()).orElseThrow(() -> new BoardBusinessException(BoardExceptionCode.NOT_EXIST_BOARD));
        return redisService.like(boardLikeDto);
    }

}
