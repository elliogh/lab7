package commands;

import utill.CommandReceiver;

import java.io.Serializable;

/**
 * Класс команды remove_key
 */
public class RemoveKeyCommand implements Command, Serializable {
    private final String key = "remove_key";
    private final String helpText = "удалить элемент из коллекции по его ключу";
    private final int id;
    private final String login;
    private static final long serialVersionUID = 6529685098267757690L;

    public RemoveKeyCommand(int id, String login) {
        this.id = id;
        this.login = login;
    }

    @Override
    public String execute(CommandReceiver commandReceiver) {
        return commandReceiver.removeKey(id);
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
