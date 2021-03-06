package commands;

import utill.CommandReceiver;

import java.io.Serializable;

/**
 * Класс команды print_descending
 */
public class PrintDescendingCommand implements Command, Serializable {
    private final String key = "print_descending";
    private final String helpText = "вывести элементы коллекции в порядке убывания";
    private static final long serialVersionUID = 6529685098267757690L;

    @Override
    public String execute(CommandReceiver commandReceiver) {
        return commandReceiver.printDescending();
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
