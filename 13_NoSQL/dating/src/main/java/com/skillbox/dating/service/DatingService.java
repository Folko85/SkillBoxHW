package com.skillbox.dating.service;

import com.github.javafaker.Faker;
import com.skillbox.dating.model.Mate;
import com.skillbox.dating.repository.MateRepository;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DatingService {

    private final MateRepository repository;

    public DatingService(MateRepository repository) {
        this.repository = repository;
    }

    public void init() {
        repository.deleteAll();
        List<Mate> mates = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Faker faker = new Faker();
            Mate mate = new Mate();
            mate.setFullName(faker.name().fullName());
            mate.setRegistrationDate(faker.date().between(new Date(120, 0, 1), new Date(120, 11, 31)).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            mates.add(mate);
        }
        mates.sort(Comparator.comparing(Mate::getRegistrationDate).reversed());
        for (int i = 0; i < 10; i++){
            repository.add(mates.get(i), i);
        }
    }

    public List<Mate> getMates(){
        return repository.findAllMates().stream().map(o -> o.toString()).map(s -> new Mate (s)).collect(Collectors.toUnmodifiableList());
    }

    public List<Mate> replaceTopMate(){
        ZSetOperations.TypedTuple tuple = repository.findTopMate();
        Mate topMate = new Mate(tuple.getValue().toString());

        if (topMate.isPromo()){
            List<Mate> mates = getMates();
            endOfPromo(tuple);
            return mates;
        } else {
            repository.replaceTopMate();
            return getMates();
        }
    }

    public void endOfPromo(ZSetOperations.TypedTuple promoMate){
        Mate mate = new Mate(promoMate.getValue().toString());
        repository.deleteMate(mate);
        mate.setPromo(false);
        repository.add(mate, promoMate.getScore());

    }
}
