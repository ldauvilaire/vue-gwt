package com.axellience.vuegwtexamples.client.examples.extendsjscomponent.parent;

import com.axellience.vuegwt.core.client.component.VueComponent;
import com.axellience.vuegwt.core.annotations.component.Computed;
import com.axellience.vuegwt.core.annotations.component.JsComponent;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

/**
 * @author Adrien Baron
 */
@JsComponent("ParentJsComponent")
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Function")
public class ParentJsComponent extends VueComponent
{
    public String parentMessage;

    public native int parentMultiplyBy2(int value);

    @Computed
    public native String getParentComputed();
}
