package commands;

import collection.Product;
import utill.CommandReceiver;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * Класс команды remove_lower
 */
public class RemoveLowerCommand implements Command, Serializable {
    private final String key = "remove_lower";
    private final String helpText = "удалить из коллекции все элементы, меньшие, чем заданный";
    private final Product product;
    private static final long serialVersionUID = 6529685098267757690L;

    public RemoveLowerCommand(Product product) {
        this.product = product;
    }

    @Override
    public String execute(CommandReceiver commandReceiver) {
        return commandReceiver.removeLower(product);
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
