package org.aslstd.api.openlib.lambda;


public interface ObjConsumer<R,V> {

	R accept(V obj);

}
