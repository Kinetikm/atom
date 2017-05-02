package ru.atom.gameinterfaces;

import ru.atom.geometry.Point;

/**
 * Created by kinetik on 02.05.17.
 */
public interface Positionable extends GameObject {
    /**
     * @return Current position
     */
    Point getPosition();
}
