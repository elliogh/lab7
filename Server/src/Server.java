import utill.Controller;
import utill.DatabaseManager;

import java.sql.SQLException;
import java.util.*;

public class Server{

    public static void main(String[] args) {
        System.out.println("Начало работы сервера:");

        DatabaseManager databaseManager = new DatabaseManager();
        try {
            databaseManager.connect();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        int port = askPort();
        Controller controller = new Controller(port);
        controller.run();
    }

    public static int askPort(){
        Scanner consoleScanner = new Scanner(System.in);
        int port = 0;
        String input;
        System.out.print("Введите порт: ");
        try {
            input = consoleScanner.nextLine();
            port = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Неправильный порт");
            System.exit(1);
        } catch (NoSuchElementException e) {
            System.out.println("Завершение работы сервера...");
            System.exit(0);
        }
        return port;
    }

}