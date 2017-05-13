package ru.atom.geometry;

import ru.atom.gameinterfaces.Collider;

/**
 * Created by kinetik on 02.05.17.
 */
public class Point implements Collider {
    private long xCoord;
    private long yCoord;

    public Point(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public long getxCoord() {
        return this.xCoord;
    }

    public long getyCoord() {
        return this.yCoord;
    }

    public void setxCoord(long xCoord) {
        this.xCoord = xCoord;
    }

    public void setyCoord(long yCoord) {
        this.yCoord = yCoord;
    }

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Point point = (Point) o;
        if (point.getxCoord() == this.getxCoord() && point.getyCoord() ==  this.getyCoord()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = (int) (xCoord ^ (xCoord >>> 32));
        result = 31 * result + (int) (yCoord ^ (yCoord >>> 32));
        return result;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Point pointTwo = (Point) other;
        if (pointTwo.getxCoord() == this.getxCoord() && pointTwo.getyCoord() == this.getyCoord()) {
            return true;
        } else {
            return false;
        }
    }
}
