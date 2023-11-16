package carrotMarcket.marcket.board.controller;

import carrotMarcket.marcket.board.request.BoardEdit;
import carrotMarcket.marcket.board.request.BoardListDto;
import carrotMarcket.marcket.board.request.BoardSaveDto;
import carrotMarcket.marcket.board.service.BoardService;
import carrotMarcket.marcket.global.response.ApiResponse;
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
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public ResponseEntity<?> BoardList(@RequestBody BoardListDto boardListDto, Pageable pageable) {
        Page list = boardService.boardList(boardListDto, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successResponse(list));
    }

    @PostMapping("/save")
    public ResponseEntity<?> BoardSave(@RequestBody @Valid BoardSaveDto boardSaveDto) {
        Long question = boardService.save(boardSaveDto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successResponse(question));
    }

    @PatchMapping("/update")
    public ResponseEntity<?> BoardEdit(@RequestParam Long boardId, @RequestBody @Valid BoardEdit request) {
        boardService.edit(boardId, request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successWithNoContent());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> BoardDelete(@RequestParam Long boardId) {
        boardService.deleteById(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successWithNoContent());
    }
}
