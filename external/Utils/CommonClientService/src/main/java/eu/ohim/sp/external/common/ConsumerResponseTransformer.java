package eu.ohim.sp.external.common;

/**
 * Created by marcoantonioalberoalbero on 30/8/16.
 */
public interface ConsumerResponseTransformer <T1 extends Object, T2 extends Object> {

    public T1 transform(T2 inputFormat);

}