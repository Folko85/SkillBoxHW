package com.skillbox.dating.repository;

import com.skillbox.dating.model.Mate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Repository
public class MateRepository implements RedisRepository {

    private static final String KEY = "Mate";

    private RedisTemplate<String, String> redisTemplate;

    private ZSetOperations zSetOperations;

    @Autowired
    public MateRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        zSetOperations = redisTemplate.opsForZSet();
    }

    public void add(Mate mate, double score) {
        zSetOperations.add(KEY, mate.toString(), score);
    }

    public void deleteMate(Mate mate) {
        zSetOperations.remove(KEY, mate.toString());
    }

    public void replaceTopMate() {
        zSetOperations.incrementScore(KEY, findTopMate().getValue().toString(), findAllMates().size());
    }

    public ZSetOperations.TypedTuple findTopMate() {
        Set<ZSetOperations.TypedTuple> typedTuple = zSetOperations.rangeWithScores(KEY, 0, 0);
        return typedTuple.stream().findFirst().orElse(null);
    }

    public ZSetOperations.TypedTuple findRandomMate() {
        int size = findAllMates().size();
        if (size == 0) {
            return null;
        }
        int bound = ThreadLocalRandom.current().nextInt(0, size);
        Set<ZSetOperations.TypedTuple> typedTuple = zSetOperations.rangeWithScores(KEY, bound, bound);
        return typedTuple.stream().findFirst().orElse(null);
    }

    public Set<Object> findAllMates() {
        return zSetOperations.range(KEY, 0, 100);
    }

    public void deleteAll() {
        zSetOperations.removeRange(KEY, 0, 100);
    }
}
