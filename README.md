# AuthMeVelocity

Like AuthMeBungee this plugin acts like a bridge between your bukkit servers and your Velocity instance. 
To explain simple how it works, bukkit-side plugins send a message to velocity-side on user authentication. If velocity-side doesn't receive this message, the player won't be able to talk in chat and to perform commands, including Velocity commands.


## Requirements

- Java 1.8+
- [AuthMeReloaded fork](https://github.com/hungnguyennaz/AuthMeReloaded-Hooked/tree/dev)
- Velocity 1.1.0+

## Building

1. Make sure that you have maven
2. Clone the repository
3. Run `mvn clean package` in the directory with `pom.xml`
4. Output file will be located in target directory

## Installation

1. Download from Releases
2. Place AuthMeVelocity.jar into your Velocity's plugin folder
4. Restart everything
5. Configure the plugin **(don't forget to config authServers)**
6. Enable the **Hooks.bungeecord** option in your **AuthMeReloaded config file**
7. Enjoy!


**Please don't ask for support with this plugin in AuthMe discord!**
