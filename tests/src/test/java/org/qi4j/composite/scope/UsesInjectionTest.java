/*
 * Copyright (c) 2007, Rickard Öberg. All Rights Reserved.
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

package org.qi4j.composite.scope;

import org.qi4j.bootstrap.AssemblerException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.composite.ObjectBuilder;
import org.qi4j.test.AbstractQi4jTest;

/**
 * Test the @Uses annotation
 */
public class UsesInjectionTest extends AbstractQi4jTest
{
    public void assemble( ModuleAssembly module )
        throws AssemblerException
    {
        module.addObjects( Object1.class, Object2.class );
    }

    public void testWhenUsesAnnotationThenInjectObject()
        throws Exception
    {
        ObjectBuilder<Object1> builder = objectBuilderFactory.newObjectBuilder( Object1.class );
        Object2 used = new Object2( "Object2" );
        builder.use( used );

        Object1 object = builder.newInstance();

        assertEquals( used, object.getUsed() );
    }

    public static class Object1
    {
        @Uses
        Object2 used;

        public Object2 getUsed()
        {
            return used;
        }
    }

    public class Object2
    {
        String name;

        public Object2( String name )
        {
            this.name = name;
        }

        public String getName()
        {
            return name;
        }
    }
}