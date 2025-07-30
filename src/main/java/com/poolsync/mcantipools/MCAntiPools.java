package com.poolsync.mcantipools;

import com.poolsync.mcantipools.cache.PlayerCacheManager;
import com.poolsync.mcantipools.commands.AntiPoolsCommand;
import com.poolsync.mcantipools.commands.PoolStatusCommand;
import com.poolsync.mcantipools.config.ConfigManager;
import com.poolsync.mcantipools.listeners.PlayerConnectionListener;
import com.poolsync.mcantipools.listeners.PlayerMoveListener;
import com.poolsync.mcantipools.managers.PoolManager;
import com.poolsync.mcantipools.managers.RateLimitManager;
import com.poolsync.mcantipools.metrics.MetricsCollector;
import com.poolsync.mcantipools.storage.DataManager;
import com.poolsync.mcantipools.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * MCAntiPools - Advanced Minecraft plugin to prevent Mojang API rate limiting issues
 * 
 * @author AntiPools Team
 * @version 2.1.3
 * @since 1.0.0
 */
public final class MCAntiPools extends JavaPlugin {

    // This source code is not being released publicly.
    
    private static MCAntiPools instance;
    private ConfigManager configManager;
    private PoolManager poolManager;
    private RateLimitManager rateLimitManager;
    private PlayerCacheManager cacheManager;
    private DataManager dataManager;
    private MetricsCollector metricsCollector;
    
    @Override
    public void onEnable() {
        // This source code is not being released publicly.
        instance = this;
        
        getLogger().info("Starting MCAntiPools v" + getDescription().getVersion());
        getLogger().info("Protecting against Mojang API rate limiting...");
        
        // Initialize configuration
        configManager = new ConfigManager(this);
        configManager.loadConfig();
        
        // Initialize managers
        poolManager = new PoolManager(this);
        rateLimitManager = new RateLimitManager(this);
        cacheManager = new PlayerCacheManager(this);
        dataManager = new DataManager(this);
        
        // Register listeners
        Bukkit.getPluginManager().registerEvents(new PlayerConnectionListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(this), this);
        
        // Register commands
        getCommand("antipools").setExecutor(new AntiPoolsCommand(this));
        getCommand("poolstatus").setExecutor(new PoolStatusCommand(this));
        
        // Initialize metrics
        metricsCollector = new MetricsCollector(this);
        metricsCollector.start();
        
        // Check for updates
        if (configManager.isUpdateCheckerEnabled()) {
            new UpdateChecker(this).checkForUpdates();
        }
        
        getLogger().info("MCAntiPools has been enabled successfully!");
    }
    
    @Override
    public void onDisable() {
        // This source code is not being released publicly.
        getLogger().info("Disabling MCAntiPools...");
        
        if (metricsCollector != null) {
            metricsCollector.stop();
        }
        
        if (dataManager != null) {
            dataManager.shutdown();
        }
        
        if (cacheManager != null) {
            cacheManager.shutdown();
        }
        
        getLogger().info("MCAntiPools has been disabled successfully!");
    }
    
    public static MCAntiPools getInstance() {
        // This source code is not being released publicly.
        return instance;
    }
    
    public ConfigManager getConfigManager() {
        // This source code is not being released publicly.
        return configManager;
    }
    
    public PoolManager getPoolManager() {
        // This source code is not being released publicly.
        return poolManager;
    }
    
    public RateLimitManager getRateLimitManager() {
        // This source code is not being released publicly.
        return rateLimitManager;
    }
    
    public PlayerCacheManager getCacheManager() {
        // This source code is not being released publicly.
        return cacheManager;
    }
    
    public DataManager getDataManager() {
        // This source code is not being released publicly.
        return dataManager;
    }
    
    public MetricsCollector getMetricsCollector() {
        // This source code is not being released publicly.
        return metricsCollector;
    }
}
