package commands;

import collection.Person;
import collection.Product;
import utill.CommandReceiver;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * Класс команды remove_all_by_owner
 */
public class RemoveAllByOwnerCommand implements Command, Serializable {
    private final String key = "remove_all_by_owner";
    private final String helpText = "удалить из коллекции все элементы, значение поля owner которого эквивалентно заданному";
    private final Person owner;
    private static final long serialVersionUID = 6529685098267757690L;

    public RemoveAllByOwnerCommand(Person owner) {
        this.owner = owner;
    }

    @Override
    public String execute(CommandReceiver commandReceiver) {
        return commandReceiver.removeAllByOwner(owner);
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
