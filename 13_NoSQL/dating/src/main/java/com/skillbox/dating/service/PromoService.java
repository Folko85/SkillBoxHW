package com.skillbox.dating.service;

import com.skillbox.dating.model.Mate;
import com.skillbox.dating.repository.MateRepository;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PromoService {

    private final MateRepository mateRepository;

    private final DatingService datingService;

    public PromoService(MateRepository mateRepository, DatingService datingService) {
        this.mateRepository = mateRepository;
        this.datingService = datingService;
    }

    @Scheduled(fixedDelay = 30000)
    public void setPromo() {
        ZSetOperations.TypedTuple<Mate> tuple = mateRepository.findRandomMate();
        Mate promoMate = tuple.getValue();
        assert promoMate != null;
        double topScore = mateRepository.findTopMate().getScore();
        mateRepository.deleteMate(promoMate);
        promoMate.setPromo(true);
        mateRepository.add(promoMate, topScore - 1);
    }
}
