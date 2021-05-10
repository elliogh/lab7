package commands;

import collection.Product;
import utill.CommandReceiver;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * Класс команды exit
 */
public class ExitCommand implements Command, Serializable {
    private final String key = "exit";
    private final String helpText = "завершить программу (без сохранения в файл)";

    @Override
    public String execute(CommandReceiver commandReceiver) {
        return commandReceiver.exit();
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
