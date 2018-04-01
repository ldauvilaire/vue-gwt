package com.axellience.vuegwt.core.client;

import com.axellience.vuegwt.core.client.component.ComponentExposedTypeConstructorFn;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.observer.VueGWTObserverManager;
import com.axellience.vuegwt.core.client.observer.vuegwtobservers.CollectionObserver;
import com.axellience.vuegwt.core.client.observer.vuegwtobservers.MapObserver;
import com.axellience.vuegwt.core.client.tools.VueGWTTools;
import com.axellience.vuegwt.core.client.vue.VueComponentFactory;
import com.axellience.vuegwt.core.client.vue.VueJsConstructor;
import elemental2.core.JsObject;
import elemental2.dom.DomGlobal;
import jsinterop.annotations.JsMethod;
import jsinterop.base.JsPropertyMap;

import java.util.LinkedList;

/**
 * @author Adrien Baron
 */
public class VueGWT
{
    private static boolean isReady = false;
    private static LinkedList<Runnable> onReadyCallbacks = new LinkedList<>();

    /**
     * Inject scripts necessary for Vue GWT to work.
     * Also inject Vue.js library.
     */
    public static void init()
    {
        if (isDevMode())
            VueLibDevInjector.ensureInjected();
        else
            VueLibInjector.ensureInjected();

        // Init VueGWT
        VueGWT.initWithoutVueLib();
    }

    private static boolean isDevMode()
    {
        return "on".equals(System.getProperty("superdevmode", "off")) || "development".equals(System
            .getProperty("vuegwt.environment", "production"));
    }

    /**
     * Inject scripts necessary for Vue GWT to work
     * Requires Vue to be defined in Window.
     */
    public static void initWithoutVueLib()
    {
        if (!isVueLibInjected())
            throw new RuntimeException(
                "Couldn't find Vue.js on init. Either include it Vue.js in your index.html or call VueGWT.init() instead of initWithoutVueLib.");

        // Register custom observers for Collection and Maps
        VueGWTObserverManager.get().registerVueGWTObserver(new CollectionObserver());
        VueGWTObserverManager.get().registerVueGWTObserver(new MapObserver());

        isReady = true;

        // Call on ready callbacks
        for (Runnable onReadyCbk : onReadyCallbacks)
            onReadyCbk.run();
        onReadyCallbacks.clear();
    }

    /**
     * Create a {@link Vue} instance for the given Vue Component Class.
     * You can then call $mount on it to mount the instance.
     * @param isVueComponentClass The Class of the Component to create
     * @param <T> The type of the {@link IsVueComponent}
     * @return The created instance of our Component (not yet mounted)
     */
    public static <T extends IsVueComponent> T createInstance(Class<T> isVueComponentClass)
    {
        return getVueComponentFactory(isVueComponentClass).create();
    }

    /**
     * Return the {@link VueComponentFactory} for the given {@link IsVueComponent} class.
     * @param isVueComponentClass The {@link IsVueComponent} class
     * @param <T> The type of the {@link IsVueComponent}
     * @return A {@link VueComponentFactory} you can use to instantiate components
     */
    public static <T extends IsVueComponent> VueComponentFactory<T> getVueComponentFactory(Class<T> isVueComponentClass)
    {
        if (JsObject.class.equals(isVueComponentClass))
        {
            throw new RuntimeException(
                "You can't use the .class of a JsComponent to instantiate it. Please use MyComponentFactory.get() instead.");
        }
        return getVueComponentFactory(isVueComponentClass.getCanonicalName());
    }

    /**
     * Return the {@link VueComponentFactory} for the given {@link IsVueComponent} fully qualified name.
     * @param componentQualifiedName The fully qualified name of the {@link IsVueComponent} class
     * @param <T> The type of the {@link IsVueComponent}
     * @return A {@link VueComponentFactory} you can use to instantiate components
     */
    @JsMethod(namespace = "VueGWT")
    public static <T extends IsVueComponent> VueComponentFactory<T> getVueComponentFactory(String componentQualifiedName)
    {
        ComponentExposedTypeConstructorFn<T> javaConstructor =
            getComponentExposedTypeConstructorFn(componentQualifiedName);
        if (javaConstructor != null)
            return javaConstructor.getVueComponentFactory();

        throw new RuntimeException("Couldn't find VueComponentFactory for Component: "
            + componentQualifiedName
            + ". Make sure that annotation are being processed, and that you added the -generateJsInteropExports flag to GWT. You can also try a \"mvn clean\" on your maven project.");
    }

    /**
     * Return the {@link VueJsConstructor} for the given {@link IsVueComponent} class.
     * @param isVueComponentClass The {@link IsVueComponent} class
     * @param <T> The type of the {@link IsVueComponent}
     * @return A {@link VueJsConstructor} you can use to instantiate components
     */
    public static <T extends IsVueComponent> VueJsConstructor<T> getJsConstructor(
        Class<T> isVueComponentClass)
    {
        return getVueComponentFactory(isVueComponentClass).getJsConstructor();
    }

    /**
     * Return the {@link VueJsConstructor} for the given {@link IsVueComponent} fully qualified name.
     * @param qualifiedName The fully qualified name of the {@link IsVueComponent} class
     * @param <T> The type of the {@link IsVueComponent}
     * @return A {@link VueJsConstructor} you can use to instantiate components
     */
    @JsMethod(namespace = "VueGWT")
    public static <T extends IsVueComponent> VueJsConstructor<T> getJsConstructor(
        String qualifiedName)
    {
        return (VueJsConstructor<T>) getVueComponentFactory(qualifiedName).getJsConstructor();
    }

    /**
     * Return the Java Constructor of our {@link IsVueComponent} Java Class.
     * This Constructor can be used to get the prototype of our Java Class and get the
     * VueComponent methods from it.
     * @param isVueComponentClass The {@link IsVueComponent} we want the constructor of
     * @param <T> The type of the {@link IsVueComponent}
     * @return The Java constructor of our {@link IsVueComponent}
     */
    public static <T extends IsVueComponent> ComponentExposedTypeConstructorFn<T> getComponentExposedTypeConstructorFn(
        Class<T> isVueComponentClass)
    {
        return getComponentExposedTypeConstructorFn(isVueComponentClass.getCanonicalName());
    }

    /**
     * Return the Java Constructor of our {@link IsVueComponent} ExposedType Java Class.
     * This Constructor can be used to get the prototype of our Java Class and get the
     * VueComponent methods from it.
     * @param componentQualifiedName The fully qualified name of the {@link IsVueComponent} class
     * @return The Java constructor of our {@link IsVueComponent}
     */
    @JsMethod(namespace = "VueGWT")
    public static <T extends IsVueComponent> ComponentExposedTypeConstructorFn<T> getComponentExposedTypeConstructorFn(
        String componentQualifiedName)
    {
        return VueGWTTools.getDeepValue(DomGlobal.window,
            "VueGWTComponents." + componentQualifiedName + ".ExposedTypeConstructorFn");
    }

    /**
     * Ask to be warned when Vue GWT is ready.
     * If Vue GWT is ready, the callback is called immediately.
     * @param callback The callback to call when Vue GWT is ready.
     */
    public static void onReady(Runnable callback)
    {
        if (isReady)
        {
            callback.run();
            return;
        }

        onReadyCallbacks.push(callback);
    }

    static boolean isVueLibInjected()
    {
        return ((JsPropertyMap) DomGlobal.window).get("Vue") != null;
    }
}
