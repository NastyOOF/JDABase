/*
 * Class created by Ron Schluseneck
 * https://www.github.com/NastyOOF
 * 09.05.21, 00:03
 */

package de.nastystudios.base.commands.slash;

import de.nastystudios.base.main.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.awt.*;

public class SlashCommands extends ListenerAdapter {

    public void onReady(net.dv8tion.jda.api.events.ReadyEvent event) {

        /*
         * guild slash commands (changes instant)
         */
        Guild guild = event.getJDA().getGuildById("908130260904738856");
        CommandListUpdateAction commands = guild.updateCommands();

        /*
         * global slash commands (These commands take up to an hour to be activated after creation/update/delete)
         *
         * JDA jda = event.getJDA();
         * CommandUpdateAction commands = jda.updateCommands();
         */


        // slash command without arguments
        commands.addCommands(new CommandData("help", "Example Description"));

        // slash command with required arguments
        commands.addCommands(new CommandData("say", "Example Description").addOption(OptionType.STRING, "content", "Message"));

        // slash command with optional arguments
        commands.addCommands(new CommandData("info", "Example Description").addOption(OptionType.USER, "user", "User"));

        // slash command with button
        commands.addCommands(new CommandData("roles", "Button Example"));

        // slash command with menu
        commands.addCommands(new CommandData("menu", "Menu Example"));

        commands.queue();
    }

    public void onSlashCommand(SlashCommandEvent event) {
        // Only accept commands from guilds
        if (event.getGuild() == null)
            return;
        switch (event.getName()) {
            case "help":
                sendHelpExample(event);
                break;
            case "say":
                sayExample(event, event.getOption("content").getAsString());
                break;
            case "info":
                if(event.getOption("user") == null) {
                 infoExample(event);
                } else {
                    infoExample(event, event.getOption("user").getAsMember());
                }
                break;
            case "roles":
                buttonExample(event);
                break;
            case "menu":
                menuExample(event);
                break;
            default:
                event.reply("I can't handle that command right now!").setEphemeral(true).queue();

        }
    }

    public void sendHelpExample(SlashCommandEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Help");
        builder.setDescription("This is an nice custom help Command");

        event.replyEmbeds(builder.build()).setEphemeral(true).queue();
    }

    public void sayExample(SlashCommandEvent event, String content) {
        if(Utils.isAdmin(event.getMember())) { // this checks if a person has a certain role
            event.reply(content).queue();
        } else {
            event.reply("You have no Permission").queue();
        }
    }

    public void infoExample(SlashCommandEvent event) {
        event.reply("Info about you: \n Name: " + event.getMember().getUser().getName() + "#" + event.getMember().getUser().getDiscriminator() + " \n " +
                "Id: " + event.getMember().getId() + " \n " +
                "Avatar URL: " + event.getMember().getUser().getAvatarUrl() + " \n " +
                "Status: " + event.getMember().getOnlineStatus()).queue();
    }

    public void infoExample(SlashCommandEvent event, Member member) {
        event.reply("Info about user: \n Name: " + member.getUser().getName() + "#" + member.getUser().getDiscriminator() + " \n " +
                "Id: " + member.getId() + " \n " +
                "Avatar URL: " + member.getUser().getAvatarUrl() + " \n " +
                "Status: " + member.getOnlineStatus()).queue();
    }

    public void buttonExample(SlashCommandEvent event) {
        if(Utils.hasRole(event.getMember(), event.getGuild().getRoleById("908130913521651753"))) {
            String id = event.getGuild().getId();

            Emoji teamEmoji = Emoji.fromEmote(event.getGuild().getEmoteById("908131576636923925"));
            Emoji rpcEmoji = Emoji.fromEmote(event.getGuild().getEmoteById("908131527739736075"));

            Message msg = event.getTextChannel().sendMessage("**Wähle deine Rollen**").setActionRows(
                    ActionRow.of(net.dv8tion.jda.api.interactions.components.Button.secondary(id + ":testerRole", "Tester").withEmoji(teamEmoji), Button.secondary(id + ":logsRole", "Logs").withEmoji(rpcEmoji))).complete();

            event.reply("Rollenauswahl wurde gesendet.").setEphemeral(true).queue();
        } else {
            event.reply("Dazu hast du keine Rechte.").setEphemeral(true).queue();
        }
    }

    public void menuExample(SlashCommandEvent event) {
        SelectionMenu menu = SelectionMenu.create("menu:class")
                .setPlaceholder("Choose your class") // shows the placeholder indicating what this menu is for
                .setRequiredRange(1, 1) // only one can be selected
                .addOption("Example 1", "example-1")
                .addOption("Example 2", "example-2")
                .addOption("Example 3", "example-3")
                .build();
        event.reply("Please pick your class below")
                .setEphemeral(true)
                .addActionRow(menu)
                .queue();
    }

    public void onSelectionMenu(SelectionMenuEvent event) {
        if(event.getValues().contains("example-1")) {
            event.reply("You clicked EXAMPLE **1**").setEphemeral(true).queue();
        } else if(event.getValues().contains("example-2")) {
            event.reply("You clicked EXAMPLE **2**").setEphemeral(true).queue();
        } else if(event.getValues().contains("example-3")) {
            event.reply("You clicked EXAMPLE **3**").setEphemeral(true).queue();
        }
    }

    @Override
    public void onButtonClick(ButtonClickEvent event) {
        String[] id = event.getComponentId().split(":");
        String authorId = id[0];
        String type = id[1];
        if (!authorId.equals(event.getGuild().getId()))
            return;
        MessageChannel channel = event.getChannel();
        event.deferEdit().queue();
        switch (type) {
            case "testerRole":

                if(Utils.hasRole(event.getMember(), event.getGuild().getRoleById("837788476492611604"))) {
                    Guild guild = event.getGuild();
                    guild.removeRoleFromMember(event.getMember(), guild.getRoleById("837788476492611604")).queue();
                } else {
                    Guild guild = event.getGuild();
                    guild.addRoleToMember(event.getMember(), guild.getRoleById("837788476492611604")).queue();
                }
                break;
            case "logsRole":
                if(Utils.hasRole(event.getMember(), event.getGuild().getRoleById("847816202651041813"))) {
                    Guild guild = event.getGuild();
                    guild.removeRoleFromMember(event.getMember(), guild.getRoleById("847816202651041813")).queue();
                } else {
                    Guild guild = event.getGuild();
                    guild.addRoleToMember(event.getMember(), guild.getRoleById("847816202651041813")).queue();
                }
                break;
        }
    }
}
