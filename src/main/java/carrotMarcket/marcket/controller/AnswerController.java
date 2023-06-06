package carrotMarcket.marcket.controller;

import carrotMarcket.marcket.request.AnswerSaveDto;
import carrotMarcket.marcket.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/save")
    public ResponseEntity<?> QuestionSave(@RequestBody @Valid AnswerSaveDto answerSaveDto) {
        System.out.println("start---");
        Long answer = answerService.save(answerSaveDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(answer);
    }
}
