package de.canitzp.cordbot;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiLoader;
import com.vdurmont.emoji.EmojiManager;
import org.apache.commons.lang3.StringUtils;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.obj.EmojiImpl;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author canitzp
 */
public class CordBot {

    private static List<ICommandUsage> commands = new ArrayList<>();
    private static List<ISimpleCommand> hosesCommands = new ArrayList<>();

    public static void main(String[] args){

        commands.add(new ICommandUsage() {
            @Override
            public void onAction(IUser sender, IChannel channel, IMessage message) {
                if(message.getContent().equals("!save")){
                    for(ISimpleCommand command : hosesCommands){
                        System.out.println(command.write());
                        channel.sendMessage(command.write());
                    }
                }
            }
        });
        commands.add(new ICommandUsage() {
            @Override
            public void onAction(IUser sender, IChannel channel, IMessage message) {
                for(ISimpleCommand command : hosesCommands){
                    if(command.getTrigger().equals(message.getContent())){
                        if(command.getTriggerRoles() == null || command.getTriggerRoles().contains(sender.getRolesForGuild(channel.getGuild()))){
                            channel.sendMessage(command.getResponse("%sender%")
                                    .replace("%sender%", sender.getDisplayName(channel.getGuild()))
                                    .replace("%n%", "\n"));
                        }
                    }
                }
            }
        });

        hosesCommands.add(new ISimpleCommand() {
            @Override
            public String getTrigger() {
                return "!stream";
            }

            @Override
            public String getResponse(String sender) {
                return "Er streamed gerade nicht " + sender;
            }
        });

        hosesCommands.add(new ISimpleCommand() {
            @Override
            public String getTrigger() {
                return "!keks";
            }

            @Override
            public String getResponse(String sender) {
                return ":cookie:";
            }
        });

        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(args[0]);
        IDiscordClient client = clientBuilder.login();
        client.getDispatcher().registerListener(new IListener<MessageReceivedEvent>() {
            @Override
            public void handle(MessageReceivedEvent event) {
                IUser sender = event.getAuthor();
                IChannel channel = event.getChannel();
                IMessage message = event.getMessage();

                for(ICommandUsage command : commands){
                    if(command.getAllowedUser() == null || command.getAllowedUser().contains(sender.getName())){
                        command.onAction(sender, channel, message);
                    }
                }
            }
        });

        //client.logout();
    }

    private void save(){
        File file = new File(".", "cordbot.dat");

    }

}
