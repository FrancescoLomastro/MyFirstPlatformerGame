package org.example.Utility;

import java.awt.*;

public class Pair{
    private Integer first;
    private Point second;

    public Pair(Integer first, Point second) {
        this.second = second;
        this.first = first;
    }

    public Integer getFirst() {
        return first;
    }

    public Point getSecond() {
        return second;
    }
}
