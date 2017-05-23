package ru.atom.model;

import ru.atom.geometry.Direction;

/**
 * Created by kinetik on 11.05.17.
 */
public class Action {
    private Type type;
    private Direction direction;
    private String player;

    public Action(Type type, String player) {
        this.type = type;
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public enum Type {
        PLANT, MOVE
    }
}
