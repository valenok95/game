package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.*;
import com.game.repository.PlayerCriteriaRepository;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
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

    public int getPlayersCountByParams(String name,
                                       String title,
                                       Race race,
                                       Profession profession,
                                       Long after,
                                       Long before,
                                       Boolean banned,
                                       Integer minExperience,
                                       Integer maxExperience,
                                       Integer minLevel,
                                       Integer maxLevel) {
        return (int) getPlayersByParams(name, title, race, profession, after,
                before, banned, minExperience, maxExperience,
                minLevel, maxLevel, null, null, null).getTotalElements();
    }

    public Player createPlayer(Player player) {
        if (Objects.isNull(player.getName()) ||
                Objects.isNull(player.getTitle()) ||
                Objects.isNull(player.getRace()) ||
                Objects.isNull(player.getProfession()) ||
                Objects.isNull(player.getBirthday()) ||
                Objects.isNull(player.getExperience())
        ) {
            return null;
        }
        if (player.getName().length() > 12 || player.getTitle().length() > 30 ||
                player.getExperience() > 10_000_000) {
            return null;
        }
        if (Objects.isNull(player.getBanned())) {
            player.setBanned(false);
        }
        player.setId(null);
        player.setLevel();
        player.setUntilNextLevel();

        return playerRepository.save(player);
    }

    public Player updatePlayer(Player player) {
        boolean needToUpdate = false;
        Player updatingPlayer = getPlayerById(player.getId());
        if (Objects.isNull(updatingPlayer)) {
            return null;
        }
        if (Objects.nonNull(player.getName())) {
            updatingPlayer.setName(player.getName());
            needToUpdate = true;
        }
        if (Objects.nonNull(player.getTitle())) {
            updatingPlayer.setTitle(player.getTitle());
            needToUpdate = true;
        }
        if (Objects.nonNull(player.getRace())) {
            updatingPlayer.setRace(player.getRace());
            needToUpdate = true;
        }
        if (Objects.nonNull(player.getProfession())) {
            updatingPlayer.setProfession(player.getProfession());
            needToUpdate = true;
        }
        if (Objects.nonNull(player.getBirthday())) {
            updatingPlayer.setBirthday(player.getBirthday());
            needToUpdate = true;
        }
        if (Objects.nonNull(player.getBanned())) {
            updatingPlayer.setBanned(player.getBanned());
            needToUpdate = true;
        }
        if (Objects.nonNull(player.getExperience())) {
            updatingPlayer.setExperience(player.getExperience());
            updatingPlayer.setLevel();
            updatingPlayer.setUntilNextLevel();
            needToUpdate = true;
        }
        if (needToUpdate) {
            return playerRepository.save(updatingPlayer);
        } else {
            return updatingPlayer;
        }
    }

    public boolean validateUpdatePlayerEntity(Player player) {
        if ((Objects.nonNull(player.getExperience()) && (player.getExperience() < 0 || player.getExperience() > 10_000_000)) ||
                (Objects.nonNull(player.getName()) && player.getName().length() > 12) ||
                (Objects.nonNull(player.getTitle()) && player.getTitle().length() > 30) ||
                (Objects.nonNull(player.getBirthday())) && player.getBirthday().getTime() < 0) {
            return false;
        }
        return true;
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id).orElse(null);

    }

    public boolean deletePlayerById(Long id) {
        if (Objects.isNull(getPlayerById(id))) {
            return false;
        } else {
            playerRepository.deleteById(id);
            return true;
        }
    }
}