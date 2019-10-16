package io.github.SebastianDanielFrenz.SimpleDBMT.adapter;

import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.SebastianDanielFrenz.SimpleDBMT.DataBaseHandler;
import io.github.SebastianDanielFrenz.SimpleDBMT.expandable.FullValueManager;

/**
 * 
 * @since SimpleDBMT 2.0.0
 *
 */
public class SimpleDBMT2BukkitAdapter extends JavaPlugin {

	public static DataBaseHandler dbh;

	@Override
	public void onEnable() {

		dbh = new DataBaseHandler(new FullValueManager(), getConfig().getString(cDB_DIR));

		this.getServer().getServicesManager().register(DataBaseHandler.class, dbh, this, ServicePriority.Normal);
	}

	@Override
	public void onDisable() {
		dbh.saveDBs();
	}

	public void loadConfiguration() {
		getConfig().addDefault(cDB_DIR, "plugins/SimpleDBMT/DBs");

		getConfig().options().copyDefaults(true);

		saveConfig();
	}

	public static final String cDB_DIR = "database.path";

}
