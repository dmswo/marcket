package carrotMarcket.marcket.controller;

import carrotMarcket.marcket.request.AnswerSaveDto;
import carrotMarcket.marcket.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

    private final AnswerService answerService;

    @GetMapping("/hello")
    public String hello(){
        return "Hello1111";
    }

    @PostMapping("/save")
    public ResponseEntity<?> AnswerSave(@RequestBody @Valid AnswerSaveDto answerSaveDto) {
        Long answer = answerService.save(answerSaveDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(answer);
    }

    @DeleteMapping("/delete/{answerId}")
    public ResponseEntity<?> AnswerDelete(@PathVariable Long answerId) {
        answerService.deleteById(answerId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(answerId);
    }

}
