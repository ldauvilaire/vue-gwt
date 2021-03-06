package com.axellience.vuegwtexamples.client.examples.gotquotes;

import com.axellience.vuegwt.core.client.component.VueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasCreated;
import com.axellience.vuegwt.core.annotations.component.Component;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;

import javax.inject.Inject;

@Component
public class GotQuotesComponent extends VueComponent implements HasCreated
{
    @JsProperty
    GotQuote quote;

    @Inject
    GotQuotesService gotQuotesService;

    @Override
    public void created()
    {
        changeQuote();
    }

    @JsMethod
    protected void changeQuote()
    {
        quote = gotQuotesService.getRandomQuote();
    }
}
