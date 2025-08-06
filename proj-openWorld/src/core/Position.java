package core;

import java.util.Objects;

public class Position {
    public int x_axis;
    public int y_axis;


    public Position(int x, int y) {
        x_axis = x;
        y_axis = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position)){
            return false;
        }
        Position p = (Position) o;
        return x_axis == p.x_axis && y_axis == p.y_axis;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x_axis, y_axis);
    }
}
