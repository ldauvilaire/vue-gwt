package com.axellience.vuegwt.client.jsnative;

import com.axellience.vuegwt.client.VueComponent;
import com.axellience.vuegwt.client.VueComponentFactory;
import com.axellience.vuegwt.client.VueDirective;
import com.axellience.vuegwt.client.definitions.VueComponentDefinition;
import com.axellience.vuegwt.client.definitions.VueDirectiveDefinition;
import com.axellience.vuegwt.client.jsnative.types.JsObject;
import com.axellience.vuegwt.client.jsnative.vue.VueConfig;
import com.google.gwt.dom.client.Element;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

import static com.axellience.vuegwt.client.definitions.VueDefinitionCache.getComponentDefinitionForClass;
import static com.axellience.vuegwt.client.definitions.VueDefinitionCache.getDirectiveDefinitionForClass;

/**
 * JsInterop representation of the main Vue instance
 * Provide some methods that are specific to VueGWT
 * @author Adrien Baron
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Vue
{
    public static VueConfig config;

    /**
     * Create a VueComponent instance and attach it to a DOM element
     * Equivalent to new Vue({el: element, ...}) in Vue.js
     * @param vueComponentClass The class of the Component to create
     */
    @JsOverlay
    public static <T extends VueComponent> T attach(String element, Class<T> vueComponentClass)
    {
        VueComponentDefinition componentDefinition =
            getComponentDefinitionForClass(vueComponentClass);
        componentDefinition.setEl(element);

        return VueGwtTools.createVueInstance(componentDefinition);
    }

    /**
     * Create a VueComponent instance and attach it to a DOM element
     * Equivalent to new Vue({el: element, ...}) in Vue.js
     * @param vueComponentClass The class of the Component to create
     */
    @JsOverlay
    public static <T extends VueComponent> T attach(Element element, Class<T> vueComponentClass)
    {
        VueComponentDefinition componentDefinition =
            getComponentDefinitionForClass(vueComponentClass);
        componentDefinition.setEl(element);

        return VueGwtTools.createVueInstance(componentDefinition);
    }

    /**
     * Extend the base Vue Class with your VueComponent definition
     * Equivalent to Vue.extend({}) in Vue.js
     * @param vueComponentClass The class of the Component to use
     * @return A factory that can be used to create instance of your VueComponent
     */
    @JsOverlay
    public static <T extends VueComponent> VueComponentFactory<T> extend(Class<T> vueComponentClass)
    {
        JsObject extendedVueClass = extend(getComponentDefinitionForClass(vueComponentClass));
        return new VueComponentFactory<>(extendedVueClass);
    }

    /**
     * Register a component globally
     * It will be usable in any component of your app.
     * The name will be automatically computed based on the component class name
     * @param vueComponentClass The class of the Component to register
     */
    @JsOverlay
    public static void component(Class<? extends VueComponent> vueComponentClass)
    {
        Vue.component(VueGwtTools.componentToTagName(vueComponentClass), vueComponentClass);
    }

    /**
     * Register a component globally
     * It will be usable in any component of your app.
     * @param id Register under the given id
     * @param vueComponentClass The class of the Component to
     */
    @JsOverlay
    public static void component(String id, Class<? extends VueComponent> vueComponentClass)
    {
        Vue.component(id, getComponentDefinitionForClass(vueComponentClass));
    }

    /**
     * Return the factory for a given registered component ID
     */
    @JsOverlay
    public static VueComponentFactory component(String id)
    {
        JsObject extendedVueClass = getRegisteredComponent(id);
        return new VueComponentFactory(extendedVueClass);
    }

    /**
     * Register a directive globally
     * It will be usable in any component of your app.
     * The name will be automatically computed based on the directive class name
     * @param vueDirectiveClass The class of the Directive to register
     */
    @JsOverlay
    public static void directive(Class<? extends VueDirective> vueDirectiveClass)
    {
        Vue.directive(VueGwtTools.directiveToTagName(vueDirectiveClass), vueDirectiveClass);
    }

    /**
     * Register a directive globally
     * It will be usable in any component of your app.
     * @param name Register under the given name
     * @param vueDirectiveClass The class of the Directive to register
     */
    @JsOverlay
    public static void directive(String name, Class<? extends VueDirective> vueDirectiveClass)
    {
        Vue.directive(name, getDirectiveDefinitionForClass(vueDirectiveClass));
    }

    private static native void component(String id, VueComponentDefinition componentDefinition);

    private static native void directive(String name, VueDirectiveDefinition directiveDefinition);

    private static native JsObject extend(VueComponentDefinition componentDefinition);

    @JsMethod(name = "component")
    private static native JsObject getRegisteredComponent(String id);
}
