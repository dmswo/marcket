package carrotMarcket.marcket.global.redis;

import carrotMarcket.marcket.board.entity.Board;
import carrotMarcket.marcket.board.request.BoardLikeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisService {

    private final RedisUtil redisUtil;
    private static final String KEY_LIKE = "board:like:";


    // 좋아요 로직
    public Boolean like(BoardLikeDto boardLikeDto) {
        Long boardId = boardLikeDto.getBoardId();
        String userId = boardLikeDto.getUserId();
        Boolean like = boardLikeDto.getLike();
        if (like == false) {
            redisUtil.like(KEY_LIKE + boardId, userId);
        } else {
            redisUtil.unLike(KEY_LIKE + boardId, userId);
        }

        return true;
    }
}
