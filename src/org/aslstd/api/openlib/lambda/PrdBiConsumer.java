package org.aslstd.api.openlib.lambda;


public interface PrdBiConsumer<I,J> {

	boolean accept(I arg0, J arg1);

}
