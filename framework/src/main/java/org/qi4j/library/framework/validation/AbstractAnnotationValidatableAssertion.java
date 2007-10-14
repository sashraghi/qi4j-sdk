package org.qi4j.library.framework.validation;

import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.qi4j.api.annotation.AppliesTo;
import org.qi4j.api.annotation.AppliesToFilter;
import org.qi4j.api.annotation.scope.AssertionFor;
import org.qi4j.api.annotation.scope.Invocation;
import org.qi4j.api.annotation.scope.ThisAs;

/**
 * TODO
 */
@AppliesTo( AbstractAnnotationValidatableAssertion.AppliesTo.class )
public abstract class AbstractAnnotationValidatableAssertion<R extends Annotation, T>
    implements InvocationHandler
{
    private Class annotationType;

    public static class AppliesTo
        implements AppliesToFilter
    {
        public boolean appliesTo( Method method, Class mixin, Class compositeType, Class modifierClass )
        {
            Class annotationClass = (Class) ( (ParameterizedType) modifierClass.getGenericSuperclass() ).getActualTypeArguments()[ 0 ];
            Annotation[][] annotations = method.getParameterAnnotations();
            for( Annotation[] annotation : annotations )
            {
                for( Annotation annotation1 : annotation )
                {
                    if( annotationClass.isInstance( annotation1 ) )
                    {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    @ThisAs ValidationMessages messages;
    @AssertionFor InvocationHandler next;

    Method method;
    String message;
    String resourceBundle;

    List<Method> annotationMethods = new ArrayList<Method>();

    public void setMethod( @Invocation Method method )
    {
        annotationType = (Class) ( (ParameterizedType) this.getClass().getGenericSuperclass() ).getActualTypeArguments()[ 0 ];

        this.method = method;

        if( method.getName().startsWith( "set" ) )
        {
            message = Introspector.decapitalize( method.getName().substring( 3 ) );
        }
        else
        {
            message = method.getName();
        }

        String name = annotationType.getSimpleName();
        StringBuffer messageBuf = new StringBuffer();
        for( int i = 0; i < name.length(); i++ )
        {
            char ch = name.charAt( i );
            if( Character.isUpperCase( ch ) )
            {
                messageBuf.append( '.' );
                messageBuf.append( Character.toLowerCase( ch ) );
            }
            else
            {
                messageBuf.append( ch );
            }
        }

        message += messageBuf.toString();

        resourceBundle = method.getDeclaringClass().getName();
        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle( resourceBundle );
            bundle.getString( message );
        }
        catch( MissingResourceException e )
        {
            resourceBundle = method.getDeclaringClass().getPackage() + ".package";
            try
            {
                ResourceBundle bundle = ResourceBundle.getBundle( resourceBundle );
                bundle.getString( message );
            }
            catch( MissingResourceException e1 )
            {
                // No bundle
                resourceBundle = null;
            }
        }

        Method[] params = annotationType.getMethods();
        for( Method param : params )
        {
            if( !param.getDeclaringClass().equals( Annotation.class ) )
            {
                annotationMethods.add( param );
            }
        }
    }

    public Object invoke( Object object, Method method, Object[] objects ) throws Throwable
    {
        Annotation[][] annotations = method.getParameterAnnotations();
        int i = 0;
        for( Annotation[] annotation : annotations )
        {
            for( Annotation annotation1 : annotation )
            {
                if( annotationType.isInstance( annotation1 ) )
                {
                    T arg = (T) objects[ i ];
                    boolean valid = isValid( (R) annotation1, arg );
                    if( !valid )
                    {
                        Object[] paramValues = new Object[annotationMethods.size() + 1];
                        paramValues[ 0 ] = objects[ i ];
                        for( int j = 0; j < annotationMethods.size(); j++ )
                        {
                            Object paramValue = annotationMethods.get( j ).invoke( annotation1 );
                            paramValues[ i + 1 ] = paramValue;
                        }
                        messages.addValidationMessage( newMessage( paramValues ) );
                    }
                    else
                    {
                        messages.removeValidationMessage( message );
                    }
                }
            }
            i++;
        }

        return next.invoke( object, method, objects );
    }

    protected abstract boolean isValid( R annotation, T argument );

    private ValidationMessage newMessage( Object[] params )
    {
        return new ValidationMessage( message, resourceBundle, ValidationMessage.Severity.ERROR, params );
    }
}
