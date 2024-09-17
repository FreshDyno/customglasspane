package me.freshdyno.customGlassPanePlugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.persistence.PersistentDataType;

public class CustomGlassPanePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Enregistrement de la commande
        if (this.getCommand("custompane") != null) {
            this.getCommand("custompane").setExecutor(new CustomPaneCommand());
        }
    }

    public class CustomPaneCommand implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Seuls les joueurs peuvent exécuter cette commande.");
                return false;
            }

            Player player = (Player) sender;

            if (!player.hasPermission("custompane.use")) {
                player.sendMessage("§cVous n'avez pas la permission d'utiliser cette commande.");
                return true;
            }

            if (args.length != 1) {
                player.sendMessage("Utilisation: /custompane <name>");
                return false;
            }

            String paneName = args[0];

            // Création d'une vitre personnalisée
            ItemStack customPane = new ItemStack(Material.GLASS_PANE);
            ItemMeta meta = customPane.getItemMeta();

            if (meta != null) {
                meta.setDisplayName(paneName);

                // Ajouter des données persistantes (pour identifier cet objet)
                NamespacedKey key = new NamespacedKey(CustomGlassPanePlugin.this, "custom_pane");
                meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, paneName);

                customPane.setItemMeta(meta);
            }

            player.getInventory().addItem(customPane);
            player.sendMessage("Vitre personnalisée ajoutée: " + paneName);

            return true;
        }
    }
}