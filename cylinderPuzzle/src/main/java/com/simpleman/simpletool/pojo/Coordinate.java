package com.simpleman.simpletool.pojo;

import lombok.Data;
import lombok.Setter;

import java.util.Objects;

/**
 * 坐标
 *
 * @author l30035293
 * @since 2024-04-29
 */
@Data
public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
