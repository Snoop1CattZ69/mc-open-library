package org.aslstd.api.bukkit.value;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>ValuePair is Pair Number representation, realised for using with floating point numbers (double, float)</p>
 * <br>Use this class for long at your risk, do not use operation methods (add, mult) if u store numbers bigger than 2⁵³
 */
@SuppressWarnings("unchecked")
@NoArgsConstructor
@Accessors(fluent = true)
public class ValuePair<T extends Number> implements Cloneable {

	@Getter @Setter private String key;

	/** For internal use only */
	@Getter @Setter private boolean percents = false;

	@Getter @Setter @NonNull private T first, second;

	public ValuePair(String key, T first, T second) {
		this.key = key ; this.first = first ; this.second = second;
	}

	public ValuePair<T> swap() {
		final T cache = first;
		first = second;
		second = cache;
		return this;
	}

	public ValuePair<T> checkSwap() {
		if (first.doubleValue() > second.doubleValue())
			return swap();
		return this;
	}

	public ValuePair<T> add(ValuePair<T> val) {
		first((T) Double.valueOf(first.doubleValue()+val.first.doubleValue()));
		second((T) Double.valueOf(second.doubleValue()+val.second.doubleValue()));
		return this;
	}

	public ValuePair<T> mult(ValuePair<Double> val) {
		first((T) Double.valueOf(first.doubleValue()*val.first.doubleValue()));
		second((T) Double.valueOf(second.doubleValue()*val.second.doubleValue()));
		return this;
	}


	public static <V extends Number> ValuePair<V> of(V first, V second) {
		return new ValuePair<>(null, first, second);
	}

	public static <V extends Number> ValuePair<V> of(String key, V first, V second) {
		return new ValuePair<>(key, first, second);
	}

	@Override
	public ValuePair<T> clone() {
		return new ValuePair<>(key, first, second);
	}

}
