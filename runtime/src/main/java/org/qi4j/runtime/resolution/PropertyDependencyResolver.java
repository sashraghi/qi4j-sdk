package org.qi4j.runtime.resolution;

import java.util.Map;
import org.qi4j.api.model.DependencyKey;
import org.qi4j.api.model.InjectionKey;
import org.qi4j.spi.dependency.DependencyInjectionContext;
import org.qi4j.spi.dependency.DependencyResolution;
import org.qi4j.spi.dependency.DependencyResolver;
import org.qi4j.spi.dependency.ObjectDependencyInjectionContext;

/**
 * TODO
 */
public class PropertyDependencyResolver
    implements DependencyResolver
{
    KeyMatcher matcher;

    public PropertyDependencyResolver()
    {
        this.matcher = new KeyMatcher();
    }

    public DependencyResolution resolveDependency( DependencyKey key )
    {
        return new PropertyDependencyResolution( key );
    }

    private class PropertyDependencyResolution implements DependencyResolution
    {
        private DependencyKey key;

        public PropertyDependencyResolution( DependencyKey key )
        {
            this.key = key;
        }

        public Object getDependencyInjection( DependencyInjectionContext context )
        {
            if( context instanceof ObjectDependencyInjectionContext )
            {
                ObjectDependencyInjectionContext mixinContext = (ObjectDependencyInjectionContext) context;
                for( Map.Entry<InjectionKey, Object> entry : mixinContext.getProperties().entrySet() )
                {
                    if( matcher.matches( key, entry.getKey() ) )
                    {
                        return entry.getValue();
                    }

                }

                return null;
            }

            return null;
        }
    }
}