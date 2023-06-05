package carrotMarcket.marcket.controller;

import carrotMarcket.marcket.entity.Question;
import carrotMarcket.marcket.request.QuestionEdit;
import carrotMarcket.marcket.request.QuestionListDto;
import carrotMarcket.marcket.request.QuestionSaveDto;
import carrotMarcket.marcket.service.QuestionService;
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
        return ResponseEntity.status(HttpStatus.OK)
                .body(list);
    }

    @PostMapping("/save")
    public ResponseEntity<?> QuestionSave(@RequestBody @Valid QuestionSaveDto questionSaveDto) {
        Long question = questionService.save(questionSaveDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(question);
    }

    @PatchMapping("/update/{questionId}")
    public void QuestionEdit(@PathVariable Long questionId, @RequestBody @Valid QuestionEdit request) {
        questionService.edit(questionId, request);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> QuestionDelete(@RequestParam Long id) {
        questionService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(id);
    }
}
