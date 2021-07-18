package fr.xephi.authmevelocity;

import ch.jalu.configme.SettingsManager;
import ch.jalu.injector.Injector;
import ch.jalu.injector.InjectorBuilder;
import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.LegacyChannelIdentifier;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import fr.xephi.authmevelocity.annotations.DataFolder;
import fr.xephi.authmevelocity.commands.VelocityReloadCommand;
import fr.xephi.authmevelocity.config.VelocityConfigProperties;
import fr.xephi.authmevelocity.config.VelocitySettingsProvider;
import fr.xephi.authmevelocity.listeners.VelocityMessageListener;
import fr.xephi.authmevelocity.listeners.VelocityPlayerListener;
import fr.xephi.authmevelocity.services.AuthPlayerManager;

import java.io.File;
import java.util.logging.Logger;

@Plugin(id = "authmevelocity", name = "AuthMeVelocity", version = "1.0.0-SNAPSHOT",
    description = "Velocity addon for AuthMe!", authors = "AuthMeTeam")
public class AuthMeVelocity {

    public static final LegacyChannelIdentifier LEGACY_AUTHME = new LegacyChannelIdentifier("AuthMe");
    public static final MinecraftChannelIdentifier AUTHME_CHANNEL = MinecraftChannelIdentifier.create("authme", "main");

    // Instances
    private static AuthMeVelocity instance;

    private final ProxyServer proxy;
    private final Logger logger;

    private Injector injector;
    private SettingsManager settings;
    private AuthPlayerManager authPlayerManager;

    @Inject
    public AuthMeVelocity(ProxyServer proxy, Logger logger) {
        this.proxy = proxy;
        this.logger = logger;

        instance = this;
    }

    public static AuthMeVelocity getInstance() {
        return instance;
    }

    @Subscribe
    public void onInit(ProxyInitializeEvent event) {
        // Prepare the injector and register stuff
        setupInjector();

        // Get singletons from the injector
        settings = injector.getSingleton(SettingsManager.class);
        authPlayerManager = injector.getSingleton(AuthPlayerManager.class);

        // Print some config information
        logger.info("Current auth servers:");
        for (String authServer : settings.getProperty(VelocityConfigProperties.AUTH_SERVERS)) {
            logger.info("> " + authServer.toLowerCase());
        }

        // Add online players (plugin hotswap, just in case)
        for (Player player : proxy.getAllPlayers()) {
            authPlayerManager.addAuthPlayer(player);
        }

        // Register commands
        CommandManager commandManager = proxy.getCommandManager();
        CommandMeta meta = commandManager.metaBuilder("abreloadproxy")
            .aliases("authmevelocityreload", "amvreload", "amvr")
            .build();
        commandManager.register(meta, new VelocityReloadCommand(settings));

        // Registering event listeners
        proxy.getChannelRegistrar().register(AUTHME_CHANNEL, LEGACY_AUTHME);
        proxy.getEventManager().register(this, injector.getSingleton(VelocityMessageListener.class));
        proxy.getEventManager().register(this, injector.getSingleton(VelocityPlayerListener.class));
    }

    public ProxyServer getProxy() {
        return proxy;
    }

    private void setupInjector() {
        // Setup injector
        injector = new InjectorBuilder().addDefaultHandlers("").create();
        injector.register(Logger.class, logger);
        injector.register(AuthMeVelocity.class, this);
        injector.register(ProxyServer.class, proxy);

        File file = new File("./plugins/AuthMeVelocity/");
        injector.provide(DataFolder.class, file);
        injector.registerProvider(SettingsManager.class, VelocitySettingsProvider.class);
    }

}
