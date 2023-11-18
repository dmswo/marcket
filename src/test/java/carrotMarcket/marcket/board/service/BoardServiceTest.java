package carrotMarcket.marcket.board.service;

import carrotMarcket.marcket.board.entity.Board;
import carrotMarcket.marcket.board.constant.BoardStatus;
import carrotMarcket.marcket.board.repository.BoardRepository;
import carrotMarcket.marcket.board.request.BoardUpdateDto;
import carrotMarcket.marcket.board.request.BoardListDto;
import carrotMarcket.marcket.board.request.BoardSaveDto;
import carrotMarcket.marcket.board.response.BoardListResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    void clean() {
        boardRepository.deleteAll();
    }

    @Test
    @DisplayName("질문글 여러개 조회")
    public void list() {
        //given
        List<Board> list = IntStream.range(0, 25)
                .mapToObj(i -> Board.builder()
                        .text("text" + i)
                        .title("title" + i)
                        .build()
                ).collect(Collectors.toList());

        boardRepository.saveAll(list);

        Pageable pageable = PageRequest.of(0, 10);

        //when
        BoardListDto search = BoardListDto.builder().build();
        Page page = boardService.boardList(search, pageable);

        //then
        List<BoardListResponse> content = page.getContent();
        assertEquals(10L, page.getSize());
        assertEquals(3L, page.getTotalPages());
        assertEquals(10L, content.size());
        assertEquals("title9", content.get(9).getTitle());
    }

    @Test
    @DisplayName("질문글 작성")
    public void save() {
        //given
        BoardSaveDto save = BoardSaveDto.builder()
                .text("text1")
                .title("title1")
                .build();

        //when
        boardService.save(save);

        //then
        Assertions.assertEquals(1L, boardRepository.count());
        Board board = boardRepository.findAll().get(0);
        Assertions.assertEquals("text1", board.getText());
        Assertions.assertEquals("title1", board.getTitle());
        Assertions.assertEquals(BoardStatus.WAIT, board.getBoardStatus());
    }

    @Test
    @DisplayName("질문글 수정")
    public void update() {
        //given
        Board save = Board.builder()
                .text("saveText1")
                .title("saveTitle1")
                .build();

        boardRepository.save(save);

        BoardUpdateDto update = BoardUpdateDto.builder()
                .text("updateText1")
                .title("updateTitle1")
                .build();

        //when
        boardService.update(save.getId(), update);

        //then
        Board board1 = boardRepository.findById(save.getId()).orElseThrow(() -> new RuntimeException("질문글이 존재하지 않습니다."));
        Assertions.assertEquals("updateText1", board1.getText());
        Assertions.assertEquals("updateTitle1", board1.getTitle());
    }

    @Test
    @DisplayName("질문글 삭제")
    public void delete() {
        //given
        BoardSaveDto save = BoardSaveDto.builder()
                .text("text1")
                .title("title1")
                .build();

        //when
        boardService.save(save);
        Board board = boardRepository.findAll().get(0);
        boardService.deleteById(board.getId());

        //then
        Assertions.assertEquals(0L, boardRepository.count());
    }
}