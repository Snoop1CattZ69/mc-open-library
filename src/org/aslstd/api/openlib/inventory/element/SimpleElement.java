package org.aslstd.api.openlib.inventory.element;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.aslstd.api.bukkit.items.IStatus;
import org.aslstd.api.bukkit.items.ItemStackUtil;
import org.aslstd.api.bukkit.utils.BasicMetaAdapter;
import org.aslstd.api.openlib.inventory.Element;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import net.kyori.adventure.text.TextComponent;

/**
 * <p>SimpleElement class.</p>
 *
 * @deprecated Will be removed after new inventory framework will be completed
 * @author Snoop1CattZ69 (https://github.com/Snoop1CattZ69)
 */
@Deprecated(since = "1.0.1", forRemoval = true)
public class SimpleElement implements Element {

	@Getter private final String				hash;
	@Getter private ItemStack					icon;
	protected Consumer<InventoryClickEvent>		func;

	public final int pX, pY;

	/**
	 * <p>setFunction.</p>
	 *
	 * @param func a {@link java.util.function.Consumer} object
	 */
	public void setFunction(Consumer<InventoryClickEvent> func) {
		this.func = func;
	}

	/**
	 * <p>Constructor for SimpleElement.</p>
	 *
	 * @param icon a {@link org.bukkit.inventory.ItemStack} object
	 * @param createFunction a boolean
	 */
	public SimpleElement(ItemStack icon, boolean createFunction) {
		pX = 0; pY = 0;
		this.icon = icon;
		hash = ItemStackUtil.toString(icon);
		if (createFunction)
			setFunction(e -> e.setCancelled(true));
	}

	/**
	 * <p>Constructor for SimpleElement.</p>
	 *
	 * @param icon a {@link org.bukkit.inventory.ItemStack} object
	 * @param createFunction a boolean
	 * @param px a int
	 * @param py a int
	 */
	public SimpleElement(ItemStack icon, boolean createFunction, int px, int py) {
		pX = px; pY = py;
		this.icon = icon;
		hash = ItemStackUtil.toString(icon);
		if (createFunction)
			setFunction(e -> e.setCancelled(true));
	}


	/**
	 * <p>Constructor for SimpleElement.</p>
	 *
	 * @param icon a {@link org.bukkit.inventory.ItemStack} object
	 * @param function a {@link java.util.function.Consumer} object
	 */
	public SimpleElement(ItemStack icon, Consumer<InventoryClickEvent> function) {
		pX = 0; pY = 0;
		this.icon = icon;
		hash = ItemStackUtil.toString(icon);
		func = Objects.requireNonNull(function);
	}

	/**
	 * <p>Constructor for SimpleElement.</p>
	 *
	 * @param icon a {@link org.bukkit.inventory.ItemStack} object
	 * @param function a {@link java.util.function.Consumer} object
	 * @param px a int
	 * @param py a int
	 */
	public SimpleElement(ItemStack icon, Consumer<InventoryClickEvent> function, int px, int py) {
		pX = px; pY = py;
		this.icon = icon;
		hash = ItemStackUtil.toString(icon);
		func = Objects.requireNonNull(function);
	}

	/**
	 * <p>changeIcon.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 */
	public void changeIcon(ItemStack stack) {
		if (ItemStackUtil.validate(stack, IStatus.HAS_MATERIAL))
			changeType(stack.getType());
		if (ItemStackUtil.validate(stack, IStatus.HAS_DISPLAYNAME))
			setIconDisplayName(( (TextComponent)stack.getItemMeta().displayName() ).content());
		if (ItemStackUtil.validate(stack, IStatus.HAS_LORE))
			setIconLore(stack.getItemMeta().getLore());
	}

	/**
	 * <p>changeType.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 */
	public void changeType(Material mat) {
		if (mat != Material.AIR)
			icon.setType(mat);
	}

	/**
	 * <p>setIconDisplayName.</p>
	 *
	 * @param name a {@link String} object
	 */
	public void setIconDisplayName(String name) {
		if (name != null)
			BasicMetaAdapter.setDisplayName(icon, name);
	}

	/**
	 * <p>setIconLore.</p>
	 *
	 * @param lore a {@link List} object
	 */
	public void setIconLore(List<String> lore) {
		if (lore != null)
			BasicMetaAdapter.setLore(icon, lore);
	}

	/** {@inheritDoc} */
	@Override
	public void accept(InventoryClickEvent event) {
		if (this.equals(event.getCurrentItem()))
			if (func != null) {
				func.accept(event);
			}
	}

}
