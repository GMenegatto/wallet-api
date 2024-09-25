package com.gmenegatto.wallet_api.service.idempotency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class IdempotencyService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public boolean isRequestProcessed(String requestId) {
        return redisTemplate.hasKey(requestId);
    }

    public void markRequestAsProcessed(String requestId) {
        redisTemplate.opsForValue().set(requestId, "processed", 1, TimeUnit.MINUTES);
    }
}
