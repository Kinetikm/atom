package ru.atom.model;

/**
 * Created by kinetik on 02.05.17.
 */
import ru.atom.gameinterfaces.GameObject;
import ru.atom.gameinterfaces.Positionable;
import ru.atom.gameinterfaces.Temporary;
import ru.atom.gameinterfaces.Tickable;
import ru.atom.geometry.Point;

/**
 * Created by kinetik on 07.03.17.
 */
public class Bomb implements GameObject, Tickable, Temporary, Positionable {

    private final int id;
    private final Point position;
    private long lifePeriod;
    private long timePass;

    public Bomb(int id, Point position, long lifePeriod, long timePass) {

        this.id = id;
        this.position = position;
        if (lifePeriod < 0) {
            throw new IllegalArgumentException();
        }
        this.lifePeriod = lifePeriod;
        this.timePass = timePass;
    }

    @Override
    public int getId() {
        return this.id;
    }


    @Override
    public void tick(long elapsed) {
        this.timePass += elapsed;
    }

    @Override
    public long getLifetimeMillis() {
        return this.lifePeriod;
    }

    @Override
    public boolean isDead() {
        return this.timePass > this.lifePeriod;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }
}