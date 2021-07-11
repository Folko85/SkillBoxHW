package com.skillbox.dating.service;

import com.github.javafaker.Faker;
import com.skillbox.dating.model.Mate;
import com.skillbox.dating.repository.MateRepository;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class DatingService {

    private final String pattern = "yyyy-MM-dd";

    private DateFormat dateFormat = new SimpleDateFormat(pattern);

    private final MateRepository repository;

    public DatingService(MateRepository repository) {
        this.repository = repository;
    }

    public void init() {
        repository.deleteAll();
        List<Mate> mates = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Faker faker = new Faker(new Locale("RU"));
            Mate mate = new Mate();
            mate.setFullName(faker.name().fullName());
            mate.setRegistrationDate(dateFormat.format(faker.date().birthday(1, 5)));
            mates.add(mate);
        }
        mates.sort(Comparator.comparing(Mate::getRegistrationDate).reversed());
        for (int i = 0; i < 10; i++){
            repository.add(mates.get(i), i);
        }
    }

    public List<Mate> getMates(){
        return repository.findAllMates().stream().collect(Collectors.toUnmodifiableList());
    }

    public List<Mate> replaceTopMate(){
        ZSetOperations.TypedTuple<Mate> tuple = repository.findTopMate();
        Mate topMate = tuple.getValue();

        assert topMate != null;
        if (topMate.isPromo()){
            List<Mate> mates = getMates();
            endOfPromo(tuple);
            return mates;
        } else {
            repository.replaceTopMate();
            return getMates();
        }
    }

    public void endOfPromo(ZSetOperations.TypedTuple<Mate> promoMate){
        Mate mate = promoMate.getValue();
        repository.deleteMate(mate);
        mate.setPromo(false);
        repository.add(mate, promoMate.getScore());

    }
}
