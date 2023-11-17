package carrotMarcket.marcket.board.controller;

import carrotMarcket.marcket.board.request.CommentSaveDto;
import carrotMarcket.marcket.board.service.CommentService;
import carrotMarcket.marcket.global.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@Api(value = "/comment", description = "댓글 관련 API")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/save")
    @ApiOperation(value="댓글 저장 API", notes="댓글을 저장합니다.")
    public ResponseEntity<?> CommentSave(@RequestBody @Valid CommentSaveDto commentSaveDto) {
        Long comment = commentService.save(commentSaveDto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successResponse(comment));
    }

    @DeleteMapping("/delete")
    @ApiOperation(value="댓글 삭제 API", notes="댓글을 삭제합니다.")
    public ResponseEntity<?> CommentDelete(@RequestParam Long commentId) {
        commentService.deleteById(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successWithNoContent());
    }

}
