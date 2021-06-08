package commands;

import utill.CommandReceiver;

import java.io.Serializable;

/**
 * Класс команды exit
 */
public class ExitCommand implements Command, Serializable {
    private final String key = "exit";
    private final String helpText = "завершить программу (без сохранения в файл)";
    private static final long serialVersionUID = 6529685098267757690L;

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
