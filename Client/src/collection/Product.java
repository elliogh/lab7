package collection;

import java.io.Serializable;
import java.util.Date;

/**
 * Класс продукта
 */
public class Product implements Serializable {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int price; //Значение поля должно быть больше 0
    private String partNumber; //Поле может быть null
    private Float manufactureCost; //Поле может быть null
    private UnitOfMeasure unitOfMeasure; //Поле может быть null
    private Person owner; //Поле может быть null

    public Product(Integer id, String name, Coordinates coordinates, Date creationDate, int price, String partNumber, Float manufactureCost, UnitOfMeasure unitOfMeasure, Person owner) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.partNumber = partNumber;
        this.manufactureCost = manufactureCost;
        this.unitOfMeasure = unitOfMeasure;
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
        else System.out.println("Ведите имя еще раз");
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates != null) {
            this.coordinates = coordinates;
        }
        else System.out.println("Введите координаты еще раз");
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate() {
        Date creationDate = new Date();
        this.creationDate = creationDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        if (price > 0) {
            this.price = price;
        }
        else System.out.println("Введите цену еще раз");
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        if (partNumber != null) {
            this.partNumber = partNumber;
        }
    }

    public Float getManufactureCost() {
        return manufactureCost;
    }

    public void setManufactureCost(Float manufactureCost) {
        if (manufactureCost != null) {
            this.manufactureCost = manufactureCost;
        }
        else System.out.println("Ведите цену производства еще раз");
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        if (unitOfMeasure != null) {
            this.unitOfMeasure = unitOfMeasure;
        }
        else System.out.println("Введите единицы измерения еще раз");
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        if (owner != null) {
            this.owner = owner;
        }
        else System.out.println("Введите еще раз");
    }

    @Override
    public String toString() {
        String nameOfUnitOFMeasure = "";
        switch (unitOfMeasure) {
            case GRAMS:
                nameOfUnitOFMeasure = "Грамм";
                break;
            case METERS:
                nameOfUnitOFMeasure = "Метр";
                break;
            case CENTIMETERS:
                nameOfUnitOFMeasure = "Сантиметр";
                break;
        }
        return  "Id: " + id + "\n" +
                "Название: " + name + "\n" +
                "Координаты: " + coordinates + "\n" +
                "Дата создания: " + creationDate + "\n" +
                "Цена: " + price + "\n" +
                "Номер части: " + partNumber + "\n" +
                "Стоимость производства: " + manufactureCost + "\n" +
                "Единица измерения: " + nameOfUnitOFMeasure + "\n" +
                "Владелец: " + "\n" + owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return price == product.price && id.equals(product.id) && name.equals(product.name) && coordinates.equals(product.coordinates) && creationDate.equals(product.creationDate) && partNumber.equals(product.partNumber) && manufactureCost.equals(product.manufactureCost) && unitOfMeasure == product.unitOfMeasure && owner.equals(product.owner);
    }

    @Override
    public int hashCode() {
        return (int) (price * manufactureCost);
    }
}
