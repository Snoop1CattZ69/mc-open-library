package org.aslstd.api.bukkit.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.aslstd.api.bukkit.items.IStatus;
import org.aslstd.api.bukkit.items.ItemStackUtil;
import org.aslstd.api.bukkit.message.Texts;
import org.aslstd.api.bukkit.value.util.NumUtil;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

/**
 * Will be included in ItemStackUtil in future
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
@UtilityClass
public class BasicMetaAdapter { // Basic Lore Adapter

	private Matcher matcher;

	/**
	 * <p>getStringValue.</p>
	 *
	 * @param patt a {@link java.util.regex.Pattern} object
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @return a {@link String} object
	 */
	public @Nullable String getStringValue(Pattern patt, ItemStack stack) {
		if  (!ItemStackUtil.validate(stack, IStatus.HAS_LORE)) return "";
		return getStringValue(patt, stack.getItemMeta().lore().toString() );
	}

	/**
	 * <p>getStringValue.</p>
	 *
	 * @param patt a {@link java.util.regex.Pattern} object
	 * @param lore a {@link List} object
	 * @return a {@link String} object
	 */
	public @Nullable String getStringValue(Pattern patt, List<Component> lore) {
		String value = "";
		if (contains(lore, patt)) {
			matcher = patt.matcher(Texts.e(lore.toString()).toLowerCase());
			if (matcher.find())
				try {
					value = matcher.group(1);
				} catch (final IllegalStateException e) {
					Texts.debug(patt.pattern());
					Texts.debug(lore.toString());
				}
		}

		return value == null ? "" : value;
	}

	/**
	 * <p>addLore.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @param strings a {@link String} object
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	public ItemStack addLore(ItemStack stack, String... strings) {
		if (!ItemStackUtil.validate(stack, IStatus.HAS_MATERIAL)) return stack;
		final ItemMeta meta = stack.getItemMeta();
		List<Component> lore = new ArrayList<>();

		if (meta.hasLore())
			lore = meta.lore();

		for (final String str : strings)
			lore.add(Component.text(Texts.c(str)));

		meta.lore(lore);
		stack.setItemMeta(meta);
		return stack;
	}

	/**
	 * <p>setLore.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @param lore a {@link List} object
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	public ItemStack setLore(ItemStack stack, List<String> lore) {
		if (!ItemStackUtil.validate(stack, IStatus.HAS_MATERIAL) || lore.isEmpty()) return stack;
		final ItemMeta meta = stack.getItemMeta();

		final List<Component> colored = new ArrayList<>();

		for (final String key : lore)
			if (key != null)
				colored.add(Component.text(Texts.c(key)));

		meta.lore(colored);
		stack.setItemMeta(meta);
		return stack;
	}

	/**
	 * <p>setLore.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @param index a int
	 * @param loreString a {@link String} object
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	public ItemStack setLore(ItemStack stack, int index, String loreString) {
		if (!ItemStackUtil.validate(stack, IStatus.HAS_MATERIAL)) return stack;
		final ItemMeta meta = stack.getItemMeta();
		List<Component> lore = new ArrayList<>();

		if (meta.hasLore())
			lore = meta.lore();

		if (lore.size()-1 >= index)
			lore.set(index, Component.text(Texts.c(loreString)));
		else
			lore.add(Component.text(Texts.c(loreString)));

		meta.lore(lore);
		stack.setItemMeta(meta);
		return stack;
	}

	/**
	 * <p>setHideFlags.</p>
	 *
	 * @see ItemStackUtil#setFlags(ItemStack, ItemFlag...)
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @param flags a {@link org.bukkit.inventory.ItemFlag} object
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	public ItemStack setHideFlags(ItemStack stack, ItemFlag... flags) {
		if (!ItemStackUtil.validate(stack, IStatus.HAS_MATERIAL)) return stack;
		stack.editMeta(meta -> {
			Stream.of(flags).filter(f -> !meta.hasItemFlag(f)).forEach(meta::addItemFlags);
		});

		return stack;
	}

	/**
	 * <p>addEnchant.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @param ench a {@link org.bukkit.enchantments.Enchantment} object
	 * @param lvl a int
	 * @param ignoreLvl a boolean
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	public ItemStack addEnchant(ItemStack stack, Enchantment ench, int lvl, boolean ignoreLvl) {
		if (!ItemStackUtil.validate(stack, IStatus.HAS_MATERIAL)) return stack;

		stack.editMeta(meta -> {
			if (meta.hasEnchant(ench))
				meta.removeEnchant(ench);
			meta.addEnchant(ench, lvl, ignoreLvl);
		});

		return stack;
	}

	/**
	 * <p>setDisplayName.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @param display a {@link String} object
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	public ItemStack setDisplayName(ItemStack stack, String display) {
		if (!ItemStackUtil.validate(stack, IStatus.HAS_MATERIAL)) return stack;

		stack.editMeta(meta -> {
			meta.displayName(display == null ? null : Component.text(Texts.c(display)));
		});

		return stack;
	}

	/**
	 * <p>getStringValue.</p>
	 *
	 * @param patt a {@link java.util.regex.Pattern} object
	 * @param loreString a {@link String} object
	 * @return a {@link String} object
	 */
	public String getStringValue(Pattern patt, String loreString) {
		String value = "";

		if (contains(loreString, patt)) {
			matcher = patt.matcher(Texts.e(loreString).toLowerCase());
			if (matcher.find()) value = matcher.group(1);
		}

		return value;
	}

	/**
	 * <p>getLore.</p>
	 *
	 * @param key a {@link String} object
	 * @param value a {@link String} object
	 * @return a {@link String} object
	 */
	public String getLore(String key, String value) {
		return Texts.c(key + ": " + value);
	}

	/**
	 * <p>getDoubleValue.</p>
	 *
	 * @param patt a {@link java.util.regex.Pattern} object
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @return a double
	 */
	public double getDoubleValue(Pattern patt, ItemStack stack) {
		if  (!ItemStackUtil.validate(stack, IStatus.HAS_LORE)) return 0.0D;
		return getDoubleValue(patt, stack.getItemMeta().lore());
	}

	/**
	 * <p>getDoubleValue.</p>
	 *
	 * @param patt a {@link java.util.regex.Pattern} object
	 * @param lore a {@link List} object
	 * @return a double
	 */
	public double getDoubleValue(Pattern patt, List<Component> lore) {
		String value = "0.0D";

		if (contains(lore, patt)) {
			matcher = patt.matcher(Texts.e(lore.toString()).toLowerCase());
			if (matcher.find())
				value = matcher.group(1);
		}

		return NumUtil.parseDouble(value.replaceAll("%", ""));
	}

	/**
	 * <p>contains.</p>
	 *
	 * @param loreString a {@link String} object
	 * @param patt a {@link java.util.regex.Pattern} object
	 * @return a boolean
	 */
	public boolean contains(String loreString, Pattern patt) {
		return patt.matcher(Texts.e(loreString).toLowerCase()).find();
	}
	/**
	 * <p>contains.</p>
	 *
	 * @param lore a {@link List} object
	 * @param patt a {@link java.util.regex.Pattern} object
	 * @return a boolean
	 */
	public boolean contains(List<Component> lore, Pattern patt) {
		return patt.matcher(Texts.e(lore.toString()).toLowerCase()).find();
	}
	/**
	 * <p>contains.</p>
	 *
	 * @param item a {@link org.bukkit.inventory.ItemStack} object
	 * @param patt a {@link java.util.regex.Pattern} object
	 * @return a boolean
	 */
	public boolean contains(ItemStack item, Pattern patt) {
		if (!ItemStackUtil.validate(item, IStatus.HAS_LORE)) return false;
		return contains(item.getItemMeta().lore(), patt);
	}

	/**
	 * <p>indexOf.</p>
	 *
	 * @param lore a {@link List} object
	 * @param patt a {@link java.util.regex.Pattern} object
	 * @return a int
	 */
	public int indexOf(List<Component> lore, Pattern patt) {
		Matcher m;

		for (int l = 0 ; l < lore.size() ; l++) {
			m = patt.matcher(Texts.e(( (TextComponent)lore.get(l) ).content().toLowerCase()));

			if (m.find()) return l;
		}

		return -1;
	}

	/**
	 * <p>indexOf.</p>
	 *
	 * @param lore a {@link List} object
	 * @param check a {@link String} object
	 * @return a int
	 */
	public int indexOf(List<String> lore, String check) {
		for (int i = 0 ; i < lore.size() ; i++) {
			if (Texts.e(lore.get(i)).startsWith(Texts.e(check)))
				return i;
		}

		return -1;
	}

	/**
	 * <p>isPercent.</p>
	 *
	 * @param lore a {@link List} object
	 * @param patt a {@link java.util.regex.Pattern} object
	 * @return a boolean
	 */
	public boolean isPercent(List<String> lore, Pattern patt) {
		matcher = patt.matcher(lore.toString().toLowerCase());
		if (matcher.find())
			if (matcher.group(1) != null)
				return matcher.group(1).contains("%");
		return false;
	}

}
