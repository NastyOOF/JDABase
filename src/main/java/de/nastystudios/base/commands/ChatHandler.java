/*
 * Class created by Ron Schluseneck
 * https://www.github.com/NastyOOF
 * 09.05.21, 00:42
 */

package de.nastystudios.base.commands;

import de.nastystudios.base.main.Utils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ChatHandler extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().startsWith(Utils.getPrefix()) && event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId()) {
            IHandler.handleCommand(IHandler.parser.parse(event.getMessage().getContentRaw().toLowerCase(), event));
        }
    }
}
