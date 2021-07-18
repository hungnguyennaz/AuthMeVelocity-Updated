package fr.xephi.authmevelocity.commands;

import ch.jalu.configme.SettingsManager;
import com.velocitypowered.api.command.RawCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import javax.inject.Inject;

public class VelocityReloadCommand implements RawCommand {

    private SettingsManager settings;

    @Inject
    public VelocityReloadCommand(SettingsManager settings) {
        this.settings = settings;
    }

    @Override
    public void execute(Invocation invocation) {
        settings.reload();
        invocation.source().sendMessage(
            Component.text("AuthMeVelocity configuration reloaded!").color(NamedTextColor.GREEN)
        );
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("authmevelocity.reload");
    }

}
