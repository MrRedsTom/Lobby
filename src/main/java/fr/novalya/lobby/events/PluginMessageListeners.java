package fr.novalya.lobby.events;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class PluginMessageListeners implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {

        if(!channel.equalsIgnoreCase("BungeeCord")) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(message);

    }
}
