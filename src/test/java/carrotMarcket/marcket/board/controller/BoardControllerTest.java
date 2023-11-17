package carrotMarcket.marcket.board.controller;

import carrotMarcket.marcket.board.entity.Board;
import carrotMarcket.marcket.board.constant.BoardStatus;
import carrotMarcket.marcket.board.repository.BoardRepository;
import carrotMarcket.marcket.board.request.BoardEdit;
import carrotMarcket.marcket.board.request.BoardListDto;
import carrotMarcket.marcket.board.request.BoardSaveDto;
import carrotMarcket.marcket.mock.WithCustomMockUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    void clean() {
        boardRepository.deleteAll();
    }

    @Test
    @DisplayName("질문글 여러개 조회")
    void list() throws Exception {
        // given
        List<Board> list = IntStream.range(0, 25)
                .mapToObj(i -> Board.builder()
                        .title("title" + i)
                        .text("text" + i)
                        .build()
                ).collect(Collectors.toList());
        boardRepository.saveAll(list);

        BoardListDto search = BoardListDto.builder()
                .build();

        String json = objectMapper.writeValueAsString(search);

        // expected
        mockMvc.perform(post("/board/list")
                .contentType(APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content.length()", is(10)))
                .andExpect(jsonPath("$.data.content[9].title").value("title9"))
                .andDo(print());
    }

    @Test
    @DisplayName("질문글 작성 성공")
    @WithCustomMockUser
    public void saveSuccess() throws Exception {
        // given
        BoardSaveDto save = BoardSaveDto.builder()
                .text("text1")
                .title("title1")
                .build();

        String json = objectMapper.writeValueAsString(save);

        // when
        mockMvc.perform(post("/board/save").header("Bearer", "ABCDE")
                .contentType(APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk())
                .andDo(print());

        // then
        Assertions.assertEquals(1L, boardRepository.count());
        Board board = boardRepository.findAll().get(0);
        Assertions.assertEquals("text1", board.getText());
        Assertions.assertEquals("title1", board.getTitle());
        Assertions.assertEquals(BoardStatus.WAIT, board.getBoardStatus());
    }

    @Test
    @DisplayName("질문글 작성 실패(text or title 비어있을경우)")
    @WithCustomMockUser
    public void saveFail() throws Exception {
        // given
        BoardSaveDto save = BoardSaveDto.builder()
                .text("text1")
                .build();

        String json = objectMapper.writeValueAsString(save);

        // expect
        mockMvc.perform(post("/board/save").header("Bearer", "ABCDE")
                .contentType(APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("FAIL"))
                .andExpect(jsonPath("$.data.title").value("타이틀을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("질문글 수정 성공")
    @WithCustomMockUser
    public void updateSuccess() throws Exception {
        // given
        Board save = Board.builder()
                .text("text1")
                .title("title1")
                .build();
        boardRepository.save(save);
        Board result = boardRepository.findAll().get(0);

        BoardEdit boardEdit = BoardEdit.builder()
                .text("updateText1")
                .title("updateTitle1")
                .build();

        String json = objectMapper.writeValueAsString(boardEdit);

        // when
        mockMvc.perform(patch("/board/update?boardId="+result.getId()).header("Bearer", "ABCDE")
                .contentType(APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk())
                .andDo(print());

        // then
        Assertions.assertEquals(1L, boardRepository.count());
        Board board = boardRepository.findAll().get(0);
        Assertions.assertEquals("updateText1", board.getText());
        Assertions.assertEquals("updateTitle1", board.getTitle());
    }

    @Test
    @DisplayName("질문글 수정 실패")
    @WithCustomMockUser
    public void updateFail() throws Exception {
        // given
        Board save = Board.builder()
                .text("text1")
                .title("title1")
                .build();
        boardRepository.save(save);
        Board result = boardRepository.findAll().get(0);

        BoardEdit boardEdit = BoardEdit.builder()
                .text("updateText1")
                .title("")
                .build();

        String json = objectMapper.writeValueAsString(boardEdit);

        // expect
        mockMvc.perform(patch("/board/update?boardId="+result.getId()).header("Bearer", "ABCDE")
                .contentType(APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("FAIL"))
                .andExpect(jsonPath("$.data.title").value("타이틀을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("질문글 삭제 성공")
    @WithCustomMockUser
    public void deleteSuccess() throws Exception {
        // given
         Board board = Board.builder()
                .text("text1")
                .title("title1")
                .build();

        boardRepository.save(board);
        Board result = boardRepository.findAll().get(0);

        // when
        mockMvc.perform(delete("/board/delete?boardId="+result.getId()).header("Bearer", "ABCDE")
                .contentType(APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print());

        // then
        Assertions.assertEquals(0L, boardRepository.count());
    }
}