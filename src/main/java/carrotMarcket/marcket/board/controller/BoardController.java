package carrotMarcket.marcket.board.controller;

import carrotMarcket.marcket.board.entity.Board;
import carrotMarcket.marcket.board.request.BoardUpdateDto;
import carrotMarcket.marcket.board.request.BoardListDto;
import carrotMarcket.marcket.board.request.BoardSaveDto;
import carrotMarcket.marcket.board.response.BoardFindByIdResponse;
import carrotMarcket.marcket.board.service.BoardService;
import carrotMarcket.marcket.global.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Api(value = "/board", description = "게시글 관련 API")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/list")
    @ApiOperation(value="게시글 조회 API", notes="등록된 게시글 전체를 조회합니다.")
    public ResponseEntity<?> BoardList(@RequestBody BoardListDto boardListDto, Pageable pageable) {
        Page list = boardService.boardList(boardListDto, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successResponse(list));
    }

    @GetMapping("/boardFindById")
    @ApiOperation(value="게시글 단건 조회 API", notes="등록된 게시글을 조회합니다.")
    public ResponseEntity<?> BoardFindById(@RequestParam Long boardId) {
        BoardFindByIdResponse board = boardService.boardFindById(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successResponse(board));
    }

    @PostMapping("/save")
    @ApiOperation(value="게시글 등록 API", notes="게시글을 등록합니다.")
    public ResponseEntity<?> BoardSave(@RequestBody @Valid BoardSaveDto boardSaveDto) {
        Long board = boardService.save(boardSaveDto);
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
}
