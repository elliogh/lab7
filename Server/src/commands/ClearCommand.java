package commands;

import collection.Product;
import utill.CommandReceiver;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * Класс команды clear
 */
public class ClearCommand implements Command, Serializable {
    private final String key = "clear";
    private final String helpText = "очистить коллекцию";

    @Override
    public String execute(CommandReceiver commandReceiver) {
        return commandReceiver.clear();
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
