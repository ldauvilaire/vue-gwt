package com.axellience.vuegwtexamples.client.examples.emitannotation;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Emit;
import com.axellience.vuegwt.core.client.component.VueComponent;
import jsinterop.annotations.JsMethod;

@Component
public class EmitAnnotationComponent extends VueComponent
{
    @Emit
    @JsMethod
    public void myEvent()
    {

    }

    @Emit
    @JsMethod
    public void myEventWithValue(int value)
    {

    }

    @Emit
    @JsMethod
    public String myEventWithValue2(int value)
    {
        return "Random Value";
    }


    @Emit("custom-event")
    @JsMethod
    public void myEventWithValueAndCustomName(int value)
    {

    }

    @JsMethod
    public void callEmitMethodFromJava()
    {
        myEventWithValue(12);
    }
}
