package eu.ohim.sp.external.common;

/**
 * Created by marcoantonioalberoalbero on 30/8/16.
 */
public interface ConsumerResponse<T extends Object> {

    public T getResponse();
}