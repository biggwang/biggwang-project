package com.biggwang.web.racecondition;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class RedisOrderController {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String GOODS_NAME_PATNS = "PANTS";
    private static final String GOODS_CODE = "ABCDEF0000";
    private static final String BUYER_LIST_KEY = "BUYER";

    @GetMapping("/redis/init")
    public String init() {
        redisTemplate.opsForValue().set(GOODS_NAME_PATNS, GOODS_CODE);
        redisTemplate.opsForHash().entries(BUYER_LIST_KEY).keySet()
                .forEach(i -> redisTemplate.opsForHash().delete(BUYER_LIST_KEY, i));
        return "초기화";
    }

    @GetMapping("/redis/order/{user}")
    public String order(@PathVariable String user) {
        String orderedGoods = redisTemplate.opsForValue().get(GOODS_NAME_PATNS);
        if (Objects.isNull(orderedGoods)) {
            return "품절";
        }
        redisTemplate.opsForHash().put(BUYER_LIST_KEY, user, GOODS_CODE);
        redisTemplate.delete(GOODS_NAME_PATNS);
        return "구매";
    }

    @GetMapping("/redis/sync/order/{user}")
    public String syncOrder(@PathVariable String user) {
        String orderedGoods = redisTemplate.opsForValue().get(GOODS_NAME_PATNS);
        if (Objects.isNull(orderedGoods)) {
            return "품절";
        }
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.watch(BUYER_LIST_KEY);
                operations.watch(GOODS_NAME_PATNS);

                redisTemplate.multi();

                redisTemplate.opsForHash().put(BUYER_LIST_KEY, user, GOODS_CODE);
                redisTemplate.delete(GOODS_NAME_PATNS);

                return operations.exec();
            }
        });
        return "구매";
    }

    @GetMapping("/redis/status")
    public String status() {
        String orderedGoods = ObjectUtils.defaultIfNull(redisTemplate.opsForValue().get(GOODS_NAME_PATNS), "품절");
        Map buyers = redisTemplate.opsForHash().entries(BUYER_LIST_KEY);
        return String.format("재고:%s 구매자:%s", orderedGoods, buyers);
    }
}
