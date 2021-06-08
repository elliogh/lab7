package utill;

import collection.Product;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;


public class DatabaseManager {
    private final String url = "jdbc:postgresql://pg/studs";
    private final String user = "";
    private final String password = "";
    private Connection connection;

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void connect (TreeMap<Integer, Product> collection) throws SQLException {
        connection = null;
        Statement statement = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver не найден");
        } catch (NullPointerException e) {
            System.out.println("SQL exception");
        }

        ResultSet rs = statement.executeQuery("SELECT * FROM person INNER JOIN product USING (passportid) ;");

        Parser.parseDatabase(rs, collection);
        this.connection = connection;
    }

    public void saveCollection(TreeMap<Integer, Product> collection){
        try {
            Statement statement = connection.createStatement();

            statement.executeUpdate("DELETE FROM product");
            statement.executeUpdate("DELETE FROM person");

            for (Map.Entry<Integer, Product> e : collection.entrySet()) {

                statement.executeUpdate("INSERT INTO person (passportid, ownername, birthday, height, weight) VALUES ("
                        + "\'" + e.getValue().getOwner().getPassportID() + "\',"
                        + "\'" + e.getValue().getOwner().getName() + "\',"
                        + "\'" + e.getValue().getOwner().getBirthday() + "\'" + "::date,"
                        + e.getValue().getOwner().getHeight() + ","
                        + e.getValue().getOwner().getWeight() + ");"
                );

                statement.executeUpdate("INSERT INTO product (name, x, y, creationdate, price, partNumber, manufactureCost, unitOfMeasure, passportId, creator) VALUES ("
                        + "\'" + e.getValue().getName() + "\',"
                        + e.getValue().getCoordinates().getX() +","
                        + e.getValue().getCoordinates().getY() + ","
                        + "\'" + e.getValue().getCreationDate() +"\'" + "::date,"
                        + e.getValue().getPrice() + ","
                        + "\'" + e.getValue().getPartNumber() + "\',"
                        + e.getValue().getManufactureCost() + ","
                        + "\'" + e.getValue().getUnitOfMeasure() + "\',"
                        + "\'" + e.getValue().getOwner().getPassportID() + "\',"
                        + "\'" + e.getValue().getCreator()  + "\');"
                );

            }

        } catch (SQLException e) {
            System.out.println("exception");
        }
    }

    public boolean checkUser(ServerUser serverUser){
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM users");
            for (;result.next();){
                String login = result.getString("login");
                String password = result.getString("password");

                if (serverUser.getLogin().equals(login)){
                    if (hash(serverUser.getPassword()).equals(password)){
                        return true;
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void registerUser(ServerUser serverUser){
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO users(login, password) VALUES( \'" + serverUser.getLogin() + "\'," +
                                                                                                    "\'" + hash(serverUser.getPassword()) + "\');");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //TODO Переписать на SHA-256
    public String hash(String str){
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
        String encoded = Base64.getEncoder().encodeToString(hash);
        return encoded;
    }
}
