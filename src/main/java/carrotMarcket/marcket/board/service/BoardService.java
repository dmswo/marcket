package carrotMarcket.marcket.board.service;

import carrotMarcket.marcket.board.entity.Board;
import carrotMarcket.marcket.board.entity.BoardFile;
import carrotMarcket.marcket.board.repository.BoardRepository;
import carrotMarcket.marcket.board.constant.BoardStatus;
import carrotMarcket.marcket.board.exception.BoardBusinessException;
import carrotMarcket.marcket.board.exception.BoardExceptionCode;
import carrotMarcket.marcket.board.request.BoardLikeDto;
import carrotMarcket.marcket.board.request.BoardUpdateDto;
import carrotMarcket.marcket.board.request.BoardSaveDto;
import carrotMarcket.marcket.board.response.BoardFindByIdResponse;
import carrotMarcket.marcket.board.response.BoardLikeResponse;
import carrotMarcket.marcket.board.response.BoardListResponse;
import carrotMarcket.marcket.global.redis.RedisUtil;
import carrotMarcket.marcket.global.util.FileUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final RedisUtil redisUtil;
    private final FileUpload fileUpload;

    private static final String KEY_VIEW = "board:view:";
    private static final String KEY_LIKE = "board:like:";

    public Page<BoardListResponse> boardList(BoardStatus status, String title, Pageable pageable) {
        Page<Board> page = boardRepository.boardList(status, title, pageable);
        Page<BoardListResponse> result = page.map(o -> new BoardListResponse(o));

        for (BoardListResponse response : result) {
            Long id = response.getId();
            Long views = response.getViews();
            String values = redisUtil.getValues(KEY_VIEW + id) == null ? String.valueOf(views) : redisUtil.getValues(KEY_VIEW + id);
            response.addViews(Long.parseLong(values));
        }

        return result;
    }

    public BoardFindByIdResponse boardFindById(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardBusinessException(BoardExceptionCode.NOT_EXIST_BOARD));

        // redis 활용한 조회수
//        String view = this.views(board);
        BoardFindByIdResponse response = BoardFindByIdResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .text(board.getText())
                .boardStatus(board.getBoardStatus())
                .regID(board.getRegID())
                .regDate(board.getRegDate())
//                .views(Long.parseLong(view))
                .build();

        return response;
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

    @Transactional
    public Long save(BoardSaveDto boardSaveDto, List<MultipartFile> multipartFile) throws IOException {
        // Board 저장
        Board board = Board.builder()
                .title(boardSaveDto.getTitle())
                .text(boardSaveDto.getText())
                .boardStatus(BoardStatus.WAIT)
                .views(0L)
                .build();

        // BoardFile 저장
        List<BoardFile> fileList = new ArrayList<>();
        for (MultipartFile file : multipartFile) {
            if (!file.isEmpty()) {
                String fileUrl = fileUpload.uploadFile(file, "board");
                BoardFile boardFile = BoardFile.builder().fileUrl(fileUrl).board(board).build();
                fileList.add(boardFile);
            }
        }

        board.addBoardFile(fileList);
        Board save = boardRepository.save(board);

        return save.getId();
    }

    @Transactional
    public void update(Long id, BoardUpdateDto boardUpdateDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new BoardBusinessException(BoardExceptionCode.NOT_EXIST_BOARD));
        board.update(boardUpdateDto.getTitle(), boardUpdateDto.getText());
    }

    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    public BoardLikeResponse like(BoardLikeDto boardLikeDto) {
        boardRepository.findById(boardLikeDto.getBoardId()).orElseThrow(() -> new BoardBusinessException(BoardExceptionCode.NOT_EXIST_BOARD));

        Long boardId = boardLikeDto.getBoardId();
        String userId = boardLikeDto.getUserId();
        Boolean like = boardLikeDto.getLike();
        if (like == false) {
            redisUtil.like(KEY_LIKE + boardId, userId);
        } else {
            redisUtil.unLike(KEY_LIKE + boardId, userId);
        }

        BoardLikeResponse response = BoardLikeResponse.builder()
                .likeCnt(redisUtil.getLikeCount(KEY_LIKE + boardId))
                .like(redisUtil.isLike(KEY_LIKE + boardId, userId))
                .build();

        return response;
    }

}
