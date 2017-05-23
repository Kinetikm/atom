package ru.atom.gameinterfaces;

/**
 * Created by kinetik on 02.05.17.
 */
public interface Tickable {
    /**
     * Applies changes to game objects that happen after elapsed time
     */
    void tick(long elapsed);
}
