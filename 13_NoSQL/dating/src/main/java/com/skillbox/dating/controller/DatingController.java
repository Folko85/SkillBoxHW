package com.skillbox.dating.controller;

import com.skillbox.dating.model.Mate;
import com.skillbox.dating.service.DatingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DatingController {

    private final DatingService datingService;

    public DatingController(DatingService datingService) {
        this.datingService = datingService;
    }


    @GetMapping("/mates")
    public List<Mate> getMates (){
        return datingService.getMates();
    }

    @PostMapping("/mates")
    public List<Mate> createMates(){
        datingService.init();
        return getMates();
    }

    @PostMapping("/replace")
    public List<Mate> replaceMates(){
        return datingService.replaceTopMate();
    }

}
