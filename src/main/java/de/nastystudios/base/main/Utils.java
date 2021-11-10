/*
 * Class created by Ron Schluseneck
 * https://www.github.com/NastyOOF
 * 09.05.21, 00:01
 */

package de.nastystudios.base.main;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public class Utils {
    private static String prefix = "!";
    public static String getPrefix() {
        return prefix;
    }

    /*
     * check if user as a certain role
     */
    public static boolean isAdmin(Member member) {
        boolean state = false;
        if(member.getRoles().stream().anyMatch(role -> role.getId().equals("roleid"))) {
            state = true;
        }
        return state;
    }

    public static boolean hasRole(Member member, Role role) {
        boolean state = false;
        if(member.getRoles().stream().anyMatch(r -> r.getId().equals(role.getId()))) {
            state = true;
        }
        return state;
    }
}
