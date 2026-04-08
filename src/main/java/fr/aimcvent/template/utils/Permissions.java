package fr.aimcvent.template.utils;

import fr.aimcvent.player.api.rank.Permission;

public enum Permissions implements Permission {
    COMMAND_MANAGE_TEAM("command.manage.team"),
    INVENTORY_TEAM_JOIN_SPECTATOR("inventory.team.join.spectator"),
    UNKNOWN("unknown");

    private final String permission;

    Permissions(String permission) {
        this.permission = "template-game." + permission;
    }

    @Override
    public String of() {
        return this.permission;
    }

    public static Permissions of(String permission) {
        for (Permissions permissions : Permissions.values()) {
            if (permissions.permission.equalsIgnoreCase(permission)) {
                return permissions;
            }
        }
        return Permissions.UNKNOWN;
    }
}
