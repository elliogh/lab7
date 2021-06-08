package commands;

import utill.CommandReceiver;

import java.io.Serializable;

/**
 * Класс команды save
 */
public class SaveCommand implements Command, Serializable {
    private final String key = "save";
    private final String helpText = "сохранить коллекцию в файл";
    private static final long serialVersionUID = 6529685098267757690L;

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
