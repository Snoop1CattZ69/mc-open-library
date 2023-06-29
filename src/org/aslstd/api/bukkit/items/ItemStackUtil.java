package org.aslstd.api.bukkit.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aslstd.api.bukkit.message.Texts;
import org.aslstd.api.bukkit.utils.BasicMetaAdapter;
import org.aslstd.api.bukkit.value.util.NumUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Nullable;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

// ISUv1
/**
 * <p>ItemStackUtil class.</p>
 *
 * @author Snoop1CattZ69
 */
public final class ItemStackUtil {

	private static HashMap<String,ItemStack> itemsCache = new HashMap<>();

	public static boolean compareData(ItemStack i1, ItemStack i2) {
		return ItemStackUtil.toString(i1).split(":")[1].equalsIgnoreCase(ItemStackUtil.toString(i2).split(":")[1]);
	}

	public static boolean compareDisplayName(ItemStack i1, ItemStack i2) {
		return ItemStackUtil.getDisplayName(i1).equals(ItemStackUtil.getDisplayName(i2));
	}

	public static boolean isEquals(ItemStack stack, ItemStack eq) {
		ItemStack one, sec;

		if (stack == null)
			one = new ItemStack(Material.AIR);
		else {
			one = stack.clone();
			if (one.getAmount() > 1) one.setAmount(1);
		}

		if (eq == null)
			sec = new ItemStack(Material.AIR);
		else {
			sec = eq.clone();
			if (sec.getAmount() > 1) sec.setAmount(1);
		}

		return ItemStackUtil.serialize(one).equals(ItemStackUtil.serialize(sec));
	}

	public static ItemStack compileStack(String stack) {
		if (stack.toUpperCase().startsWith("SKULL")) {
			return toSkull(stack);
		} else
			return toStack(stack);
	}

	public static ItemStack toSkull(String stack) {
		final String[] params = stack.split("@");

		if (params.length < 2)
			Texts.warn("&4Need to set skull owner: &5" + stack + "&4 template: SKULL@<skullUid/base64>@DisplayName@Lore;Lore;Lore");

		ItemStack skull = SkullCreator.itemFromBase64(params[1]);

		if (params.length >= 3)
			skull = BasicMetaAdapter.setDisplayName(skull, params[2]);

		if (params.length >= 4)
			skull = BasicMetaAdapter.addLore(skull, params[3].split(";"));

		return skull;
	}

	public static ItemStack toStack(String str) {
		return ItemStackUtil.deserialize(str);
	}

	public static String toString(ItemStack stack) {
		return ItemStackUtil.serialize(stack);
	}

	public static Component getDisplayName(ItemStack stack) {
		if (stack == null) return Component.text("null");
		if (!ItemStackUtil.validate(stack, IStatus.HAS_META)) return Component.text(stack.getI18NDisplayName());
		if (ItemStackUtil.validate(stack, IStatus.HAS_DISPLAYNAME))
			return stack.getItemMeta().displayName();
		return Component.text(stack.getI18NDisplayName());
	}

	public static List<Component> getLore(ItemStack stack) {
		if (!ItemStackUtil.validate(stack, IStatus.HAS_LORE)) return Arrays.asList(Component.empty());
		return stack.getItemMeta().lore();
	}

	public static ItemStack setDamage(ItemStack stack, int damage) {
		if (ItemStackUtil.hasDurability(stack.getType())) {

			final ItemMeta itemMeta = stack.getItemMeta();

			((Damageable) itemMeta).setDamage(damage);

			stack.setItemMeta(itemMeta);
			return stack;
		}
		return stack;
	}

	public static ItemStack setFlags(ItemStack stack, ItemFlag... flags) {
		if (ItemStackUtil.validate(stack, IStatus.HAS_MATERIAL)) {
			final ItemMeta meta = stack.getItemMeta();
			meta.addItemFlags(flags);
			stack.setItemMeta(meta);
		}
		return stack;
	}

	public static ItemStack removeFlags(ItemStack stack, ItemFlag... flags) {
		if (ItemStackUtil.validate(stack,IStatus.HAS_MATERIAL)) {
			final ItemMeta meta = stack.getItemMeta();
			for (final ItemFlag flag : flags)
				if (meta.hasItemFlag(flag))
					meta.removeItemFlags(flag);
		}
		return stack;
	}

	public static int getDamage(ItemStack stack) {
		if (ItemStackUtil.hasDurability(stack.getType()))
			return ((Damageable)stack.getItemMeta()).getDamage();
		return 0;
	}

	public static ItemFlag getFlagByName(String key) {
		for (final ItemFlag flag : ItemFlag.values())
			if (flag.name().equalsIgnoreCase(key)) return flag;
		return null;
	}

	public static void incrementDamage(ItemStack stack) { ItemStackUtil.setDamage(stack,ItemStackUtil.getDamage(stack)+1); }
	public static void incrementDamage(ItemStack stack, int value) { ItemStackUtil.setDamage(stack,ItemStackUtil.getDamage(stack)+value); }

	public static void decrementDamage(ItemStack stack) { ItemStackUtil.setDamage(stack,ItemStackUtil.getDamage(stack)-1); }
	public static void decrementDamage(ItemStack stack, int value) { ItemStackUtil.setDamage(stack,ItemStackUtil.getDamage(stack)-value); }

	public static boolean validate(ItemStack stack, IStatus status) {
		if ((stack == null) || (stack.getType() == Material.AIR))
			return false;
		if (status == IStatus.HAS_MATERIAL) return true;

		if (!stack.hasItemMeta())
			return false;
		if (status == IStatus.HAS_META) return true;

		switch (status) {
			case HAS_DISPLAYNAME:
				return stack.getItemMeta().hasDisplayName();
			case HAS_DURABILITY:
				return ItemStackUtil.hasDurability(stack.getType());
			case HAS_ENCHANTS:
				return stack.getItemMeta().hasEnchants();
			case HAS_LORE:
				return stack.getItemMeta().hasLore();
			case IS_UNBREAKABLE:
				return stack.getItemMeta().isUnbreakable();
			default:
				return false;
		}
	}

	public static ItemStack setCustomModelData(ItemStack stack, int data) {
		if (!ItemStackUtil.validate(stack, IStatus.HAS_MATERIAL)) return stack;
		final ItemMeta meta = stack.getItemMeta();

		meta.setCustomModelData(data);

		stack.setItemMeta(meta);
		return stack;
	}

	public static ItemStack setUnbreakable(ItemStack stack, boolean arg) {
		if (!ItemStackUtil.validate(stack, IStatus.HAS_MATERIAL)) return stack;
		final ItemMeta meta = stack.getItemMeta();

		meta.setUnbreakable(arg);

		stack.setItemMeta(meta);

		return stack;
	}

	public static boolean isHelmet		(Material mat) { return mat.name().contains("HELMET"); 		}
	public static boolean isChestplate	(Material mat) { return mat.name().contains("CHESTPLATE"); 	}
	public static boolean isLeggings	(Material mat) { return mat.name().contains("LEGGINGS");	}
	public static boolean isBoots		(Material mat) { return mat.name().contains("BOOTS"); 		}
	public static boolean isPickaxe		(Material mat) { return mat.name().contains("PICKAXE"); 	}
	public static boolean isAxe			(Material mat) { return mat.name().contains("_AXE"); 		}
	public static boolean isHoe			(Material mat) { return mat.name().contains("HOE"); 		}
	public static boolean isSpade		(Material mat) { return (mat.name().contains("SPADE") || mat.name().contains("SHOVEL")); }
	public static boolean isFishingRod	(Material mat) { return mat.name().contains("FISHING"); 	}
	public static boolean isFlintSteel	(Material mat) { return mat.name().contains("_STEEL"); 		}
	public static boolean isCarrotRod	(Material mat) { return mat.name().contains("CARROT_ON"); 	}
	public static boolean isRanged		(Material mat) { return mat.name().contains("BOW"); 		}
	public static boolean isElytra		(Material mat) { return mat.name().contains("ELYTRA"); 		}
	public static boolean isShield		(Material mat) { return mat.name().contains("SHIELD"); 		}
	public static boolean isSword		(Material mat) { return mat.name().contains("SWORD"); 		}

	public static boolean isArmor(Material mat) {
		return ItemStackUtil.isBoots(mat) || ItemStackUtil.isHelmet(mat) || ItemStackUtil.isChestplate(mat) || ItemStackUtil.isLeggings(mat);
	}

	public static boolean isTool(Material mat) {
		return ItemStackUtil.isHoe(mat) || ItemStackUtil.isSpade(mat) || ItemStackUtil.isAxe(mat) || ItemStackUtil.isPickaxe(mat);
	}

	public static boolean isAnotherTool(Material mat) {
		return ItemStackUtil.isCarrotRod(mat) || ItemStackUtil.isFlintSteel(mat) || ItemStackUtil.isFishingRod(mat);
	}

	public static boolean isWeapon(Material mat) {
		return ItemStackUtil.isSword(mat) || ItemStackUtil.isRanged(mat);
	}

	public static boolean hasDurability(Material mat) {
		return ItemStackUtil.isWeapon(mat) || ItemStackUtil.isArmor(mat) || ItemStackUtil.isAnotherTool(mat) || ItemStackUtil.isTool(mat);
	}

	private static ItemStack deserialize(String hash) {
		if (hash.equalsIgnoreCase("AIR:-1:0") || (Material.getMaterial(hash.split("&")[0].split(":")[0]) == null)) return new ItemStack(Material.AIR, 0);
		if (ItemStackUtil.itemsCache.containsKey(hash))
			return ItemStackUtil.itemsCache.get(hash);
		final String[] params = new String[5];

		StringBuffer buff = new StringBuffer();
		int id = 0;
		int idn = 0;

		final char[] charSet = hash.toCharArray();

		for (final char ch : charSet) {
			if (ch == '♥')
				idn = 1;
			if (ch == '♦')
				idn = 2;
			if (ch == '♣')
				idn = 3;
			if (ch == '♠')
				idn = 4;

			if (id != idn) {
				if (params[id] == null)
					params[id] = buff.toString();
				if (params[idn] == null)
					id = idn;
				buff = new StringBuffer();
				continue;
			}

			buff.append(ch);
		}
		params[id] = buff.toString();

		ItemStack item = new ItemStack(Material.AIR, 0);
		final int length = params.length;
		if (length == 0) {
			final String[] opt0 = hash.split(":");
			final Material mat = Material.getMaterial(opt0[0]);
			int amount = 1;
			int durability = 0;
			try {
				durability = NumUtil.parseInteger(opt0[2]);
				amount = NumUtil.parseInteger(opt0[1]);
				item = new ItemStack(mat, amount);
				ItemStackUtil.setDamage(item,durability);
			} catch (final NumberFormatException e) {
				item = new ItemStack(mat, amount);
				ItemStackUtil.setDamage(item,durability);
			}
			return item;
		}
		if (params[0] != null) {// Material:Amount:Durability:CustomModelData
			final String[] opt0 = params[0].split(":");
			final Material mat = Material.getMaterial(opt0[0]);
			int amount = 1;
			int durability = 0;
			int custommodeldata = 0;
			if (opt0.length > 1)
				try { amount = NumUtil.parseInteger(opt0[1]); } catch (final NumberFormatException e) { }
			if (opt0.length > 2)
				try { durability = NumUtil.parseInteger(opt0[2]); } catch (final NumberFormatException e) { }
			if (opt0.length > 3)
				try { custommodeldata = NumUtil.parseInteger(opt0[3]); } catch (final NumberFormatException e) { }

			item = setDamage(new ItemStack(mat, amount), durability);
			item = custommodeldata <= 0 ? item : setCustomModelData(item, custommodeldata);
		}
		if (item == null)
			return new ItemStack(Material.AIR, 0);
		ItemMeta meta = item.getItemMeta();
		if (params[1] != null) meta.displayName(Component.text(Texts.c(params[1])));
		if (params[2] != null) {// ♦Lore◘Lore◘Lore
			final String[] opt2 = params[2].split("◘");
			final List<Component> lore = new ArrayList<>();
			for (final String str : opt2)
				lore.add(Component.text(Texts.c(str)));
			meta.lore(lore);
		}
		if (params[3] != null) {// ♣Enchant:Level;Enchant:Level
			final String[] opt3 = params[3].split(";");
			for (final String ench : opt3) {
				final String[] splited = ench.split(":");
				if (Enchantment.getByKey(NamespacedKey.minecraft(splited[0])) == null) continue;
				if (splited.length == 2) try {
					meta.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(splited[0])), NumUtil.parseInteger(splited[1]), true);
				} catch (final NumberFormatException e) {
					meta.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(splited[0])), 1, true);
				}
				else meta.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(splited[0])), 1, true);
			}
		}
		if (params[4] != null) {// ♠ItemFlag;ItemFlag
			final String[] opt4 = params[4].split(";");
			for (final String flag : opt4) {
				if (flag.equalsIgnoreCase("unbreakable")) {
					item.setItemMeta(meta);
					item = ItemStackUtil.setUnbreakable(item, true);
					meta = item.getItemMeta();
					continue;
				}

				try {
					meta.addItemFlags(ItemFlag.valueOf(flag));
				} catch (final IllegalArgumentException e) {
					continue;
				}
			}
		}
		item.setItemMeta(meta);
		ItemStackUtil.itemsCache.put(hash,item);
		return item;
	}

	private static String serialize(ItemStack stack) {
		final StringBuilder hash = new StringBuilder().append(stack == null ? "AIR:0:0" : stack.getType().toString() + ":");
		if (stack != null) {
			final int typeHash = ItemStackUtil.getDamage(stack);
			hash.append(stack.getAmount()).append(":").append(typeHash > 0 ? typeHash : 0);
			if (stack.hasItemMeta()) {
				final ItemMeta meta = stack.getItemMeta();
				if (meta.hasDisplayName()) hash.append("♥").append(( (@NonNull TextComponent)meta.displayName() ).content().replace('§', '&'));
				boolean first = false;
				if (meta.hasLore()) {
					final @Nullable List<Component> lore = meta.lore();
					if (lore.size() > 0) {
						hash.append("♦");
						for (final Component str : lore)
							if (!first) { hash.append(( (TextComponent)str ).content().replace('§', '&')); first = true; }
							else hash.append("◘").append(( (TextComponent)str ).content().replace('§', '&'));
					}
				}
				first = false;
				if (meta.hasEnchants()) {
					final Map<Enchantment, Integer> enchants = meta.getEnchants();
					if (enchants.size() > 0) {
						hash.append("♣");
						for (final Enchantment e : enchants.keySet()) {
							if (e == null) continue;
							if (!first) { hash.append(e.getKey().toString()).append(":").append(enchants.get(e)); first = true; }
							else hash.append(";").append(e.getKey().toString()).append(":").append(enchants.get(e));
						}
					}
				}
				first = false;
				if (meta.getItemFlags() != null) {
					final Set<ItemFlag> flags = meta.getItemFlags();
					if (flags.size() > 0) {
						hash.append("♠");
						for (final ItemFlag flag : flags)
							if (!first) { hash.append(flag.toString()); first = true; }
							else hash.append(";").append(flag.toString());
					}
					if (meta.isUnbreakable())
						hash.append(";unbreakable");
				}
				first = false;
			}
		}
		return hash.toString();
	}

	/**
	 * <p>decreaseAmount.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 */
	public void decreaseAmount(ItemStack stack) {
		stack.setAmount(stack.getAmount() - 1);
	}

}
