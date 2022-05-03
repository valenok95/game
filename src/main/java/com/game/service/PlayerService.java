package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    /**
     * Получение всех игроков по параметрам
     */
    public List<Player> getPlayersByParams(String name,
                                           String title,
                                           Race race,
                                           Profession profession,
                                           Long after,
                                           Long before,
                                           Boolean banned,
                                           Integer minExperience,
                                           Integer maxExperience,
                                           Integer minLevel,
                                           Integer maxLevel,
                                           PlayerOrder order,
                                           Integer pageNumber,
                                           Integer pageSize
    ) {
        if (order == null) {
            order = PlayerOrder.ID;
        }
        if (pageNumber == null) {
            pageNumber = 0;
        }
        if (pageSize == null) {
            pageSize = 3;
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));
        List<Player> resultList = playerRepository.findAll(pageable).get().filter(
                player -> {
                    if (name == null) {
                        return true;
                    } else {
                        return player.getName().contains(name);
                    }
                }).filter(
                player -> {
                    if (title == null) {
                        return true;
                    } else {
                        return player.getTitle().contains(title);
                    }
                }).filter(
                player -> {
                    if (race == null) {
                        return true;
                    } else {
                        return race.equals(player.getRace());
                    }
                }).filter(
                player -> {
                    if (profession == null) {
                        return true;
                    } else {
                        return profession.equals(player.getProfession());
                    }
                }).filter(
                player -> {
                    if (after == null) {
                        return true;
                    } else {
                        return after > player.getBirthday().getTime();
                    }
                }).filter(
                player -> {
                    if (before == null) {
                        return true;
                    } else {
                        return before < player.getBirthday().getTime();
                    }
                }).filter(
                player -> {
                    if (banned == null) {
                        return true;
                    } else {
                        return player.getBanned() == banned;
                    }
                }).filter(
                player -> {
                    if (minExperience == null) {
                        return true;
                    } else {
                        return minExperience <= player.getExperience();
                    }
                }).filter(
                player -> {
                    if (maxExperience == null) {
                        return true;
                    } else {
                        return maxExperience >= player.getExperience();
                    }
                }).filter(
                player -> {
                    if (minLevel == null) {
                        return true;
                    } else {
                        return minLevel <= player.getLevel();
                    }
                }).filter(
                player -> {
                    if (maxLevel == null) {
                        return true;
                    } else {
                        return maxLevel >= player.getLevel();
                    }
                }).collect(Collectors.toList());
        /*Set<CisDto> result =
                cisRepository.findByIdHeader(idHeader, includeFields).stream()
                        .map(cisMapper::model2CisDto)
                        .collect(Collectors.toSet());
        checkEmptyCollection(result, String.format(CISES_NOT_FOUND_MSG, idHeader));
        log.trace(
                "Из БД Mongo получено {} обогащенных КИЗов для документа с idHeader {}",
                result.size(),
                idHeader);*/

        return resultList;
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id).orElse(null);

    }

}
