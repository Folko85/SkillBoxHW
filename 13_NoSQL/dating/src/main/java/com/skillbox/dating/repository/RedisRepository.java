package com.skillbox.dating.repository;

import com.skillbox.dating.model.Mate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

public interface RedisRepository {

    Set<Object> findAllMates();

    void add(Mate mate, double score);

    void deleteMate(Mate mate);

    void replaceTopMate();

    ZSetOperations.TypedTuple findTopMate();

    ZSetOperations.TypedTuple findRandomMate();

    void deleteAll();
}
