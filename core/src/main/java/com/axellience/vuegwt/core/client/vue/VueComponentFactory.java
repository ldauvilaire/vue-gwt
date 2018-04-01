package com.axellience.vuegwt.core.client.vue;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.JsComponent;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import jsinterop.annotations.JsMethod;

/**
 * A factory to create {@link IsVueComponent}s.
 * All the {@link Component} and {@link JsComponent} get a generated {@link VueComponentFactory}.
 * It wraps a {@link VueJsConstructor} that is configured when the factory is created.
 * It can be injected with Gin or Dagger2.
 * @author Adrien Baron
 */
public class VueComponentFactory<T extends IsVueComponent>
{
    protected VueJsConstructor<T> jsConstructor;

    @JsMethod
    public final T create()
    {
        return jsConstructor.instantiate();
    }

    @JsMethod
    public VueJsConstructor<T> getJsConstructor()
    {
        return jsConstructor;
    }
}
