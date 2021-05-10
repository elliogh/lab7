package commands;

import utill.CommandReceiver;

/**
 * Абстрактный класс команды
 */
public interface Command {

    String execute(CommandReceiver commandReceiver);

    String getKey();

    String getHelpText();
}
