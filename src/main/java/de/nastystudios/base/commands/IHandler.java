/*
 * Class created by Ron Schluseneck
 * https://www.github.com/NastyOOF
 * 09.05.21, 00:39
 */

package de.nastystudios.base.commands;

import java.util.HashMap;

public class IHandler {

    public static final IParser parser = new IParser();
    public static HashMap<String, ICommand> commands = new HashMap<>();

    public static void handleCommand(IParser.commandContainer cmd) {
        if (commands.containsKey(cmd.invoke)) {
                commands.get(cmd.invoke).run(cmd.args, cmd.event);
            }
        }
    }
