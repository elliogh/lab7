package commands;

import collection.Product;
import utill.CommandReceiver;

import java.io.Serializable;

/**
 * Класс команды insert
 */
public class InsertCommand implements Command, Serializable {
    private final String key = "insert";
    private final String helpText = "добавить новый элемент с заданным ключом";
    private final int id;
    private final Product product;
    private static final long serialVersionUID = 6529685098267757690L;
    private String login;

    public InsertCommand(int id, Product product, String login) {
        this.id = id;
        this.product = product;
        this.login = login;
    }

    @Override
    public String execute(CommandReceiver commandReceiver) {
        return commandReceiver.insert(id, product);
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
