package com.axellience.vuegwt.core.client.vnode.builder;

import com.axellience.vuegwt.core.client.VueGWT;
import com.axellience.vuegwt.core.client.component.VueComponent;
import com.axellience.vuegwt.core.client.vnode.VNode;
import com.axellience.vuegwt.core.client.vnode.VNodeData;
import com.axellience.vuegwt.core.client.vue.VueFactory;
import com.axellience.vuegwt.core.client.vue.VueJsConstructor;

/**
 * @author Adrien Baron
 */
public class VNodeBuilder
{
    private final CreateElementFunction function;

    public VNodeBuilder(CreateElementFunction function)
    {
        this.function = function;
    }

    /**
     * Create an empty VNode
     * @return a new empty VNode
     */
    public VNode el()
    {
        return this.function.create(null, null, null);
    }

    /**
     * Create a VNode with the given HTML tag
     * @param tag HTML tag for the new VNode
     * @param children Children
     * @return a new VNode of this tag
     */
    public VNode el(String tag, Object... children)
    {
        return this.function.create(tag, children, null);
    }

    /**
     * Create a VNode with the given HTML tag
     * @param tag HTML tag for the new VNode
     * @param data Information for the new VNode (attributes...)
     * @param children Children
     * @return a new VNode of this tag
     */
    public VNode el(String tag, VNodeData data, Object... children)
    {
        return this.function.create(tag, data, children);
    }

    /**
     * Create a VNode with the given {@link VueComponent}
     * @param vueComponentClass Class for the {@link VueComponent} we want
     * @param children Children
     * @param <T> The type of the {@link VueComponent}
     * @return a new VNode of this Component
     */
    public <T extends VueComponent> VNode el(Class<T> vueComponentClass, Object... children)
    {
        return el(VueGWT.getJsConstructor(vueComponentClass), children);
    }

    /**
     * Create a VNode with the given {@link VueComponent}
     * @param vueComponentClass Class for the {@link VueComponent} we want
     * @param data Information for the new VNode (attributes...)
     * @param children Children
     * @param <T> The type of the {@link VueComponent}
     * @return a new VNode of this Component
     */
    public <T extends VueComponent> VNode el(Class<T> vueComponentClass, VNodeData data, Object... children)
    {
        return el(VueGWT.getJsConstructor(vueComponentClass), data, children);
    }

    /**
     * Create a VNode with the {@link VueComponent} of the given {@link VueFactory}
     * @param vueFactory {@link VueFactory} for the Component we want
     * @param children Children
     * @param <T> The type of the {@link VueComponent}
     * @return a new VNode of this Component
     */
    public <T extends VueComponent> VNode el(VueFactory<T> vueFactory, Object... children)
    {
        return el(vueFactory.getJsConstructor(), children, null);
    }

    /**
     * Create a VNode with the {@link VueComponent} of the given {@link VueFactory}
     * @param vueFactory {@link VueFactory} for the Component we want
     * @param data Information for the new VNode (attributes...)
     * @param children Children
     * @param <T> The type of the {@link VueComponent}
     * @return a new VNode of this Component
     */
    public <T extends VueComponent> VNode el(VueFactory<T> vueFactory, VNodeData data, Object... children)
    {
        return el(vueFactory.getJsConstructor(), data, children);
    }

    /**
     * Create a VNode with the {@link VueComponent} of the given {@link VueJsConstructor}
     * @param vueJsConstructor {@link VueJsConstructor} for the Component we want
     * @param children Children
     * @param <T> The type of the {@link VueComponent}
     * @return a new VNode of this Component
     */
    public <T extends VueComponent> VNode el(VueJsConstructor<T> vueJsConstructor, Object... children)
    {
        return el(vueJsConstructor, null, children);
    }

    /**
     * Create a VNode with the {@link VueComponent} of the given {@link VueJsConstructor}
     * @param vueJsConstructor {@link VueJsConstructor} for the Component we want
     * @param data Information for the new VNode (attributes...)
     * @param children Children
     * @param <T> The type of the {@link VueComponent}
     * @return a new VNode of this Component
     */
    public <T extends VueComponent> VNode el(VueJsConstructor<T> vueJsConstructor, VNodeData data, Object... children)
    {
        return this.function.create(vueJsConstructor, data, children);
    }
}
