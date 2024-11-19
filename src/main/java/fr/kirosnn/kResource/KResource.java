package fr.kirosnn.kResource;

import fr.kirosnn.kResource.files.ConfigManager;
import fr.kirosnn.kResource.files.LangManager;
import fr.kirosnn.kResource.listeners.PlayerJoin;
import fr.kirosnn.kResource.listeners.ResourcePackStatus;
import fr.kirosnn.kResource.loaders.LoadListeners;
import fr.kirosnn.kResource.loaders.LoadMessages;
import fr.kirosnn.kResource.managers.ResourcePackManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class KResource extends JavaPlugin {

    private ConfigManager configManager;
    private LangManager langManager;
    private ResourcePackManager resourcePackManager;
    private LoadListeners loadListeners;
    private LoadMessages loadMessages;
    private BukkitAudiences adventure;

    @Override
    public void onEnable() {
        this.adventure = BukkitAudiences.create(this);

        try {
            this.configManager = new ConfigManager(this);
            this.langManager = new LangManager(this);
            this.loadListeners = new LoadListeners(this);
            this.loadMessages = new LoadMessages(this);

            loadMessages.logEnableMessage(false, null, null);

            loadListeners.registerListener(new PlayerJoin(this), new ResourcePackStatus(this));

            resourcePackManager = new ResourcePackManager(this);

        } catch (Exception e) {
            loadMessages.logEnableMessage(true, null, e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        if (this.adventure != null) {
            this.adventure.close();
        }

        try {
            loadMessages.logDisableMessage(false, "Plugin désactivé sans erreur", null);
        } catch (Exception e) {
            loadMessages.logDisableMessage(true, null, e.getMessage());
        }
    }


    public @NotNull ConfigManager getConfigManager() {
        return this.configManager;
    }

    public @NotNull LangManager getLangManager() {
        return this.langManager;
    }

    public @NotNull LoadListeners getLoadListeners() {
        return this.loadListeners;
    }

    public @NotNull LoadMessages getLoadMessages() {
        return this.loadMessages;
    }

    public BukkitAudiences adventure() {
        return this.adventure;
    }

    public ResourcePackManager getResourcePackManager() {
        return resourcePackManager;
    }
}