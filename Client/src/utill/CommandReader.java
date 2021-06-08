package utill;

import collection.Coordinates;
import collection.Person;
import collection.Product;
import collection.UnitOfMeasure;
import commands.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Класс Парсер(Parser). Делает парсинг команд
 */
public class CommandReader {
    private static final ArrayList<String> listOfPaths = new ArrayList<>();
    private static boolean isRec = false;
    private static boolean isExecuteScript = false;
    private static final ArrayList<String> listOfCommands = new ArrayList<>();
    private static String argument = "";
    private final ArrayList<String> scriptPaths = new ArrayList<>();
    private final String[] fields = new String[12];


    /**
     * Метод для парсинга и запуска команды
     * @param input аргументы команды
     */
    public void parseCommand(String[] input, DatagramSocket socket, SocketAddress address, Scanner scanner, String login) {
        String commandKey = input[0];
        String[] ar = Arrays.copyOfRange(input, 1, input.length);
        switch (commandKey) {
            case "help" :
                send(new HelpCommand(), socket, address);
                receive(socket);
                break;
            case "info" :
                send(new InfoCommand(), socket, address);
                receive(socket);
                break;
            case "show" :
                send(new ShowCommand(), socket, address);
                receive(socket);
                break;
            case "insert" :
                try {
                    //Проверяем на execute_script
                    if (isExecuteScript) send(new InsertCommand(Integer.parseInt(argument), insertProduct(argument.split(" "), login), login), socket, address);
                    else send(new InsertCommand(Integer.parseInt(ar[0]), insertProduct(ar, login), login), socket, address);
                    receive(socket);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Недостаточно аргументов");
                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат ключа");
                }
                break;
            case "update" :
                try {
                    //Проверяем на execute_script
                    if (isExecuteScript) send(new UpdateCommand(Integer.parseInt(argument), insertProduct(argument.split(" "), login), login), socket, address);
                    else send(new UpdateCommand(Integer.parseInt(ar[0]), insertProduct(ar, login), login), socket, address);
                    receive(socket);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Недостаточно аргументов");
                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат ключа");
                }
                break;
            case "remove_key" :
                try {
                    send(new RemoveKeyCommand(Integer.parseInt(ar[0]), login), socket, address);
                    receive(socket);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Недостаточно аргументов");
                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат ключа");
                }
                break;
            case "clear" :
                send(new ClearCommand(), socket, address);
                receive(socket);
                break;
            case "execute_script" :
                isExecuteScript = true;
                ArrayList<String> script = readScript(ar[0]);
                scriptPaths.add(ar[0]);
                if (checkRecurssionInScript(script, scriptPaths)) {
                    System.out.println("Рекурсия");
                }
                else {
                    try {
                        readWholeScript(script); //Читаем полный скрипт и записываем в listOfCommands
                        for (int i = 0; i < listOfCommands.size(); i++) {
                            String[] in = listOfCommands.get(i).trim().split(" ");
                            if (in[0].equals("insert") || in[0].equals("update") || in[0].equals("remove_lower")) {
                                if (!in[0].equals("remove_lower")) argument = in[1];
                                if (listOfCommands.size() - i <= 12) {
                                    System.out.println("Недостаточно полей");
                                    break;
                                }
                                else {
                                    fields[0] = listOfCommands.get(i + 1);
                                    fields[1] = listOfCommands.get(i + 2);
                                    fields[2] = listOfCommands.get(i + 3);
                                    fields[3] = listOfCommands.get(i + 4);
                                    fields[4] = listOfCommands.get(i + 5);
                                    fields[5] = listOfCommands.get(i + 6);
                                    fields[6] = listOfCommands.get(i + 7);
                                    fields[7] = listOfCommands.get(i + 8);
                                    fields[8] = listOfCommands.get(i + 9);
                                    fields[9] = listOfCommands.get(i + 10);
                                    fields[10] = listOfCommands.get(i + 11);
                                    fields[11] = listOfCommands.get(i + 12);
                                    i+=12;
                                }
                            }
                            parseCommand(in, socket, address, scanner, login); //Отправляем команду
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                listOfCommands.clear();
                listOfPaths.clear();
                isExecuteScript = false; //Выставляем переменную проверки execute_script = false
                break;
            case "exit" :
                System.out.println("Завершение работы клиентского приложения");
                System.exit(0);
            case "remove_lower" :
                try {
                    String[] a = new String[1];
                    a[0] = "45";
                    if (isExecuteScript) send(new RemoveLowerCommand(insertProduct(a, login)), socket, address);
                    else send(new RemoveLowerCommand(insertProduct(a, login)), socket, address);
                    receive(socket);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Недостаточно аргументов");
                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат ключа");
                }
                break;
            case "replace_if_greater" :
                try {
                    send(new ReplaceIfGreaterCommand(Integer.parseInt(ar[0]), insertProduct(ar, login)), socket, address);
                    receive(socket);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Недостаточно аргументов");
                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат ключа");
                }
                break;
            case "remove_lower_key" :
                try {
                    send(new RemoveLowerKeyCommand(Integer.parseInt(ar[0])), socket, address);
                    receive(socket);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Недостаточно аргументов");
                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат ключа");
                }
                break;
            case "remove_all_by_owner" :
                send(new RemoveAllByOwnerCommand(insertOwner(scanner)), socket, address);
                receive(socket);
                break;
            case "print_ascending" :
                send(new PrintAscendingCommand(), socket, address);
                receive(socket);
                break;
            case "print_descending" :
                send(new PrintDescendingCommand(), socket, address);
                receive(socket);
                break;
            default:
                if (!commandKey.equals("")) System.out.println("Неверная команда");
        }
    }

    /**
     * Метод для получения экземпляра класса Product
     * @param ar аргумент команды
     * @return product
     */
    public Product insertProduct(String[] ar, String login) {
        Scanner scanner = new Scanner(System.in);
        String line = "";
        Product product = new Product(Integer.parseInt(ar[0]), null, null, null, 0,  null, null, null, null, login);

        //Ввод product если execute_script
        if (isExecuteScript) {
            if (isFloat(fields[1]) && isFloat(fields[2]) && isInteger(fields[3]) && isFloat(fields[5]) && isUnitOfMeasure(fields[6]) && isDateFormat(fields[8]) && isFloat(fields[9]) && isLong(fields[10])) {
                product.setName(fields[0]);
                product.setCoordinates(new Coordinates(Double.parseDouble(fields[1]), Float.parseFloat(fields[2])));
                product.setCreationDate();
                product.setPrice(Integer.parseInt(fields[3]));
                product.setPartNumber(fields[4]);
                product.setManufactureCost(Float.parseFloat(fields[5]));
                switch (fields[6]) {
                    case "METERS":
                        product.setUnitOfMeasure(UnitOfMeasure.METERS);
                        break;
                    case "CENTIMETERS":
                        product.setUnitOfMeasure(UnitOfMeasure.CENTIMETERS);
                        break;
                    case "GRAMS":
                        product.setUnitOfMeasure(UnitOfMeasure.GRAMS);
                        break;
                    default:
                        System.out.println("Ошибка ввода полей");
                        break;
                }
                int date = 0;
                int month = 0;
                int year = 0;
                try {
                    if (fields[8].length() == 10 && fields[8].matches("\\d{2}[.]\\d{2}[.]\\d{4}")) {
                        String[] str = fields[8].split("\\.");

                        if (Integer.parseInt(str[0]) > 0 && Integer.parseInt(str[0]) < 32) {
                            date = Integer.parseInt(str[0]);
                        }

                        if (Integer.parseInt(str[1]) > 0 && Integer.parseInt(str[1]) < 13) {
                            month = Integer.parseInt(str[1]) - 1;
                        }

                        if (Integer.parseInt(str[2]) > 0) {
                            year = Integer.parseInt(str[2]) - 1900;
                        }
                    } else System.out.println("Дата введена неверно");
                } catch (Exception e) {
                    System.out.println("Ошибка ввода");
                }
                product.setOwner(new Person(fields[7], new Date(year, month, date), Float.parseFloat(fields[9]), Long.parseLong(fields[10]), fields[11]));
            } else System.out.println("Поля неверны. Проверьте файл");
        }
        //Ввод product если нет execute_script
        else {
            while (true) {
                System.out.println("Введите значение поля name ");
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine().trim();
                }
                if (line.equals(null) || line.equals("")) {
                    System.out.println("Ошибка ввода");
                } else {
                    product.setName(line);
                    break;
                }
            }

            Coordinates coordinates = new Coordinates(0, null);
            System.out.println("Введем координаты");

            while (true) {
                try {
                    System.out.println("Введите значение поля для координаты X ");
                    if (scanner.hasNextLine()) {
                        line = scanner.nextLine().trim();
                    }
                    coordinates.setX(Double.parseDouble(line));
                    break;
                } catch (Exception e) {
                    System.out.println("Ошибка ввода");
                }
            }

            while (true) {
                try {
                    System.out.println("Введите значение поля для координаты Y ");
                    if (scanner.hasNextLine()) {
                        line = scanner.nextLine().trim();
                    }
                    if (Float.parseFloat(line) <= 834) {
                        coordinates.setY(Float.valueOf(line));
                        break;
                    } else throw new Exception("Wrong input");
                } catch (Exception e) {
                    System.out.println("Ошибка ввода");
                }
            }
            product.setCoordinates(coordinates);

            product.setCreationDate();

            while (true) {
                try {
                    System.out.println("Введите значение для поля price ");
                    if (scanner.hasNextLine()) {
                        line = scanner.nextLine().trim();
                    }
                    if (Integer.parseInt(line) > 0) {
                        product.setPrice(Integer.parseInt(line));
                        break;
                    } else throw new Exception("Wrong input");
                } catch (Exception e) {
                    System.out.println("Ошибка ввода");
                }
            }

            while (true) {
                try {
                    System.out.println("Введите значение для поля partNumber ");
                    if (scanner.hasNextLine()) {
                        line = scanner.nextLine().trim();
                    }
                    if (line.equals(null) || line.equals("")) {
                        throw new Exception("Wrong input");
                    } else {
                        product.setPartNumber(line);
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка ввода");
                }
            }

            while (true) {
                try {
                    System.out.println("Введите значение для поля manufactureCost ");
                    if (scanner.hasNextLine()) {
                        line = scanner.nextLine().trim();
                    }
                    if (line.equals(null) && line.equals("")) {
                        throw new Exception("Wrong input");
                    }
                    product.setManufactureCost(Float.valueOf(line));
                    break;
                } catch (Exception e) {
                    System.out.println("Ошибка ввода");
                }
            }

            while (true) {
                try {
                    System.out.println("Введите значение для поля unitOfMeasure ");
                    System.out.println("Возможные варианты : " + UnitOfMeasure.METERS + ", " + UnitOfMeasure.CENTIMETERS + ", " + UnitOfMeasure.GRAMS);
                    if (scanner.hasNextLine()) {
                        line = scanner.nextLine().trim();
                    }
                    if (line.equals("METERS") || line.equals("CENTIMETERS") || line.equals("GRAMS")) {
                        switch (line) {
                            case ("METERS"):
                                product.setUnitOfMeasure(UnitOfMeasure.METERS);
                                break;
                            case ("CENTIMETERS"):
                                product.setUnitOfMeasure(UnitOfMeasure.CENTIMETERS);
                                break;
                            case ("GRAMS"):
                                product.setUnitOfMeasure(UnitOfMeasure.GRAMS);
                                break;
                        }
                        break;
                    } else throw new Exception("Wrong input");
                } catch (Exception e) {
                    System.out.println("Ошибка ввода");
                }
            }

            Person owner = insertOwner(scanner);
            product.setOwner(owner);
        }
        return product;
    }

    /**
     * Метод, создающий экземпляр класса Person из командной строки
     * @return owner
     */
    public Person insertOwner(Scanner scanner) {
        String line = "";
        Person owner = new Person(null, null, null, 0, null);
        System.out.println("Введем владельца");

        while (true) {
            System.out.println("Введите значение для поля name ");
            if (scanner.hasNextLine()) {
                line = scanner.nextLine().trim();
            }
            if (line.equals(null) || line.equals("")) {
                System.out.println("Ошибка ввода");
            } else {
                owner.setName(line);
                break;
            }
        }

        int date = 0;
        int month = 0;
        int year = 0;

        while (true) {
            try {
                System.out.println("Введите дату в формате DD.MM.YYYY");
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine().trim();
                }
                if (line.length() == 10 && line.matches("\\d{2}[.]\\d{2}[.]\\d{4}")) {
                    String[] str = line.split("\\.");

                    if (Integer.parseInt(str[0]) > 0 && Integer.parseInt(str[0]) < 32) {
                        date = Integer.parseInt(str[0]);
                    }

                    if (Integer.parseInt(str[1]) > 0 && Integer.parseInt(str[1]) < 13) {
                        month = Integer.parseInt(str[1]) - 1;
                    }

                    if (Integer.parseInt(str[2]) > 0) {
                        year = Integer.parseInt(str[2]) - 1900;
                    }
                    break;
                } else System.out.println("Дата введена неверно");
            } catch (Exception e) {
                System.out.println("Ошибка ввода");
            }
        }

        Date birthday = new Date(year, month, date);
        owner.setBirthday(birthday);

        while (true) {
            try {
                System.out.println("Введите значение для поля height ");
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine().trim();
                }
                if (line.equals(null) || line.equals("") || line.equals("null")) {
                    owner.setHeight(null);
                    break;
                }
                if (Float.parseFloat(line) > 0) {
                    owner.setHeight(Float.valueOf(line));
                    break;
                } else throw new Exception("Wrong input");
            } catch (Exception e) {
                System.out.println("Ошибка ввода");
            }
        }

        while (true) {
            try {
                System.out.println("Введите значение для поля weight ");
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine().trim();
                }
                if (Long.parseLong(line) > 0) {
                    owner.setWeight(Long.parseLong(line));
                    break;
                } else throw new Exception("Wrong input");
            } catch (Exception e) {
                System.out.println("Ошибка ввода");
            }
        }

        while (true) {
            try {
                System.out.println("Введите значение для поля passportID ");
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine().trim();
                }
                if (line.length() <= 50 && line.length() >= 6) {
                    owner.setPassportID(line);
                    break;
                } else throw new Exception("Wrong input");
            } catch (Exception e) {
                System.out.println("Ошибка ввода");
            }

        } return owner;
    }

    /**
     * Метод для отправки пакета с командой Серверу
     * @param command - команда
     * @param socket - сокет
     * @param address - адрес для отправки
     */
    private void send(Command command, DatagramSocket socket, SocketAddress address) {
        byte[] sending;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(command);
            out.flush();
            sending = bos.toByteArray();
            DatagramPacket packet = new DatagramPacket(sending, sending.length, address);
            socket.send(packet);
        } catch (PortUnreachableException e) {
            System.out.println("Порт недоступен");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO Проверить корректность работы
    public boolean checkServerUser(ServerUser serverUser, DatagramSocket socket, SocketAddress address){
        byte[] sending;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(serverUser);
            out.flush();
            sending = bos.toByteArray();
            DatagramPacket packet = new DatagramPacket(sending, sending.length, address);
            socket.send(packet);
        } catch (PortUnreachableException e) {
            System.out.println("Порт недоступен");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Получили реакцию сервера
        byte[] message = new byte[16384]; //Массив байт, который мы получаем
        try {
            DatagramPacket packet = new DatagramPacket(message, message.length);
            socket.setSoTimeout(10000); //Задержка для возможности обработки запроса клиента
            socket.receive(packet);
            ByteArrayInputStream bis = new ByteArrayInputStream(message);
            ObjectInput in = new ObjectInputStream(bis);
            return (Boolean) in.readObject();
        } catch (SocketTimeoutException e) {
            System.out.println("Время ожидания превышено");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void registerServerUser(ServerUser serverUser, DatagramSocket socket, SocketAddress address){
        byte[] sending;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(serverUser);
            out.flush();
            sending = bos.toByteArray();
            DatagramPacket packet = new DatagramPacket(sending, sending.length, address);
            socket.send(packet);
        } catch (PortUnreachableException e) {
            System.out.println("Порт недоступен");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для получения пакета от Сервера
     * @param socket - сокет
     */
    public void receive(DatagramSocket socket) {
        byte[] message = new byte[16384]; //Массив байт, который мы получаем
        try {
            DatagramPacket packet = new DatagramPacket(message, message.length);
            socket.setSoTimeout(10000); //Задержка для возможности обработки запроса клиента
            socket.receive(packet);
            ByteArrayInputStream bis = new ByteArrayInputStream(message);
            ObjectInput in = new ObjectInputStream(bis);
            String received_message = (String) in.readObject();
            System.out.println(received_message);
        } catch (SocketTimeoutException e) {
            System.out.println("Время ожидания превышено");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, который проверяет рекурсию в файлах execute_script
     * @param script - изначальный список команд
     * @param listOfPaths - изначальный список путей у всех execute_script
     * @return есть ли рекурсия или нет
     */
    private boolean checkRecurssionInScript(ArrayList<String> script, ArrayList<String> listOfPaths) {
        for (int i = 0; i < script.size(); i++) {
            String[] input = script.get(i).trim().split(" ");
            if (input[0].equals("execute_script"))
                if (listOfPaths.contains(input[1])) {
                    isRec = true;
                }
                else {
                    listOfPaths.add(input[1]);
                    checkRecurssionInScript(readScript(input[1]), listOfPaths);
                }
        }
        return isRec;
    }

    /**
     * Метод, читающий команды из скрипт-файла
     * @param path путь к файлу
     * @return Изначальный список команд
     */
    private ArrayList<String> readScript(String path) {
        File file = new File(path);
        ArrayList<String> listOfCommands = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                listOfCommands.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        }
        return listOfCommands;
    }

    /**
     * Метод, который возвращает полный список команд для испонения, даже если есть вложенные execute_script
     * @param list изначальный список команд
     * @return Полный список команд
     * @throws FileNotFoundException
     */
    private ArrayList<String> readWholeScript(ArrayList<String> list) throws FileNotFoundException {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).trim().split(" ")[0].equals("execute_script")) {
                readWholeScript(readScript(list.get(i).trim().split(" ")[1]));
            }
            else listOfCommands.add(list.get(i));
        }
        return listOfCommands;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isLong(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isUnitOfMeasure(String s) {
        return (s.equals(UnitOfMeasure.METERS.toString()) || s.equals(UnitOfMeasure.CENTIMETERS.toString()) || s.equals(UnitOfMeasure.GRAMS.toString()));
    }

    public static boolean isDateFormat(String s) {
        return s.matches("\\d{2}[.]\\d{2}[.]\\d{4}");
    }

}
