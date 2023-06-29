package org.aslstd.api.openlib.player;

import org.aslstd.api.bukkit.settings.Settings;
import org.aslstd.api.bukkit.settings.impl.StringSettings;
import org.aslstd.api.bukkit.value.ValuePair;
import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public final class Options {

	@Getter private Settings<Double> tempStore = new Settings<>();
	@Getter private StringSettings tempData = new StringSettings();
	@Getter private StringSettings data = new StringSettings();

	private OPlayer player;

	private String scaleModifier = "level";

	public Options() {}

	protected Options(@NotNull OPlayer player) {
		this.player = player;
		this.data.importYaml(player.dataFile());
	}

	protected void save() {
		if (player != null)
			this.data.exportYaml(player.dataFile(), null);
	}

	public boolean checkData(String key) {
		return data.hasKey(key);
	}

	public boolean checkTemp(String key) {
		return tempData.hasKey(key);
	}

	public boolean checkValue(String key) {
		return tempStore.hasKey(key);
	}

	public Options linkScaleMod(String key) {
		this.scaleModifier = key; return this;
	}

	public Options writeData(String key, String data) {
		this.data.setValue(key, data); return this;
	}

	public Options writeTemp(String key, String data) {
		this.tempData.setValue(key, data); return this;
	}

	public Options writeValue(String key, double val) {
		this.tempStore.setValue(key, val); return this;
	}

	public Options writeScaleMod(double mod) {
		return writeValue(scaleModifier, mod);
	}

	public Options writeBase(String key, double base) {
		return writeValue(key + ".base", base);
	}

	public Options writeScale(String key, double scale) {
		return writeValue(key + ".scale", scale);
	}

	public Options writeValue(String key, double base, double scale) {
		writeValue(key + ".base", base);
		return writeValue(key + ".scale", scale);
	}

	public Options writeRange(String key, ValuePair<Double> range) {
		range.checkSwap();

		writeValue(key + ".first", range.first());
		return writeValue(key + ".second", range.second());
	}

	public Options writeRange(String key, double first, double second) {
		return writeRange(key, ValuePair.of(first, second));
	}

	public String readData(String key) {
		return data.getValue(key);
	}

	public String readTemp(String key) {
		return tempData.getValue(key);
	}

	public double readValue(String key) {
		return tempStore.getValue(key);
	}

	public double readScaleMod() {
		return readValue(scaleModifier);
	}

	public double readBase(String key) {
		return readValue(key + ".base");
	}

	public double readScale(String key) {
		return readValue(key + ".scale");
	}

	public double readNScale(String key) {
		return readBase(key) + (readScale(key) * readScaleMod());
	}

	public double readFRange(String key) {
		return readValue(key + ".first");
	}

	public double readSRange(String key) {
		return readValue(key + ".second");
	}

	public ValuePair<Double> readRange(String key) {
		return ValuePair.of(readFRange(key), readSRange(key));
	}

	public String eraseDataKey(String key) {
		return data.remove(key);
	}

	public void eraseDataSection(String section) {
		data.removeKey(section);
	}

	public String eraseTempKey(String key) {
		return tempData.remove(key);
	}

	public void eraseTempSections(String section) {
		tempData.removeKey(section);
	}

	public double eraseValue(String key) {
		return tempStore.remove(key);
	}

	public ValuePair<Double> eraseNScale(String key) {
		return ValuePair.of(key, eraseValue(key + ".base"), eraseValue(key + ".scale"));
	}

	public ValuePair<Double> eraseRange(String key) {
		return ValuePair.of(key, eraseValue(key + ".first"), eraseValue(key + ".second"));
	}

	public void eraseValues(String section) {
		tempStore.removeKey(section);
	}

}
