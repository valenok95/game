package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.*;
import com.game.repository.PlayerCriteriaRepository;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PlayerCriteriaRepository playerCriteriaRepository;

    /**
     * Получение всех игроков по параметрам
     */
    public Page<Player> getPlayersByParams(String name,
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
        PlayerPage playerPage = new PlayerPage();
        if (Objects.nonNull(order)) {
            playerPage.setSortBy(order.getFieldName());
        }
        if (Objects.nonNull(pageNumber)) {
            playerPage.setPageNumber(pageNumber);
        }
        if (Objects.nonNull(pageSize)) {
            playerPage.setPageSize(pageSize);
        }
        PlayerSearchCriteria playerSearchCriteria = new PlayerSearchCriteria();
        if (Objects.nonNull(name)) {
            playerSearchCriteria.setName(name);
        }
        if (Objects.nonNull(title)) {
            playerSearchCriteria.setTitle(title);
        }
        if (Objects.nonNull(race)) {
            playerSearchCriteria.setRace(race);
        }
        if (Objects.nonNull(profession)) {
            playerSearchCriteria.setProfession(profession);
        }
        if (Objects.nonNull(after)) {
            playerSearchCriteria.setAfter(after);
        }
        if (Objects.nonNull(before)) {
            playerSearchCriteria.setBefore(before);
        }
        if (Objects.nonNull(banned)) {
            playerSearchCriteria.setBanned(banned);
        }
        if (Objects.nonNull(minExperience)) {
            playerSearchCriteria.setMinExperience(minExperience);
        }
        if (Objects.nonNull(maxExperience)) {
            playerSearchCriteria.setMaxExperience(maxExperience);
        }
        if (Objects.nonNull(minLevel)) {
            playerSearchCriteria.setMinLevel(minLevel);
        }
        if (Objects.nonNull(maxLevel)) {
            playerSearchCriteria.setMaxLevel(maxLevel);
        }
        return playerCriteriaRepository.findAllWithParams(playerPage, playerSearchCriteria);
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id).orElse(null);

    }

}
