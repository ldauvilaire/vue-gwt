package com.axellience.vuegwt.gwt2.template.parser.exceptions;

import com.axellience.vuegwt.gwt2.template.parser.context.TemplateParserContext;

/**
 * An exception thrown when an Expression is invalid in the template.
 * @author Adrien Baron
 */
public class TemplateExpressionException extends TemplateParserException
{
    public TemplateExpressionException(String message, String expression,
        TemplateParserContext context)
    {
        this(message, expression, context, null);
    }

    public TemplateExpressionException(String message, String expression,
        TemplateParserContext context, Throwable cause)
    {
        super(message + "\n\nIn expression: " + expression, context, cause);
    }
}