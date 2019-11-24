package io.github.SebastianDanielFrenz.SimpleDBMT.adapter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.exception.ExceptionUtils;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.SebastianDanielFrenz.SimpleDBMT.CrashedDBstock;
import io.github.SebastianDanielFrenz.SimpleDBMT.DataBase;
import io.github.SebastianDanielFrenz.SimpleDBMT.DataBaseHandler;
import io.github.SebastianDanielFrenz.SimpleDBMT.Table;
import io.github.SebastianDanielFrenz.SimpleDBMT.registry.RegistryValueManager;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBvalue;

/**
 * 
 * @since SimpleDBMT 2.0.2
 * @version SimpleDBMT 2.2.0
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

		dbh = new DataBaseHandler(new RegistryValueManager(CrashedDBstock.getDefaultTypeRegistry()),
				getConfig().getString(cDB_DIR));

		this.getServer().getServicesManager().register(DataBaseHandler.class, dbh, this, ServicePriority.Normal);

		List<AutoSaveEventListener> autoSaveEventListeners = new ArrayList<AutoSaveEventListener>();

		new Thread(new Runnable() {

			@Override
			public void run() {
				if (getConfig().getInt(cAUTOSAVE_TIME) == -1) {
					return;
				}
				while (true) {
					for (AutoSaveEventListener listener : autoSaveEventListeners) {
						listener.run();
					}

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
			public final String[] permission_list_database = new String[] { "SimpleDBMT.list.database",
					"SimpleDBMT.list.*" };
			public final String[] permission_list_table = new String[] { "SimpleDBMT.list.table", "SimpleDBMT.list.*" };
			public final String[] permission_show_database = new String[] { "SimpleDBMT.show.database",
					"SimpleDBMT.show.*" };
			public final String[] permission_show_table = new String[] { "SimpleDBMT.show.table", "SimpleDBMT.show.*" };
			public final String[] permission_show_all = new String[] { "SimpleDBMT.show.all", "SimpleDBMT.show.*" };

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
						if (hasPermission(sender, permission_list_database)) {
							sender.sendMessage(prefix + "/simpledebmt list <database>");
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
						sender.sendMessage(prefix + "§aDone!");
					}
					return true;
				} else if (args[0].equalsIgnoreCase("list")) {
					if (args.length == 1) {
						// list DBs
						if (hasPermission(sender, permission_list_database)) {
							sender.sendMessage(prefix + "List of data bases:");
							for (DataBase db : dbh.getDBs()) {
								sender.sendMessage(prefix + " - " + db.getName());
								for (String table : db.getTablenames()) {
									sender.sendMessage(prefix + "     - " + table);
								}
							}
						}
					} else {
						if (hasPermission(sender, permission_list_table)) {
							sender.sendMessage(prefix + "List of tables in " + args[1] + ":");
							for (String table : dbh.getDataBase(args[1]).getTablenames()) {
								sender.sendMessage(prefix + " - " + table);
							}
						}
					}
				} else if (args[0].equalsIgnoreCase("show")) {
					if (args.length == 1) {
						if (hasPermission(sender, permission_show_all)) {
							sender.sendMessage(prefix + "Showing every table's content:");
							for (DataBase db : dbh.getDBs()) {
								sender.sendMessage(prefix + "DB: " + db.getName());
								for (String tablename : db.getTablenames()) {
									sender.sendMessage(prefix + "    Table: " + tablename);
									String headerText = "|";
									for (String header : db.getTable(tablename).getHeaders()) {
										headerText += header + "|";
									}
									sender.sendMessage(headerText);

									for (List<DBvalue> row : db.getTable(tablename).getValues()) {
										String tmp = "|";
										for (DBvalue value : row) {
											tmp += value.Display() + "|";
										}
										sender.sendMessage(tmp);
									}
								}
							}
						}
					} else if (args.length == 2) {
						if (hasPermission(sender, permission_show_database)) {
							sender.sendMessage(prefix + "Showing contents of the " + args[1] + ":");
							DataBase db = dbh.getDataBase(args[1]);

							for (String tablename : db.getTablenames()) {
								sender.sendMessage(prefix + "Table: " + tablename);
								String headerText = "|";
								for (String header : db.getTable(tablename).getHeaders()) {
									headerText += header + "|";
								}
								sender.sendMessage(headerText);

								for (List<DBvalue> row : db.getTable(tablename).getValues()) {
									String tmp = "|";
									for (DBvalue value : row) {
										tmp += value.Display() + "|";
									}
									sender.sendMessage(tmp);
								}
							}
						}
					} else {
						if (hasPermission(sender, permission_show_table)) {
							sender.sendMessage(
									prefix + "Showing contents of the table " + args[2] + " in " + args[1] + ":");
							DataBase db = dbh.getDataBase(args[1]);
							String tablename = args[2];
							String headerText = "|";
							for (String header : db.getTable(tablename).getHeaders()) {
								headerText += header + "|";
							}
							sender.sendMessage(headerText);

							for (List<DBvalue> row : db.getTable(tablename).getValues()) {
								String tmp = "|";
								for (DBvalue value : row) {
									tmp += value.Display() + "|";
								}
								sender.sendMessage(tmp);
							}
						}
					}
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
