package carrotMarcket.marcket.board.service;

import carrotMarcket.marcket.board.entity.Board;
import carrotMarcket.marcket.board.constant.BoardStatus;
import carrotMarcket.marcket.board.repository.BoardRepository;
import carrotMarcket.marcket.board.request.BoardUpdateDto;
import carrotMarcket.marcket.board.request.BoardSaveDto;
import carrotMarcket.marcket.board.response.BoardFindByIdResponse;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @DisplayName("게시글 여러개 조회")
    public void list() {
        //given
        List<Board> list = IntStream.range(0, 25)
                .mapToObj(i -> Board.builder()
                        .text("text" + i)
                        .title("title" + i)
                        .boardStatus(BoardStatus.WAIT)
                        .views(0L)
                        .build()
                ).collect(Collectors.toList());

        boardRepository.saveAll(list);

        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page page = boardService.boardList(BoardStatus.WAIT, null, pageable);

        //then
        List<BoardListResponse> content = page.getContent();
        System.out.println(content);
        assertEquals(10L, page.getSize());
        assertEquals(3L, page.getTotalPages());
        assertEquals(10L, content.size());
        assertEquals("title9", content.get(9).getTitle());
    }

//    @Test
//    @DisplayName("게시글 단건 조회")
//    public void boardFindById() throws IOException {
//        //given
//        BoardSaveDto save = BoardSaveDto.builder()
//                .text("text1")
//                .title("title1")
//                .build();
//
//        List<MultipartFile> multipartFileList = List.of();
//
//        Long boardId = boardService.save(save, multipartFileList);
//
//        //when
//        BoardFindByIdResponse response = boardService.boardFindById(boardId);
//
//        //then
//        assertEquals("title1", response.getTitle());
//        assertEquals("text1", response.getText());
//    }

    @Test
    @DisplayName("게시글 작성")
    public void save() throws IOException {
        //given
        BoardSaveDto save = BoardSaveDto.builder()
                .text("text1")
                .title("title1")
                .build();

        List<MultipartFile> multipartFileList = List.of();

        //when
        boardService.save(save, multipartFileList);

        //then
        Assertions.assertEquals(1L, boardRepository.count());
        Board board = boardRepository.findAll().get(0);
        Assertions.assertEquals("text1", board.getText());
        Assertions.assertEquals("title1", board.getTitle());
        Assertions.assertEquals(BoardStatus.WAIT, board.getBoardStatus());
    }

    @Test
    @DisplayName("게시글 수정")
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
    @DisplayName("게시글 삭제")
    public void delete() throws IOException {
        //given
        BoardSaveDto save = BoardSaveDto.builder()
                .text("text1")
                .title("title1")
                .build();

        List<MultipartFile> multipartFileList = List.of();

        //when
        boardService.save(save, multipartFileList);
        Board board = boardRepository.findAll().get(0);
        boardService.deleteById(board.getId());

        //then
        Assertions.assertEquals(0L, boardRepository.count());
    }
}