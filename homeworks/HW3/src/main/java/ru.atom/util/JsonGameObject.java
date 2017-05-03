package ru.atom.util;

import ru.atom.gameinterfaces.Positionable;

/**
 * Created by kinetik on 02.05.17.
 */
public class JsonGameObject {
    private String type;
    private int id;
    private String positionString;

    public JsonGameObject(Positionable object) {
        this.id = object.getId();
        this.type = object.getClass().getSimpleName();
        Position position = new Position(object.getPosition().getxCoord(), object.getPosition().getyCoord());
        this.positionString = JsonHelper.toJson(position);
    }

    public String toJsonPosition() {
        return JsonHelper.toJson(this);
    }

    private class Position {
        int x;
        int y;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private enum ObjectTypes {
        Pawn,
        Bomb,
        Wall,
        Wood,
        Fire
    }
}