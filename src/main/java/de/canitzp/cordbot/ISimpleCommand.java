package de.canitzp.cordbot;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;

import java.util.List;

/**
 * @author canitzp
 */
public interface ISimpleCommand {

    // null for everybody
    default List<IRole> getTriggerRoles(){
        return null;
    }

    String getTrigger();

    String getResponse(String sender);

    default String write(){
        StringBuilder s = new StringBuilder();
        if(getTriggerRoles() != null && !getTriggerRoles().isEmpty()){
            for(IRole role : this.getTriggerRoles()){
                s.append(role.getName()).append(";");
            }
            s.deleteCharAt(s.length() - 1);
        }
        s.append("#");
        s.append(getTrigger());
        s.append("#");
        s.append(getResponse("%sender%"));
        return s.toString();
    }

}
