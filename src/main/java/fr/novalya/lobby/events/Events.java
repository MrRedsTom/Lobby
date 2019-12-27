package fr.novalya.lobby.events;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.novalya.lobby.ItemBuilder;
import fr.novalya.lobby.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;

public class Events implements Listener {

    final String[] subs = {
            "Connect", "ConnectOther", "IP", "PlayerCount", "GetServers", "Message", "Forward", "ForwardToPlayer", "UUID", "UUIDOther", "ServerIP", "KickPlayer"
    };

    String disconnectReason;

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

            Lobby.getInstance().giveMenuInv(event.getPlayer());

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent event){

        event.setQuitMessage(disconnectReason.replace("%player%", event.getPlayer().getDisplayName()));
        disconnectReason = null;

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemInteract(PlayerInteractEvent event){
        try {
            event.setCancelled(true);

            if (event.getPlayer().hasPermission("lobby.bypass_all") && !event.getItem().getItemMeta().getDisplayName().equals("§cChoix du Serveur"))
                event.setCancelled(false);


            if (event.getItem() == null) return;

            if (event.getItem().getType() == Material.COMPASS && event.getItem().getItemMeta().getDisplayName().equals("§cChoix du Serveur")) {

                Lobby.getInstance().openGameMenu(event.getPlayer());


            }
        }catch (Exception e){

        }

    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        if(event.getPlayer().hasPermission("lobby.bypass_all")) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryInteract(InventoryClickEvent event){

        event.setCancelled(true);

        if(event.getWhoClicked().hasPermission("lobby.bypass_all")) event.setCancelled(false);

        if(event.getCurrentItem() == null) return;
        if(!(event.getWhoClicked() instanceof Player)) return;


        if(event.getCurrentItem().getType() == Material.COMPASS && event.getCurrentItem().getItemMeta().getDisplayName().equals("§cChoix du Serveur")){
            if(!Boolean.parseBoolean(event.getCurrentItem().getItemMeta().getLore().get(2).replace("§0", ""))) {
                event.getWhoClicked().sendMessage("§aLobby §f- §cLe serveur " + event.getCurrentItem().getItemMeta().getDisplayName() + "§c est fermé, tu ne peux donc pas t'y connecter !");
                event.getWhoClicked().closeInventory();
                return;
            }
            event.setCancelled(true);
            Lobby.getInstance().openGameMenu((Player) event.getWhoClicked());
            event.getWhoClicked().closeInventory();
        }
        if(event.getCurrentItem().getType() == Material.GREEN_STAINED_GLASS_PANE && event.getCurrentItem().getItemMeta().getDisplayName().equals("§a")){
            if(!Boolean.parseBoolean(event.getCurrentItem().getItemMeta().getLore().get(2).replace("§0", ""))) {
                event.getWhoClicked().sendMessage("§aLobby §f- §cLe serveur " + event.getCurrentItem().getItemMeta().getDisplayName() + "§c est fermé, tu ne peux donc pas t'y connecter !");
                event.getWhoClicked().closeInventory();
                return;
            }
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();
        }
        if(event.getCurrentItem().getType() == Material.GRASS_BLOCK && event.getCurrentItem().getItemMeta().getDisplayName().equals("§aSurvie")){
            if(!Boolean.parseBoolean(event.getCurrentItem().getItemMeta().getLore().get(2).replace("§0", ""))) {
                event.getWhoClicked().sendMessage("§aLobby §f- §cLe serveur " + event.getCurrentItem().getItemMeta().getDisplayName() + "§c est fermé, tu ne peux donc pas t'y connecter !");
                event.getWhoClicked().closeInventory();
                return;
            }
            event.setCancelled(true);
            sendPluginMessage(subs[0], (Player) event.getWhoClicked(), new String[]{"Survie"});
            disconnectReason = "§aLobby §f- §aLe joueur §6%player%§a s'est connecté sur le §cSurvie";
            event.getWhoClicked().closeInventory();
        }
        if(event.getCurrentItem().getType() == Material.JUNGLE_LEAVES && event.getCurrentItem().getItemMeta().getDisplayName().equals("§cSkyBlock")){
            if(!Boolean.parseBoolean(event.getCurrentItem().getItemMeta().getLore().get(2).replace("§0", ""))) {
                event.getWhoClicked().sendMessage("§aLobby §f- §cLe serveur " + event.getCurrentItem().getItemMeta().getDisplayName() + "§c est fermé, tu ne peux donc pas t'y connecter !");
                event.getWhoClicked().closeInventory();
                return;
            }
            event.setCancelled(true);
            sendPluginMessage(subs[0], (Player) event.getWhoClicked(), new String[]{"Skyblock"});
            disconnectReason = "§aLobby §f- §aLe joueur §6%player%§a s'est connecté sur le §cSkyblock";
            event.getWhoClicked().closeInventory();
        }
        if(event.getCurrentItem().getType() == Material.STONE && event.getCurrentItem().getItemMeta().getDisplayName().equals("§cSpawn")){
            if(!Boolean.parseBoolean(event.getCurrentItem().getItemMeta().getLore().get(2).replace("§0", ""))) {
                event.getWhoClicked().sendMessage("§aLobby §f- §cLe serveur " + event.getCurrentItem().getItemMeta().getDisplayName() + "§c est fermé, tu ne peux donc pas t'y connecter !");
                event.getWhoClicked().closeInventory();
                return;
            }
            event.setCancelled(true);
            sendPluginMessage(subs[0], (Player) event.getWhoClicked(), new String[]{"Spawn"});
            disconnectReason = "§aLobby §f- §aLe joueur §6%player%§a s'est connecté sur le §cSpawn";
            event.getWhoClicked().closeInventory();
        }
        if(event.getCurrentItem().getType() == Material.COARSE_DIRT && event.getCurrentItem().getItemMeta().getDisplayName().equals("§cSurvie Test")){
            if(!Boolean.parseBoolean(event.getCurrentItem().getItemMeta().getLore().get(2).replace("§0", ""))) {
                event.getWhoClicked().sendMessage("§aLobby §f- §cLe serveur " + event.getCurrentItem().getItemMeta().getDisplayName() + "§c est fermé, tu ne peux donc pas t'y connecter !");
                event.getWhoClicked().closeInventory();
                return;
            }
            event.setCancelled(true);
            sendPluginMessage(subs[0], (Player) event.getWhoClicked(), new String[]{"Survietest"});
            disconnectReason = "§aLobby §f- §aLe joueur §6%player%§a s'est connecté sur le §cSurvie Test";
            event.getWhoClicked().closeInventory();
        }
        if(event.getCurrentItem().getType() == Material.REPEATER && event.getCurrentItem().getItemMeta().getDisplayName().equals("§cTests RedsTom")){
            if(!Boolean.parseBoolean(event.getCurrentItem().getItemMeta().getLore().get(2).replace("§0", ""))) {
                event.getWhoClicked().sendMessage("§aLobby §f- §cLe serveur " + event.getCurrentItem().getItemMeta().getDisplayName() + "§c est fermé, tu ne peux donc pas t'y connecter !");
                event.getWhoClicked().closeInventory();
                return;
            }
            event.setCancelled(true);
            sendPluginMessage(subs[0], (Player) event.getWhoClicked(), new String[]{"RedsTomTests"});
            disconnectReason = "§aLobby §f- §aLe joueur §6%player%§a s'est connecté sur le §cRedsTom Tests";
            event.getWhoClicked().closeInventory();
        }
    }

    private void sendPluginMessage(String sub, Player player, String args[]){

        final ByteArrayDataOutput message = ByteStreams.newDataOutput();
        message.writeUTF(sub);

        for(String arg : args){
            message.writeUTF(arg);
        }

        while(player == null){
            player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        }

        player.sendPluginMessage(Lobby.getInstance(),"BungeeCord" , message.toByteArray());


    }

    private boolean isSubChannel(String subChannel){
        for(String sub : subs){
            if(subChannel.equals(sub)) return true;
        }
        return false;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){

        if(event.getPlayer().getLocation().getY() < 25){
            event.getPlayer().chat("/spawn");
            System.out.println("Le joueur " + event.getPlayer().getName() + " n'a pas exécuté la commande /spawn, il a juste été redirigé car il tombait");
        }

    }

}
