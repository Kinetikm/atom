package ru.atom.geometry;

import ru.atom.gameinterfaces.Collider;

/**
 * Created by kinetik on 02.05.17.
 */
public class Point implements Collider {
    private int x;
    private int y;
    private static int MAX_X = 16 * 32;
    private static int MAX_Y = 12 * 32;

    public Point(int x, int y) {
        if (x >= 0 && x < MAX_X) {
            this.x = x;
        } else {
            this.x = x <= 0 ? 1 : MAX_X - 1;
        }
        if (y >= 0 && y < MAX_Y) {
            this.y = y;
        } else {
            this.y = y <= 0 ? 1 : MAX_Y - 1;
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        if (x > 0 && x < MAX_X) {
            this.x = x;
        } else {
            this.x = x < 0 ? MAX_X - 1 : 1;
        }
    }

    public void setY(int y) {
        if (y > 0 && y < MAX_Y) {
            this.y = y;
        } else {
            this.y = y < 0 ? MAX_Y - 1 : 1;
        }
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
        if (point.getX() == this.getX() && point.getY() ==  this.getY()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = x ^ (x >>> 32);
        result = 31 * result + y ^ (y >>> 32);
        return result;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Point pointTwo = (Point) other;
        if (pointTwo.getX() == this.getX() && pointTwo.getY() == this.getY()) {
            return true;
        } else {
            return false;
        }
    }
}
