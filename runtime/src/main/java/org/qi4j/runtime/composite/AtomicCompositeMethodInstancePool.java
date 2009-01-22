/*
 * Copyright (c) 2008, Rickard Öberg. All Rights Reserved.
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

package org.qi4j.runtime.composite;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Method instance pool that keeps a linked list. Uses atomic reference
 * to ensure that instances are acquired and returned in a thread-safe
 * manner.
 */
public final class AtomicCompositeMethodInstancePool
    implements CompositeMethodInstancePool
{
    private final AtomicReference<CompositeMethodInstance> first = new AtomicReference<CompositeMethodInstance>();

    public CompositeMethodInstance getInstance()
    {
        CompositeMethodInstance instance = first.getAndSet( null );
        if( instance != null )
        {
            CompositeMethodInstance next = instance.getNext();
/*
            if( next != null )
            {
                System.out.println( "Set first" );
            }
*/
            first.set( next );
        }
        return instance;
    }

    public void returnInstance( CompositeMethodInstance compositeMethodInstance )
    {
        CompositeMethodInstance previous = first.getAndSet( compositeMethodInstance );
        if( previous != null )
        {
/*
            System.out.println( "Link" );
*/
            compositeMethodInstance.setNext( previous );
        }
    }
}
