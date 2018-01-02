package org.inventivetalent.bossbarcompass;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BossbarCompass extends JavaPlugin implements Listener {

	Map<UUID, BossBar> bars = new HashMap<>();

	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void on(PlayerQuitEvent event) {
		bars.remove(event.getPlayer().getUniqueId());
	}

	@EventHandler
	public void on(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if ((player.getInventory().getItemInMainHand() == null || Material.COMPASS != player.getInventory().getItemInMainHand().getType()) &&
				(player.getInventory().getItemInOffHand() == null || Material.COMPASS != player.getInventory().getItemInOffHand().getType())) {
			return;
		}
		if (Math.abs(event.getFrom().getYaw() - event.getTo().getYaw()) < 0.2) { return; }

		BossBar bar = bars.get(player.getUniqueId());
		if (bar == null) {
			bar = Bukkit.createBossBar("", BarColor.BLUE, BarStyle.SEGMENTED_20);
			bar.addPlayer(player);
			bars.put(player.getUniqueId(), bar);
		}

		String s = "";
		for (int i = (int) Math.abs(player.getLocation().getYaw()) - 16; i < ((int) Math.abs(player.getLocation().getYaw())) + 16; i++) {
			if (i == 0) {
				s += "S";
			} else if (i == 45) {
				s += "SW";
			} else if (i == 90) {
				s += "W";
			} else if (i == 135) {
				s += "NW";
			} else if (i == 180) {
				s += "N";
			} else if (i == 225) {
				s += "NE";
			} else if (i == 270) {
				s += "E";
			} else if (i == 315) {
				s += "SE";
			} else {
				if (i % 2 == 0) { s += "|"; }
			}
			s += " ";
		}

		bar.setTitle(s);
		bar.setVisible(true);
	}

}
