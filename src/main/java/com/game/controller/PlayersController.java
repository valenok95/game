package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/players")
public class PlayersController {
    @Autowired
    private PlayerService playerService;

    @GetMapping
    public List<Player> getPlayersWithParams(
            @RequestParam (required = false) String name,
            @RequestParam (required = false) String title,
            @RequestParam (required = false) Race race,
            @RequestParam (required = false) Profession profession,
            @RequestParam (required = false) Long after,
            @RequestParam (required = false) Long before,
            @RequestParam (required = false) Boolean banned,
            @RequestParam (required = false) Integer minExperience,
            @RequestParam (required = false) Integer maxExperience,
            @RequestParam (required = false) Integer minLevel,
            @RequestParam (required = false) Integer maxLevel,
            @RequestParam (required = false) PlayerOrder order,
            @RequestParam (required = false) Integer pageNumber,
            @RequestParam (required = false) Integer pageSize) {

        return playerService.getPlayersByParams(name, title, race, profession, after, before, banned, minExperience,
                maxExperience, minLevel, maxLevel, order, pageNumber, pageSize).getContent();
    }
}
