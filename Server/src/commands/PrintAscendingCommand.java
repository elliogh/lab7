package commands;

import utill.CommandReceiver;

import java.io.Serializable;

/**
 * Класс команды print_ascending
 */
public class PrintAscendingCommand implements Command, Serializable {
    private final String key = "print_ascending";
    private final String helpText = "вывести элементы коллекции в порядке возрастания";
    private static final long serialVersionUID = 6529685098267757690L;

    @Override
    public String execute(CommandReceiver commandReceiver) {
       return commandReceiver.printAscending();
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
