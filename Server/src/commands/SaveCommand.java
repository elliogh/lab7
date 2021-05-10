package commands;

import collection.Product;
import utill.CommandReceiver;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * Класс команды save
 */
public class SaveCommand implements Command, Serializable {
    private final String key = "save";
    private final String helpText = "сохранить коллекцию в файл";

    @Override
    public String execute(CommandReceiver commandReceiver) {
        return commandReceiver.save();
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
