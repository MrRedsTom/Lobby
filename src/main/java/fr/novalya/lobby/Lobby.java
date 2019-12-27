package fr.novalya.lobby;

import fr.novalya.lobby.events.Events;
import fr.novalya.lobby.events.PluginMessageListeners;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.*;

public final class Lobby extends JavaPlugin {

    private static Lobby instance;


    public static Lobby getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;

        getCommand("inv").setExecutor((sender, command, label, args) -> {

            if(!(sender instanceof Player)) return false;
            giveMenuInv((Player) sender);
            return false;

        });

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessageListeners());

        Bukkit.getPluginManager().registerEvents(new Events(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    
    public void giveMenuInv(Player p){
        ItemBuilder it = new ItemBuilder(Material.COMPASS)
                .setName("§cChoix du Serveur");
        ItemBuilder glass = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE)
                .setName("§a");

        p.getInventory().clear();

        /*for(int i = 0 ; i < p.getInventory().getSize() ; i++){

            if(p.getInventory().getItem(i) != null && p.getInventory().getItem(i).getType() != glass.toItemStack().getType()) continue;
            p.getInventory().setItem(i, glass.toItemStack());

        }

        */p.getInventory().setItem(4, it.toItemStack());/*
        p.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
        p.getInventory().setHelmet(new ItemStack(Material.AIR));
        p.getInventory().setChestplate(new ItemStack(Material.AIR));
        p.getInventory().setLeggings(new ItemStack(Material.AIR));
        p.getInventory().setBoots(new ItemStack(Material.AIR));*/
    }

    public void openGameMenu(Player p) {
        boolean survieReacheable = pingPort(26777);
        boolean spawnReacheable = pingPort(26672);
        boolean survieTestReacheable = pingPort(36666);
        boolean skyblockReacheable = pingPort(54322);
        boolean redstomReacheable = pingPort(54888);


        ItemBuilder survie = new ItemBuilder(Material.GRASS_BLOCK)
                .setName("§aSurvie")
                .addLoreLine("§6Accessible à tous les joueurs")
                .addLoreLine("§6État : " + (survieReacheable ? "§aOuvert" : "§cFermé"))
                .addLoreLine("§0" + survieReacheable);
        ItemBuilder spawn = new ItemBuilder(Material.STONE)
                .setName("§cSpawn")
                .addLoreLine("§6Uniquement accessible au modérateurs")
                .addLoreLine("§6État : " + (spawnReacheable ? "§aOuvert" : "§cFermé"))
                .addLoreLine("§0" + spawnReacheable);
        ItemBuilder survie1 = new ItemBuilder(Material.COARSE_DIRT)
                .setName("§cSurvie Test")
                .addLoreLine("§6Uniquement accessible au modérateurs")
                .addLoreLine("§6État : " + (survieTestReacheable ? "§aOuvert" : "§cFermé"))
                .addLoreLine("§0" + survieTestReacheable);

        ItemBuilder skyblock = new ItemBuilder(Material.JUNGLE_LEAVES)
                .setName("§cSkyBlock")
                .addLoreLine("§6Uniquement accessible au modérateurs")
                .addLoreLine("§6État : " + (skyblockReacheable ? "§aOuvert" : "§cFermé"))
                .addLoreLine("§0" + skyblockReacheable);
        ItemBuilder test = new ItemBuilder(Material.REPEATER)
                .setName("§cTests RedsTom")
                .addLoreLine("§6Uniquement accessible au modérateurs")
                .addLoreLine("§6État : " + (redstomReacheable ? "§aOuvert" : "§cFermé"))
                .addLoreLine("§0" + redstomReacheable);

        ItemBuilder glass = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE)
                .setName("§a");

        Inventory inv = Bukkit.createInventory(null, 27, "§6Serveurs");
        if(p.hasPermission("lobby.other_servers")) {
            inv.setItem(11, spawn.toItemStack());
            inv.setItem(15, survie1.toItemStack());
            inv.setItem(21, skyblock.toItemStack());
            inv.setItem(23, test.toItemStack());
            inv.setItem(13, survie.toItemStack());
        }else{
            inv.setItem(11, survie.toItemStack());
            inv.setItem(15, skyblock.toItemStack());
        }
        for(int i = 0 ; i < inv.getSize() ; i++){

            if(inv.getItem(i) != null && inv.getItem(i).getType() != glass.toItemStack().getType()) continue;
            inv.setItem(i, glass.toItemStack());

        }

        p.openInventory(inv);
    }

    private boolean pingPort(int port){
        try {
            try (Socket crunchifySocket = new Socket()) {
                crunchifySocket.connect(new InetSocketAddress("127.0.0.1", port), 5000);
            }
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
