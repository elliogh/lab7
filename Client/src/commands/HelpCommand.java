package commands;

import utill.CommandReceiver;

import java.io.Serializable;

/**
 * Класс команды help
 */
public class HelpCommand implements Command, Serializable {
    private final String key = "help";
    private final String helpText = "вывести справку по доступным командам";
    private static final long serialVersionUID = 6529685098267757690L;

    @Override
    public String execute(CommandReceiver commandReceiver) {
        return commandReceiver.help();
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
