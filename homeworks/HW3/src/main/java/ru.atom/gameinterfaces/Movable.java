package ru.atom.gameinterfaces;

import ru.atom.geometry.Point;

/**
 * Created by kinetik on 02.05.17.
 */
public interface Movable extends Positionable, Tickable {
    /**
     * Tries to move entity towards specified direction
     * @return final position after movement
     */
    Point move(Direction direction);

    enum Direction {
        UP, DOWN, RIGHT, LEFT, IDLE
    }
}