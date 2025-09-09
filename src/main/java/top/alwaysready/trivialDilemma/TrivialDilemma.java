package top.alwaysready.trivialDilemma;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import top.alwaysready.trivialDilemma.module.ModuleManager;
import top.alwaysready.trivialDilemma.utils.ConfigUpdater;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public final class TrivialDilemma extends JavaPlugin {

    private static TrivialDilemma instance;

    public static TrivialDilemma getInstance() {
        return instance;
    }

    private ModuleManager moduleManager;

    @Override
    public void onEnable() {
        instance = this;
        loadConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void loadConfig() {
        getDataFolder().mkdirs();
        saveResource("config.yml", false);
        File file = getDataFolder().toPath().resolve("config.yml").toFile();
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        switch (ConfigUpdater.MAIN.update(cfg)) {
            case FAILED -> {
                saveResource("config.yml", true);
                cfg = YamlConfiguration.loadConfiguration(file);
            }
            case UPDATED -> {
                try {
                    cfg.save(file);
                } catch (IOException e) {
                    TrivialDilemma.getInstance().getLogger().log(Level.WARNING,"Failed to save config.yml",e);
                }
            }
        }
        getModuleManager().loadModuleConfig(cfg.getConfigurationSection("modules"));
        getLogger().info("Reloaded!");
    }

    public ModuleManager getModuleManager() {
        if (moduleManager == null) moduleManager = new ModuleManager();
        return moduleManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("trivialdilemma.admin")) return false;
        loadConfig();
        sender.sendMessage("Reloaded");
        return true;
    }
}
