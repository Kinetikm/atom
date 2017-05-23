package ru.atom.gameinterfaces;

/**
 * Created by kinetik on 02.05.17.
 */
public interface Temporary extends Tickable, GameObject {
    /**
     * @return lifetime in milliseconds
     */
    long getLifetimeMillis();

    /**
     * Checks if gameObject is dead. If it becomes dead, executes death actions
     * @return true if GameObject is dead
     */
    boolean isDead();
}
