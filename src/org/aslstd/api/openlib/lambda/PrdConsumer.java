package org.aslstd.api.openlib.lambda;


public interface PrdConsumer<V> {

	boolean accept(V object);

}
