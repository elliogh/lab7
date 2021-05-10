package commands;

import collection.Product;
import utill.CommandReceiver;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * Класс команды remove_key
 */
public class RemoveKeyCommand implements Command, Serializable {
    private final String key = "remove_key";
    private final String helpText = "удалить элемент из коллекции по его ключу";
    private final int id;

    public RemoveKeyCommand(int id) {
        this.id = id;
    }

    @Override
    public String execute(CommandReceiver commandReceiver) {
        return commandReceiver.removeKey(id);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getHelpText() {
        return helpText;
    }
}
