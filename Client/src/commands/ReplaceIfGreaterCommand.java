package commands;

import collection.Product;
import utill.CommandReceiver;

import java.io.Serializable;

/**
 * Класс команды replace_if_greater
 */
public class ReplaceIfGreaterCommand implements Command, Serializable {
    private final String key = "replace_if_greater";
    private final String helpText = "заменить значение по ключу, если новое значение больше старого";
    private final int id;
    private final Product product;
    private static final long serialVersionUID = 6529685098267757690L;

    public ReplaceIfGreaterCommand(int id, Product product) {
        this.id = id;
        this.product = product;
    }

    @Override
    public String execute(CommandReceiver commandReceiver) {
        return commandReceiver.replaceIfGreater(id, product);
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
