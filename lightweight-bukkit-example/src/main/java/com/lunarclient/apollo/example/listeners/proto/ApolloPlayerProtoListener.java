package com.lunarclient.apollo.example.listeners.proto;

import com.lunarclient.apollo.example.ApolloExamplePlugin;
import com.lunarclient.apollo.example.utilities.ProtobufPacketUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.plugin.messaging.Messenger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ApolloPlayerProtoListener implements Listener {

    private static final List<String> APOLLO_MODULES = Arrays.asList("limb", "beam", "border", "chat", "colored_fire", "combat", "cooldown",
        "entity", "glow", "hologram", "mod_setting", "nametag", "nick_hider", "notification", "packet_enrichment", "rich_presence",
        "server_rule", "staff_mod", "stopwatch", "team", "title", "tnt_countdown", "transfer", "vignette", "waypoint"
    );

    private static final String APOLLO_CHANNEL = "lunar:apollo"; // Used for detecting whether the player supports Apollo

    private final Set<UUID> playersRunningApollo = new HashSet<>();

    public ApolloPlayerProtoListener(ApolloExamplePlugin plugin) {
        Messenger messenger = Bukkit.getServer().getMessenger();
        messenger.registerOutgoingPluginChannel(plugin, APOLLO_CHANNEL);
        messenger.registerIncomingPluginChannel(plugin, APOLLO_CHANNEL, new ApolloRoundtripProtoListener());
        messenger.registerIncomingPluginChannel(plugin, APOLLO_CHANNEL, new ApolloPacketReceiveProtoListener());

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onRegisterChannel(PlayerRegisterChannelEvent event) {
        if (!event.getChannel().equalsIgnoreCase(APOLLO_CHANNEL)) {
            return;
        }

        this.onApolloRegister(event.getPlayer());
    }

    private boolean isPlayerRunningApollo(Player player) {
        return this.playersRunningApollo.contains(player.getUniqueId());
    }

    private void onApolloRegister(Player player) {
        ProtobufPacketUtil.enableModules(player, APOLLO_MODULES);

        this.playersRunningApollo.add(player.getUniqueId());
        player.sendMessage("You are using LunarClient!");
    }

}
