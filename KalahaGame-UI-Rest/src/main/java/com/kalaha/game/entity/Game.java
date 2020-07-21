package com.kalaha.game.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ElementCollection
    @OneToMany(cascade=CascadeType.ALL)
    private Map<Integer, Pit> pitMap;
    private Integer status;
    private Integer nextMove;
    private Integer winner;
}
