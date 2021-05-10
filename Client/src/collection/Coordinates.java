package collection;

import java.io.Serializable;

/**
 * Класс координат
 */
public class Coordinates implements Serializable {
    private double x;
    private Float y; //Максимальное значение поля: 834, Поле не может быть null

    public Coordinates(double x, Float y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        if ( (y <= 834) && (y != null) ) {
            this.y = y;
        }
    }

    @Override
    public String toString() {
        return  "x=" + x +
                ", y=" + y ;
    }
}
