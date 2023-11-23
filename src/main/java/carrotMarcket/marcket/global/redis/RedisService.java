package carrotMarcket.marcket.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisService {

    private final RedisUtil redisUtil;

    private static final String KEY_LIKE = "board:view:";

    public String view(Long boardId) {
        String values = redisUtil.getValues(KEY_LIKE+boardId);
        if (values == null) {
            redisUtil.setValues(KEY_LIKE + boardId, "1");
        } else {
            int increment = Integer.parseInt(values) + 1;
            redisUtil.setValues(KEY_LIKE + boardId, String.valueOf(increment));
        }

        return redisUtil.getValues(KEY_LIKE + boardId);
    }
}
