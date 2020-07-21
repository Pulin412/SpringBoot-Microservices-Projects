package com.kalaha.game.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Player player;
    private Integer stones;
    private Integer position;
    private String pitType;
}
