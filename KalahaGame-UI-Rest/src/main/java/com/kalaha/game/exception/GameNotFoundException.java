package com.kalaha.game.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameNotFoundException extends Exception {

    private String message;
}
