package com.game.entity;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "player")
public class Player {
    // id игрока
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 12)
    private String name; //Имя персонажа (до 12 знаков включительно)
    @Column(nullable = false, length = 30)
    private String title; //Титул персонажа (до 30 знаков включительно)
    @Enumerated(EnumType.STRING)
    private Race race; //Расса персонажа
    @Enumerated(EnumType.STRING)
    private Profession profession; //Профессия персонажа
    private Integer experience; //Опыт персонажа. Диапазон значений 0..10,000,000
    private Integer level; // Уровень персонажа
    private Integer untilNextLevel; //Остаток опыта до следующего уровня
    private Date birthday; //Дата регистрации
    //Диапазон значений года 2000..3000 включительно
    private Boolean banned; //Забанен / не забанен

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        int size = name.length();
        if(size<=12){
            this.name = name;
        } else {
            throw new RuntimeException(String.format("Длина имени %s равна %s - не должна превышать 12.",name,size));
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        int size = title.length();
        if(size<=30){
            this.title = title;
        } else {
            throw new RuntimeException(String.format("Длина титула %s равна %s - не должна превышать 30.",title,size));
        }
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        if(experience>=0 && experience<=10000000){
            this.experience = experience;
        } else {
            throw new RuntimeException(String.format("Диапазон значений опыта персонажа 0..10,000,000. Передано значение %d",experience));
        }
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setLevel() {
        Integer result = (int) (Math.sqrt(2500d + getExperience()*200d)-50)/100;
        this.level = result;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }
    public void setUntilNextLevel() {
        Integer result = 50*(getLevel()+1)*(getLevel()+2)-getExperience();
        this.untilNextLevel = result;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        int year = birthday.getYear();
        if(year>3000 || year<2000){
            this.birthday = birthday;
        } else {
            throw new RuntimeException(String.format("Год рождения %d: не должен выходить за диапазон 2000...3000",year));
        }
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }
}
