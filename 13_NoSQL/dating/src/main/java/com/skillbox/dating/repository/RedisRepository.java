package com.skillbox.dating.repository;

import com.skillbox.dating.model.Mate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

public interface RedisRepository {

    Set<Mate> findAllMates();

    void add(Mate mate, double score);

    void deleteMate(Mate mate);

    void replaceTopMate();

    ZSetOperations.TypedTuple<Mate> findTopMate();

    ZSetOperations.TypedTuple<Mate> findRandomMate();

    void deleteAll();
}
