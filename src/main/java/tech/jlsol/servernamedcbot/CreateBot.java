package tech.jlsol.servernamedcbot;

import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import tech.jlsol.servernamedcbot.listeners.ReactionsListeners;
import tech.jlsol.servernamedcbot.listeners.VoiceListeners;
import tech.jlsol.servernamedcbot.util.Config;

public class CreateBot {
  public CreateBot() {
    try {

      JDABuilder
          .create(getToken(), GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                  GatewayIntent.DIRECT_MESSAGE_TYPING,
                  GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_BANS,
                  GatewayIntent.GUILD_EMOJIS, GatewayIntent.GUILD_INVITES,
                  GatewayIntent.GUILD_MEMBERS,
                  GatewayIntent.GUILD_MESSAGE_REACTIONS,
                  GatewayIntent.GUILD_MESSAGE_TYPING,
                  GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_PRESENCES,
                  GatewayIntent.GUILD_VOICE_STATES)
          /*
          set Flag´s
          */
          .setAutoReconnect(true)
          .setIdle(true)
          .setStatus(OnlineStatus.DO_NOT_DISTURB)
          // TODO: make this working
          //.setStatus(HandleOnlineStatus.handleStatus())
          //.setActivity(CheckStatus.Status())
          /*
          import listener
          */
          .addEventListeners(new ReactionsListeners())
          .addEventListeners(new VoiceListeners())
          //.addEventListeners(new MessageListeners())
          /*
          guild listener
          */
          //.addEventListeners(new _VoiceChannel())
          /*
          commands
          */
          //.addEventListeners(new Blacklist_Command())
          /*
          build the bot
          */
          .build();
    } catch (LoginException e) {
      e.printStackTrace();
    }
  }
  private String getToken() {
    if (!Config.readConfig("data", "credentials", "discordToken").isEmpty() &&
        Config.readConfig("data", "credentials", "discordToken").length() ==
            59) {
      return Config.readConfig("data", "credentials", "discordToken");
    } else {
      System.err.println("Invalid Bot Token");
      return null;
    }
  }
}
