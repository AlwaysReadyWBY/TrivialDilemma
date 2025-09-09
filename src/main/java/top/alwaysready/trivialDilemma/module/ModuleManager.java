package top.alwaysready.trivialDilemma.module;

import org.bukkit.configuration.ConfigurationSection;
import top.alwaysready.trivialDilemma.module.armorstand.ArmorStandModule;
import top.alwaysready.trivialDilemma.module.casing.CasingModule;
import top.alwaysready.trivialDilemma.module.itemframe.ItemFrameModule;
import top.alwaysready.trivialDilemma.module.wax.WaxModule;

import java.util.Hashtable;
import java.util.Map;

public class ModuleManager {
    private final Map<String,Module> moduleMap = new Hashtable<>();

    public ModuleManager(){
        registerModule(new ItemFrameModule());
        registerModule(new ArmorStandModule());
        registerModule(new CasingModule());
        registerModule(new WaxModule());
    }

    public void registerModule(Module module){
        moduleMap.put(module.getKey(),module);
    }

    public void loadModuleConfig(ConfigurationSection sec){
        if(sec == null) return;
        moduleMap.values().forEach(module -> module.loadConfig(sec.getConfigurationSection(module.getKey())));
    }
}
