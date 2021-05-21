package utill;

import collection.Coordinates;
import collection.Person;
import collection.Product;
import collection.UnitOfMeasure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;

public class Parser {
    public static void parseDatabase(ResultSet rs, TreeMap<Integer, Product> collection) throws SQLException {
        for (int i = 0; rs.next(); i++) {
            try {
                Product product = new Product(rs.getInt("id"),
                        rs.getString("name"),
                        new Coordinates(rs.getDouble("x"), rs.getFloat("y")),
                        rs.getDate("creationdate"),
                        rs.getInt("price"),
                        rs.getString("partnumber"),
                        rs.getFloat("manufacturecost"),
                        UnitOfMeasure.valueOf(rs.getString("unitofmeasure")),
                        new Person(rs.getString("ownername"), rs.getDate("birthday"), rs.getFloat("height"), rs.getLong("weight"), rs.getString("passportid")));
                collection.put(product.getId(), product);
            } catch (SQLException e) {
                System.out.println("exception");
            }
        }

    }
}
