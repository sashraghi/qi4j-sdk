package org.qi4j.spi.dependency;

import java.lang.reflect.InvocationHandler;
import org.qi4j.api.model.CompositeContext;

/**
 * TODO
 */
public interface FragmentDependencyInjectionContext
{
    public CompositeContext getContext();

    public InvocationHandler getThisAs();
}