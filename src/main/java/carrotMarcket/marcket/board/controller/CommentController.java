package carrotMarcket.marcket.board.controller;

import carrotMarcket.marcket.board.request.CommentSaveDto;
import carrotMarcket.marcket.board.service.CommentService;
import carrotMarcket.marcket.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/save")
    public ResponseEntity<?> CommentSave(@RequestBody @Valid CommentSaveDto commentSaveDto) {
        Long comment = commentService.save(commentSaveDto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successResponse(comment));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> CommentDelete(@RequestParam Long commentId) {
        commentService.deleteById(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successWithNoContent());
    }

}
