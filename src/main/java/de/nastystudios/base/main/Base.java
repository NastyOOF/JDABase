/*
 * Class created by Ron Schluseneck
 * https://www.github.com/NastyOOF
 * 08.05.21, 23:49
 */

package de.nastystudios.base.main;

import de.nastystudios.base.commands.ChatHandler;
import de.nastystudios.base.commands.IHandler;
import de.nastystudios.base.commands.impl.TestCMD;
import de.nastystudios.base.commands.slash.SlashCommands;
import de.nastystudios.base.events.ReadyEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

public class Base {

    public static void main(String[] args) throws LoginException {
        List intents = new ArrayList();
        intents.add(GatewayIntent.DIRECT_MESSAGE_REACTIONS);
        intents.add(GatewayIntent.DIRECT_MESSAGE_TYPING);
        intents.add(GatewayIntent.DIRECT_MESSAGES);
        intents.add(GatewayIntent.GUILD_BANS);
        intents.add(GatewayIntent.GUILD_EMOJIS);
        intents.add(GatewayIntent.GUILD_INVITES);
        intents.add(GatewayIntent.GUILD_INVITES);
        intents.add(GatewayIntent.GUILD_MEMBERS);
        intents.add(GatewayIntent.GUILD_MESSAGE_REACTIONS);
        intents.add(GatewayIntent.GUILD_MESSAGE_TYPING);
        intents.add(GatewayIntent.GUILD_MESSAGES);
        intents.add(GatewayIntent.GUILD_PRESENCES);
        intents.add(GatewayIntent.GUILD_VOICE_STATES);

        JDA jda = JDABuilder.create("token", intents)
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.watching("JDA BASE by NastyStudios"))
                .setAutoReconnect(true)
                .addEventListeners(new ChatHandler())
                .addEventListeners(new ReadyEvent())
                .addEventListeners(new SlashCommands())
                .build();

        addCommands();
    }

    private static void addCommands() {
        IHandler.commands.put("test", new TestCMD());
    }
}
