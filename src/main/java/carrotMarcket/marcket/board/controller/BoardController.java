package carrotMarcket.marcket.board.controller;

import carrotMarcket.marcket.board.constant.BoardStatus;
import carrotMarcket.marcket.board.request.BoardLikeDto;
import carrotMarcket.marcket.board.request.BoardUpdateDto;
import carrotMarcket.marcket.board.request.BoardSaveDto;
import carrotMarcket.marcket.board.response.BoardFindByIdResponse;
import carrotMarcket.marcket.board.response.BoardLikeResponse;
import carrotMarcket.marcket.board.response.BoardListResponse;
import carrotMarcket.marcket.board.service.BoardService;
import carrotMarcket.marcket.global.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Api(value = "/board", description = "게시글 관련 API")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    @ApiOperation(value="게시글 조회 API", notes="등록된 게시글 전체를 조회합니다.")
    public ResponseEntity<?> BoardList(@RequestParam(required = false) BoardStatus status
            , @RequestParam(required = false) String title
            , Pageable pageable) {
        Page<BoardListResponse> list = boardService.boardList(status, title, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successResponse(list));
    }

    @GetMapping("/boardFindById")
    @ApiOperation(value="게시글 단건 조회 API", notes="등록된 게시글을 조회합니다.")
    public ResponseEntity<?> BoardFindById(@RequestParam Long boardId) {
        BoardFindByIdResponse board = boardService.boardFindById(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successResponse(board));
    }

    @PostMapping(value = "/save", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value="게시글 등록 API", notes="게시글을 등록합니다.")
    public ResponseEntity<?> BoardSave(@RequestPart @Valid BoardSaveDto boardSaveDto
            , @RequestPart(required = false) List<MultipartFile> files) throws IOException {
        Long board = boardService.save(boardSaveDto, files);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successResponse(board));
    }

    @PatchMapping("/update")
    @ApiOperation(value="게시글 수정 API", notes="게시글을 수정합니다.")
    public ResponseEntity<?> BoardUpdate(@RequestParam Long boardId, @RequestBody @Valid BoardUpdateDto boardUpdateDto) {
        boardService.update(boardId, boardUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successWithNoContent());
    }

    @DeleteMapping("/delete")
    @ApiOperation(value="게시글 삭제 API", notes="게시글을 삭제합니다.")
    public ResponseEntity<?> BoardDelete(@RequestParam Long boardId) {
        boardService.deleteById(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successWithNoContent());
    }

    @PostMapping("/like")
    @ApiOperation(value="좋아요 API", notes="게시글에대한 좋아요 정보 수정")
    public ResponseEntity<?> BoardLike(@RequestBody @Valid BoardLikeDto boardLikeDto) {
        BoardLikeResponse like = boardService.like(boardLikeDto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successResponse(like));
    }
}
