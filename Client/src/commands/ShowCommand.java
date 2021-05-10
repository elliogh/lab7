package commands;

import utill.CommandReceiver;

import java.io.Serializable;

/**
 * Класс команды show
 */
public class ShowCommand implements Command, Serializable {
    private final String key = "show";
    private final String helpText = "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";

    @Override
    public String execute(CommandReceiver commandReceiver) {
        return commandReceiver.show();
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
