package fr.xephi.authmevelocity.commands;

import ch.jalu.configme.SettingsManager;
import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;
import java.util.List;

public class VelocityReloadCommand implements Command {

    private SettingsManager settings;

    @Inject
    public VelocityReloadCommand(SettingsManager settings) {
        this.settings = settings;
    }

    @Override
    public void execute(CommandSource commandSource, @NonNull String[] strings) {
        settings.reload();
        commandSource.sendMessage(
            TextComponent.of("AuthMeBungee configuration reloaded!").color(TextColor.GREEN)
        );
    }

    @Override
    public List<String> suggest(CommandSource source, @NonNull String[] currentArgs) {
        return null;
    }

    @Override
    public boolean hasPermission(CommandSource source, @NonNull String[] args) {
        return source.hasPermission("authmebungee.reload");
    }

}
