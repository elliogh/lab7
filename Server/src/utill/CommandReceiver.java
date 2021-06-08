package utill;

import collection.Person;
import collection.Product;
import commands.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CommandReceiver {
    private TreeMap<Integer, Product> collection;
    private final LocalDate initDate;
    private final HashSet<Command> commandSet = new HashSet<>();
    private DatabaseManager databaseManager;

    public CommandReceiver() {
        collection = new TreeMap<>();
        initDate = LocalDate.now();
        databaseManager = new DatabaseManager();
        try {
            databaseManager.connect(collection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        addCommands(
                new HelpCommand(),
                new InfoCommand(),
                new ShowCommand(),
                new InsertCommand(0, null),
                new RemoveKeyCommand(0,null),
                new ClearCommand(),
                new ExecuteScriptCommand(null),
                new ExitCommand(),
                new RemoveLowerCommand(null),
                new ReplaceIfGreaterCommand(0, null),
                new RemoveLowerKeyCommand(0),
                new RemoveAllByOwnerCommand(null),
                new PrintAscendingCommand(),
                new PrintDescendingCommand()
        );

    }

    public TreeMap<Integer, Product> getCollection() {
        return collection;
    }

    public String help() {
        return commandSet.stream()
                .map(command -> Clr.ANSI_YELLOW + command.getKey() + Clr.ANSI_RESET + " : " + command.getHelpText())
                .collect(Collectors.joining("\n"));
    }

    public String info() {
        for (Map.Entry<Integer, Product> e : collection.entrySet()) {
            if (e.getValue() == null) {
                System.out.println(Clr.ANSI_RED + "Ошибка" + Clr.ANSI_RESET);
                System.exit(1);
            }
        }
        return  Clr.ANSI_BLUE + "Тип коллекции: " + Clr.ANSI_RESET + getCollectionType() +
                Clr.ANSI_BLUE + "\nДата инициализации: " + Clr.ANSI_RESET + getInitDate() +
                Clr.ANSI_BLUE + "\nКоличество элементов: " + Clr.ANSI_RESET + getNumberOfElements();
    }

    public String show() {
        if (!collection.isEmpty())
            return collection.values().stream()
                    .map(Product::toString)
                    .collect(Collectors.joining("\n"));
        else return "Коллекция пуста";
    }

    public String insert(int id, Product product) {
        collection.put(id, product);
        return Clr.ANSI_GREEN + "Элемент добавлен в коллекцию" + Clr.ANSI_RESET;
    }

    public String update(int id, Product product, String login) {
        if (collection.containsKey(id)) {
            if (collection.get(id).getCreator().equals(login)) {
                collection.replace(id, product);
            } else return "Вы не можете изменять данный объект";
            return Clr.ANSI_GREEN + "Элемент обновлен" + Clr.ANSI_RESET;
        } else return Clr.ANSI_RED + "Элемента с таким ключом не существует" + Clr.ANSI_RESET;
    }

    public synchronized String removeKey(int id, String login) {
        if (collection.containsKey(id)) {
            if (collection.get(id).getCreator().equals(login)) {
                collection.remove(id);
            } else return "Вы не можете удалить данный объект";
            return Clr.ANSI_GREEN + "Команда успешно выполнена" + Clr.ANSI_RESET;
        }
        else return Clr.ANSI_RED + "Элемента с таким ключом не существует" + Clr.ANSI_RESET;
    }

    public synchronized String clear() {
        collection.clear();
        return Clr.ANSI_GREEN + "Коллекция очищена" + Clr.ANSI_RESET;
    }

    public synchronized String executeScript(ArrayList<String> list) {
        return null;
    }

    public synchronized String exit() {
        return null;
    }

    public synchronized String removeLower(Product product) {
        HashSet<Integer> keys = new HashSet<>();
        for (Map.Entry<Integer, Product> e : collection.entrySet()) {
            if (e.getValue().hashCode() - product.hashCode() < 0) keys.add(e.getKey());
        }
        for (Integer s : keys) {
            collection.remove(s);
        }
        return Clr.ANSI_GREEN + "Команда успешно выполнена" + Clr.ANSI_RESET;
    }

    public synchronized String replaceIfGreater(int id, Product product) {
        if (collection.containsKey(id)) {
            if (collection.get(id).hashCode() - product.hashCode() < 0) {
                collection.replace(id, product);
            }
            return Clr.ANSI_GREEN + "Команда успешно выполнена" + Clr.ANSI_RESET;
        }
        else return Clr.ANSI_RED + "Элемента с таким ключом не существует" + Clr.ANSI_RESET;
    }

    public synchronized String removeLowerKey(int id) {
        HashSet<Integer> keys = new HashSet<>();
        for (Map.Entry<Integer, Product> e : collection.entrySet()) {
            if (e.getKey() < id) keys.add(e.getKey());
        }

        for (Integer s : keys) {
            collection.remove(s);
        }
        return Clr.ANSI_GREEN + "Команда успешно выполнена" + Clr.ANSI_RESET;
    }

    public synchronized String removeAllByOwner(Person owner) {
        HashSet<Integer> keys = new HashSet<>();
        for (Map.Entry<Integer, Product> e : collection.entrySet()) {
            if (owner.equals(e.getValue().getOwner())) {
                keys.add(e.getKey());
            }
        }

        for (Integer s : keys) {
            collection.remove(s);
        }
        return Clr.ANSI_GREEN + "Команда успешно выполнена" + Clr.ANSI_RESET;
    }

    public synchronized String printAscending() {
        if (!collection.isEmpty()) return collection.values().stream()
                .sorted(Comparator.comparing(Product::getPrice))
                .map(Product::toString)
                .collect(Collectors.joining("\n"));
        else return "Коллекция пуста";
    }

    public synchronized String printDescending() {
        if (!collection.isEmpty()) return collection.values().stream()
                .sorted(Comparator
                        .comparing(Product::getPrice)
                        .reversed())
                .map(Product::toString)
                .collect(Collectors.joining("\n"));
        else return "Коллекция пуста";
    }

    public String save() {
        databaseManager.saveCollection(collection);
        return Clr.ANSI_GREEN + "Коллекция сохранена" + Clr.ANSI_RESET;
    }

    /**
     * Метод, возвращающий тип коллекции
     * @return тип колекции
     */
    public String getCollectionType() {
        return collection.getClass().getSimpleName();
    }

    /**
     * Метод, возвращающий дату инициализации коллекции
     * @return initDate
     */
    public LocalDate getInitDate() {
        return initDate;
    }

    /**
     * Метод, возвращающий количество элементов коллеции
     * @return размер коллекции
     */
    public int getNumberOfElements() {
        return collection.size();
    }

    private void addCommands(Command ... commands) {
        for (Command command : commands) {
            commandSet.add(command);
        }
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
