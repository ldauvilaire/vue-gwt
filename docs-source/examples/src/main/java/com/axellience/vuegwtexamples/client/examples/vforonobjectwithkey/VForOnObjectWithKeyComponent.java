package com.axellience.vuegwtexamples.client.examples.vforonobjectwithkey;

import com.axellience.vuegwt.core.client.component.VueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasCreated;
import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwtexamples.client.examples.common.Todo;
import jsinterop.annotations.JsProperty;
import jsinterop.base.JsPropertyMap;

/**
 * @author Adrien Baron
 */
@Component
public class VForOnObjectWithKeyComponent extends VueComponent implements HasCreated
{
    @JsProperty JsPropertyMap<Object> myObject;

    @Override
    public void created()
    {
        this.myObject = JsPropertyMap.of();
        this.myObject.set("myString", "Hello World");
        this.myObject.set("myInt", 12);
        this.myObject.set("myTodo", new Todo("I'm a Todo"));
    }
}
