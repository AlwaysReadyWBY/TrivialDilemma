package top.alwaysready.trivialDilemma.module.wax;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface WaxAttachable {
    NamespacedKey KEY_WAX_OWNER = NamespacedKey.fromString("tdilemma:wax_owner");

    UUID getWaxOwner();

    default boolean canModify(Entity entity){
        if(getWaxOwner() == null) return true;
        if(!(entity instanceof Player player)) return false;
        return player.hasPermission("trivialdilemma.wax.bypass")
                || player.getUniqueId().equals(getWaxOwner());
    }
}
