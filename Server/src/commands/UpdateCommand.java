package commands;

import collection.Product;
import utill.CommandReceiver;

import java.io.Serializable;

/**
 * Класс команды update
 */
public class UpdateCommand implements Command, Serializable {
    private final String key = "update";
    private final String helpText = "обновить значение элемента коллекции, id которого равен заданному";
    private final int id;
    private final Product product;
    private static final long serialVersionUID = 6529685098267757690L;
    private String login;

    public UpdateCommand(int id, Product product, String login) {
        this.id = id;
        this.product = product;
        this.login = login;
    }

    @Override
    public String execute(CommandReceiver commandReceiver) {
        return commandReceiver.update(id, product, login);
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
