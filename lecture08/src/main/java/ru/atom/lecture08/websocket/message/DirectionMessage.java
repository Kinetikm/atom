package ru.atom.lecture08.websocket.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by kinetik on 19.04.17.
 */
public class DirectionMessage {
    private final Direction direction;

    @JsonCreator
    public DirectionMessage(@JsonProperty("direction") Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
