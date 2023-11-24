package carrotMarcket.marcket.board.batch;

import carrotMarcket.marcket.board.entity.Board;
import carrotMarcket.marcket.board.exception.BoardBusinessException;
import carrotMarcket.marcket.board.exception.BoardExceptionCode;
import carrotMarcket.marcket.board.repository.BoardRepository;
import carrotMarcket.marcket.global.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Component
@RequiredArgsConstructor
public class BoardBatch {

    private final RedisUtil redisUtil;
    private final BoardRepository boardRepository;

    @Scheduled(cron = "0 0/10 * * * ?")
    @Transactional
    public void BoardViewsRedisToDB () {
        Set<String> keys = redisUtil.getKeys("board:view:*");

        if (keys.isEmpty()) {
            return;
        }

        for (String key : keys) {
            Long boardId = redisUtil.extractBoardIdToKey(key);
            Long views = Long.parseLong(redisUtil.getValues(key));
            Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardBusinessException(BoardExceptionCode.NOT_EXIST_BOARD));
            board.updateViews(views);
            redisUtil.deleteDate(key);
        }
    }
}
