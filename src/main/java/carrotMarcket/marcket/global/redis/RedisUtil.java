package carrotMarcket.marcket.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;


    public Set<String> getKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public Long extractBoardIdToKey(String key) {
        return Long.parseLong(key.substring(11));
    }

    public void setValues(String key, String data) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, data);
    }

    public String getValues(String key) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        return (String) values.get(key);
    }

    public void deleteDate(String key) {
        redisTemplate.delete(key);
    }

    public Boolean like(String key, String userId) {
        redisTemplate.opsForSet().add(key, userId);
        return isLike(key, userId);
    }

    public Boolean unLike(String key, String userId) {
        redisTemplate.opsForSet().remove(key, userId);
        return isLike(key, userId);
    }

    public Boolean isLike(String key, String userId) {
        return redisTemplate.opsForSet().isMember(key, userId);
    }

    public Long getLikeCount(String key) {
        return redisTemplate.opsForSet().size(key);
    }
}
