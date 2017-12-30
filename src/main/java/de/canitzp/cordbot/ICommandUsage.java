package de.canitzp.cordbot;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.util.List;

/**
 * @author canitzp
 */
public interface ICommandUsage {

    // if null then everyone can use it
    default List<String> getAllowedUser(){
        return null;
    }

    void onAction(IUser sender, IChannel channel, IMessage message);

}
