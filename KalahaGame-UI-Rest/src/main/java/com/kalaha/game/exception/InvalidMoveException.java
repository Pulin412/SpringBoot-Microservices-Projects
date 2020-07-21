package com.kalaha.game.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidMoveException extends Exception {

    private String message;
}
