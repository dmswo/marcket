package carrotMarcket.marcket.board.controller;

import carrotMarcket.marcket.board.request.QuestionEdit;
import carrotMarcket.marcket.board.request.QuestionListDto;
import carrotMarcket.marcket.board.request.QuestionSaveDto;
import carrotMarcket.marcket.board.service.QuestionService;
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
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/questionList")
    public ResponseEntity<?> QuestionList(@RequestBody QuestionListDto questionListDto, Pageable pageable) {
        Page list = questionService.questionList(questionListDto, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successResponse(list));
    }

    @PostMapping("/save")
    public ResponseEntity<?> QuestionSave(@RequestBody @Valid QuestionSaveDto questionSaveDto) {
        Long question = questionService.save(questionSaveDto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successResponse(question));
    }

    @PatchMapping("/update/{questionId}")
    public ResponseEntity<?> QuestionEdit(@PathVariable Long questionId, @RequestBody @Valid QuestionEdit request) {
        questionService.edit(questionId, request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successWithNoContent());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> QuestionDelete(@RequestParam Long id) {
        questionService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successWithNoContent());
    }
}
