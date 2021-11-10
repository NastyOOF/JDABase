/*
 * Class created by Ron Schluseneck
 * https://www.github.com/NastyOOF
 * 09.05.21, 00:44
 */

package de.nastystudios.base.commands.impl;

import de.nastystudios.base.commands.ICommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TestCMD implements ICommand {
    @Override
    public void run(String[] args, MessageReceivedEvent event) {
        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessage("This is an nice simple command").queue();
    }
}
