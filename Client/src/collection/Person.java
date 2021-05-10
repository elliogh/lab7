package collection;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Класс владельца
 */
public class Person implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Date birthday; //Поле может быть null
    private Float height; //Поле может быть null, Значение поля должно быть больше 0
    private long weight; //Значение поля должно быть больше 0
    private String passportID; //Длина строки не должна быть больше 50, Значение этого поля должно быть уникальным, Длина строки должна быть не меньше 6, Поле может быть null

    public Person(String name, Date birthday, Float height, long weight, String passportID) {
        this.name = name;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.passportID = passportID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if ( (name != null) && (name != "") ) {
            this.name = name;
        }
        else System.out.println("Введите имя еще раз");
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    @Override
    public String toString() {
        return  "          Имя: " + name + "\n" +
                "          День рождения: " + birthday + "\n" +
                "          Рост: " + height + "\n" +
                "          Вес: " + weight + "\n" +
                "          Номер паспорта: " + passportID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return weight == person.weight && name.equals(person.name) && birthday.equals(person.birthday) && height.equals(person.height) && passportID.equals(person.passportID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday, height, weight, passportID);
    }
}

