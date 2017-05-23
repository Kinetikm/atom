package ru.atom.model;

import ru.atom.gameinterfaces.GameObject;
import ru.atom.gameinterfaces.Positionable;
import ru.atom.geometry.Point;

/**
 * Created by kinetik on 02.05.17.
 */
public class Wall implements Positionable, GameObject {

    private final int wallId;
    private Point position;

    public Wall(int wallId, Point position) {
        this.wallId = wallId;
        this.position = position;
    }

    @Override
    public int getId() {
        return this.wallId;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }
}