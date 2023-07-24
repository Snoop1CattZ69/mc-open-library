package org.aslstd.api.openlib.inventory.pane;

import java.util.Arrays;

import org.aslstd.api.openlib.inventory.Page;
import org.aslstd.api.openlib.inventory.Pane;
import org.aslstd.api.openlib.inventory.page.LockedPage;
import org.aslstd.core.OpenLib;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.kyori.adventure.text.Component;

/**
 * <p>SimplePane class.</p>
 *
 * @deprecated Will be removed after new inventory framework will be completed
 * @author Snoop1CattZ69 (https://github.com/Snoop1CattZ69)
 */
@Deprecated(since = "1.0.1", forRemoval = true)
public class SimplePane implements Pane {
	/* Заглушка */
	/** {@inheritDoc} */
	@Override @Deprecated
	public Inventory getInventory() { return Bukkit.createInventory(null, 9); }

	protected String		title;
	protected final int		size;
	@Getter @Setter protected Page 	page;

	@Getter private boolean returnItems = true;

	/**
	 * <p>Constructor for SimplePane.</p>
	 *
	 * @param title a {@link String} object
	 * @param size a int
	 * @param page a {@link org.aslstd.api.openlib.inventory.Page} object
	 */
	public SimplePane(@NonNull String title, int size, Page page) {
		this.title = title;
		this.size = size;

		this.page = page;
	}

	/**
	 * <p>Setter for the field <code>title</code>.</p>
	 *
	 * @param title a {@link String} object
	 */
	public void setTitle(String title) { this.title = title; }

	/**
	 * <p>returnItems.</p>
	 *
	 * @param ret a boolean
	 */
	public void returnItems(boolean ret) { returnItems = ret; }

	/** {@inheritDoc} */
	@Override
	public void fire(InventoryClickEvent event) {
		if (page instanceof LockedPage) {
			final LockedPage lPage = (LockedPage) page;
			if (lPage.isUnlocked(event.getSlot())) {
				if (lPage.getEmptyClick() != null)
					lPage.getEmptyClick().accept(event);
				return;
			} else {
				lPage.fire(event);
				return;
			}
		}

		page.fire(event);
	}

	/** {@inheritDoc} */
	@Override
	public void showTo(Player... players) {
		final Inventory inventory = Bukkit.createInventory(this, size, Component.text(title));

		page.display(inventory);

		Arrays.asList(players).stream()
		.filter(p -> p != null)
		.forEach(p -> {
			Bukkit.getScheduler().scheduleSyncDelayedTask(OpenLib.instance(), () -> {
				p.closeInventory(); p.openInventory(inventory);
			});
		});
	}

}
