package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/rest/players")
public class PlayersController {
    @Autowired
    private PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<Player>> getPlayersWithParams(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Race race,
            @RequestParam(required = false) Profession profession,
            @RequestParam(required = false) Long after,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Boolean banned,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false) Integer maxExperience,
            @RequestParam(required = false) Integer minLevel,
            @RequestParam(required = false) Integer maxLevel,
            @RequestParam(required = false) PlayerOrder order,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize) {

        return ResponseEntity.status(HttpStatus.OK).body(playerService.getPlayersByParams(name, title, race, profession, after, before, banned, minExperience,
                maxExperience, minLevel, maxLevel, order, pageNumber, pageSize).getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getPlayersCountWithParams(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Race race,
            @RequestParam(required = false) Profession profession,
            @RequestParam(required = false) Long after,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Boolean banned,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false) Integer maxExperience,
            @RequestParam(required = false) Integer minLevel,
            @RequestParam(required = false) Integer maxLevel) {

        return ResponseEntity.status(HttpStatus.OK).body(playerService.getPlayersCountByParams(name, title, race, profession, after, before, banned, minExperience,
                maxExperience, minLevel, maxLevel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayersCountWithParams(
            @PathVariable("id") long id) {
        if (id < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Player returnedPlayer = playerService.getPlayerById(id);
        if (Objects.isNull(returnedPlayer)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(playerService.getPlayerById(id));
    }

    @PostMapping
    public ResponseEntity createPlayer(
            @RequestBody Player player) {
        Player newPlayer = playerService.createPlayer(player);
        if (Objects.isNull(newPlayer)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(newPlayer);
    }
    @PostMapping("/{id}")
    public ResponseEntity updatePlayer(
            @PathVariable("id") long id,
            @RequestBody Player player) {
        if ( id < 1 || !playerService.validateUpdatePlayerEntity(player)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        player.setId(id);
        Player newPlayer = playerService.updatePlayer(player);
        if (Objects.isNull(newPlayer)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(newPlayer);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity updatePlayer(
            @PathVariable("id") long id) {
        if ( id < 1 ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if(playerService.deletePlayerById(id)){
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}