package ru.atom.network;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.atom.geometry.Direction;

/**
 * Created by kinetik on 02.05.17.
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
