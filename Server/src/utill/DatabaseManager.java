package utill;

import java.sql.*;

public class DatabaseManager {
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "dany";
    private final String password = "ogc316";

    private Connection connection;

    public DatabaseManager(){
        //connection = connect();
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public Connection connect () throws SQLException {
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("sqlEx");
        }

        ResultSet rs = statement.executeQuery("SELECT * FROM product INNER JOIN person USING (passportId) INNER JOIN coordinates USING (id);");
        for (int i = 0; rs.next(); i++) {
            System.out.println(rs.getString("y"));
        }
        return connection;
    }

    public void saveCollection(CommandReceiver commandReceiver){

    }

    public void getCollection(){

    }
}
