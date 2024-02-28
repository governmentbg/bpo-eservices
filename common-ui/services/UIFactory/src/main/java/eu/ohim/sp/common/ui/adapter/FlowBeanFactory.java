package eu.ohim.sp.common.ui.adapter;



public interface FlowBeanFactory<V,T> extends UIFactory<V, T> {

    



    public UIFactory<?, ?> getFactory(Class<?> clazz);

}
