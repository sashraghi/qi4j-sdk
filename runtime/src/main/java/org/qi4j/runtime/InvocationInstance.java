/*
 * Copyright (c) 2007, Rickard Öberg. All Rights Reserved.
 * Copyright (c) 2007, Niclas Hedhman. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.qi4j.runtime;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class InvocationInstance
{
    private Object firstAssertion;
    private Object[] sideEffects;
    private SideEffectInvocationHandlerResult sideEffectResult;
    private Method method;
    private Class mixinType;
    private FragmentInvocationHandler mixinInvocationHandler;
    private ProxyReferenceInvocationHandler proxyHandler;
    private InvocationInstancePool pool;
    private InvocationInstance next;

    public InvocationInstance( Object aFirstAssertion, Object[] sideEffects, SideEffectInvocationHandlerResult sideEffectResult, FragmentInvocationHandler aMixinInvocationHandler, ProxyReferenceInvocationHandler aProxyHandler, InvocationInstancePool aPool, Method method, Class mixinType )
    {
        this.sideEffectResult = sideEffectResult;
        this.sideEffects = sideEffects;
        this.mixinType = mixinType;
        this.method = method;
        firstAssertion = aFirstAssertion;
        proxyHandler = aProxyHandler;
        mixinInvocationHandler = aMixinInvocationHandler;
        pool = aPool;
    }

    public Object invoke( Object proxy, Object[] args, Object mixin )
        throws Throwable
    {
        try
        {
            Object result;
            if( firstAssertion == null )
            {
                if( mixin instanceof InvocationHandler )
                {
                    result = ( (InvocationHandler) mixin ).invoke( proxy, method, args );
                }
                else
                {
                    result = method.invoke( mixin, args );
                }
            }
            else
            {
                proxyHandler.setContext( proxy, mixin, mixinType );
                mixinInvocationHandler.setFragment( mixin );
                if( firstAssertion instanceof InvocationHandler )
                {
                    result = ( (InvocationHandler) firstAssertion ).invoke( proxy, method, args );
                }
                else
                {
                    result = method.invoke( firstAssertion, args );
                }
            }

            // Check for side-effects
            invokeSideEffects( result, null, proxy, args );

            return result;
        }
        catch( InvocationTargetException e )
        {
            Throwable throwable = e.getTargetException();
            invokeSideEffects( null, throwable, proxy, args );

            throw throwable;
        }
        finally
        {
            pool.returnInstance( this );
        }
    }

    private void invokeSideEffects( Object result, Throwable throwable, Object proxy, Object[] args )
    {
        sideEffectResult.setResult( result, throwable );
        for( Object sideEffect : sideEffects )
        {
            if( sideEffect instanceof InvocationHandler )
            {
                InvocationHandler handler = (InvocationHandler) sideEffect;
                try
                {
                    handler.invoke( proxy, method, args );
                }
                catch( Throwable e )
                {
                    // Ignore (?)
                }
            }
            else
            {
                try
                {
                    method.invoke( sideEffect, args );
                }
                catch( Throwable e )
                {
                    // Ignore (?)
                    e.printStackTrace();
                }
            }
        }
    }

    public InvocationInstance getNext()
    {
        return next;
    }

    public void setNext( InvocationInstance next )
    {
        this.next = next;
    }
}
