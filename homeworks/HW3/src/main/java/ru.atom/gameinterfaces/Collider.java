package ru.atom.gameinterfaces;

/**
 * Created by kinetik on 02.05.17.
 */
public interface Collider {
    /**
     * @return true if Colliders geometrically intersect
     */
    boolean isColliding(Collider other);
}
