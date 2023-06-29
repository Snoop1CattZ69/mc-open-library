package org.aslstd.api.openlib.attributes.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.aslstd.api.bukkit.value.ValuePair;

import com.google.common.collect.ForwardingList;

public class AttrModifiable extends ForwardingList<ValuePair<Double>> {

	private static List<ValuePair<Double>> list = new ArrayList<>();

	@Override
	public boolean add(ValuePair<Double> e) {
		return list.add(e);
	}

	@Override
	public boolean addAll(final Collection<? extends ValuePair<Double>> c) {
		return list.addAll(c);
	}

	@Override
	protected List<ValuePair<Double>> delegate() {
		return list;
	}

	public ValuePair<Double> calculate() {
		final ValuePair<Double> additive = ValuePair.of(0d, 0d);
		final ValuePair<Double> multiplicative = ValuePair.of(0d, 0d);
		for(final ValuePair<Double> val : list)
			if (val.percents())
				multiplicative.add(val);
			else
				additive.add(val);

		return additive.mult(multiplicative);
	}

}
