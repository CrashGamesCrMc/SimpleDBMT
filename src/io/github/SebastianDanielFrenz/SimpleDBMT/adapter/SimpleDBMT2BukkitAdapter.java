package io.github.SebastianDanielFrenz.SimpleDBMT.adapter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.exception.ExceptionUtils;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.SebastianDanielFrenz.SimpleDBMT.DataBase;
import io.github.SebastianDanielFrenz.SimpleDBMT.DataBaseHandler;
import io.github.SebastianDanielFrenz.SimpleDBMT.Table;
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

		loadConfiguration();

		try {
			Files.createDirectories(Paths.get(getConfig().getString(cDB_DIR)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		dbh = new DataBaseHandler(new FullValueManager(), getConfig().getString(cDB_DIR));

		this.getServer().getServicesManager().register(DataBaseHandler.class, dbh, this, ServicePriority.Normal);

		new Thread(new Runnable() {

			@Override
			public void run() {
				if (getConfig().getInt(cAUTOSAVE_TIME) == -1) {
					return;
				}
				while (true) {
					dbh.saveDBs();
					try {
						Thread.sleep(1000 * getConfig().getInt(cAUTOSAVE_TIME));
					} catch (InterruptedException e) {
						getLogger().info("Detected shutdown!");
						break;
					}
				}
			}
		});

		getCommand("simpledbmt").setExecutor(new CommandExecutor() {

			public final String prefix = "§f[§4SimpleDBMT§f]: ";

			public final String[] permission_help = new String[] { "SimpleDBMT.help" };
			public final String[] permission_dump = new String[] { "SimpleDBMT.dump" };
			public final String[] permission_save = new String[] { "SimpleDBMT.save" };

			public boolean hasPermission(CommandSender sender, String[] perms) {
				if (sender.isOp() || sender instanceof ConsoleCommandSender || sender.hasPermission("SimpleDBMT.*")) {
					return true;
				}
				for (String perm : perms) {
					if (sender.hasPermission(perm)) {
						return true;
					}
				}

				sender.sendMessage(prefix + "§4You do not have permission to run this command!");

				return false;
			}

			@Override
			public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
				if (args.length == 0) {
					if (hasPermission(sender, permission_help)) {
						sender.sendMessage(prefix + "Displaying help for SimpleDBMT");
						if (hasPermission(sender, permission_dump)) {
							sender.sendMessage(prefix + "/simpledbmt dump <database>");
						}
						if (hasPermission(sender, permission_save)) {
							sender.sendMessage(prefix + "/simpledbmt save");
						}
					}
					return true;
				} else if (args[0].equalsIgnoreCase("dump")) {
					if (hasPermission(sender, permission_dump)) {
						if (args.length == 1) {
							sender.sendMessage(prefix + "Missing database name!");
						} else {
							DataBase db = dbh.getDataBase(args[1]);
							Table table;
							for (int i = 0; i < db.getTables().size(); i++) {
								table = db.getTables().get(i);
								try {
									table.ToQueryResult().DumpHTMLandFormat(db.getTablenames().get(i) + ".html");
								} catch (FileNotFoundException e) {
									e.printStackTrace();
									sender.sendMessage("§4Error: " + ExceptionUtils.getStackTrace(e));
								}
							}

							sender.sendMessage(prefix + "§aDone!");
						}
					}
					return true;
				} else if (args[0].equalsIgnoreCase("save")) {
					if (hasPermission(sender, permission_save)) {
						dbh.saveDBs();
					}
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public void onDisable() {
		getLogger().info("Saving databases...");
		dbh.saveDBs();
		getLogger().info("Saved databases!");

	}

	public void loadConfiguration() {
		getConfig().addDefault(cDB_DIR, "plugins/SimpleDBMT/DBs");
		getConfig().addDefault(cAUTOSAVE_TIME, 5);

		getConfig().options().copyDefaults(true);

		saveConfig();
	}

	public static final String cDB_DIR = "database.path";

	public static final String cAUTOSAVE_TIME = "autosave_time";

}
