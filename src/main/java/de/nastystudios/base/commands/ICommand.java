/*
 * Class created by Ron Schluseneck
 * https://www.github.com/NastyOOF
 * 09.05.21, 00:32
 */

package de.nastystudios.base.commands;


import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface ICommand {
    void run(String[] args, MessageReceivedEvent event);
}
