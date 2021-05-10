package commands;

import collection.Product;
import utill.CommandReceiver;

import java.util.TreeMap;

/**
 * Абстрактный класс команды
 */
public interface Command {

    String execute(CommandReceiver commandReceiver);

    String getKey();

    String getHelpText();
}
