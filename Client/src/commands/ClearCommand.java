package commands;

import utill.CommandReceiver;

import java.io.Serializable;

/**
 * Класс команды clear
 */
public class ClearCommand implements Command, Serializable {
    private final String key = "clear";
    private final String helpText = "очистить коллекцию";
    private static final long serialVersionUID = 6529685098267757690L;

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
