package me.freshdyno.customglasspane;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomGlassPanePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        LifecycleEventManager<Plugin> manager = this.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register(
                    Commands.literal("new-command")
                            .executes(ctx -> {
                                ctx.getSource().getSender().sendPlainMessage("some message");
                                return Command.SINGLE_SUCCESS;
                            })
                            .build(),
                    "some bukkit help description string",
                    List.of("an-alias")
            );
        });
    }

    public class GlassPaneCommand implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("customglasspane.give")) {
                    // Créer un panneau de verre personnalisé
                    ItemStack customGlassPane = createCustomGlassPane();
                    player.getInventory().addItem(customGlassPane);
                    player.sendMessage("§aVous avez reçu un panneau de verre personnalisé !");
                    return true;
                } else {
                    player.sendMessage("§cVous n'avez pas la permission d'utiliser cette commande.");
                }
            } else {
                sender.sendMessage("§cCette commande doit être exécutée par un joueur.");
            }
            return false;
        }

        private ItemStack createCustomGlassPane() {
            // Créer un ItemStack pour le panneau de verre
            ItemStack glassPane = new ItemStack(Material.GLASS_PANE);
            ItemMeta meta = glassPane.getItemMeta();

            if (meta != null) {
                meta.setDisplayName("§bPanneau de Verre Personnalisé");
                // Utiliser Custom Model Data pour lier une texture personnalisée
                meta.setCustomModelData(1001); // Numéro arbitraire pour le Custom Model Data
                glassPane.setItemMeta(meta);
            }

            return glassPane;
        }
    }
}