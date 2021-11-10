/*
 * Class created by Ron Schluseneck
 * https://www.github.com/NastyOOF
 * 08.05.21, 23:52
 */

package de.nastystudios.base.events;

import de.nastystudios.base.commands.IHandler;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReadyEvent extends ListenerAdapter {

    public void onReady(net.dv8tion.jda.api.events.ReadyEvent event) {

        /*
        * send an message into the console on start
        */
        System.out.println("Bot started! We are on " + event.getGuildTotalCount() + " Servers");
        System.out.println("Loaded "+ IHandler.commands.size() + " Commands");
    }
}
