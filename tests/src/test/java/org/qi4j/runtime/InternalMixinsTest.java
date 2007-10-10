package org.qi4j.runtime;

import org.qi4j.api.Composite;
import org.qi4j.api.annotation.Mixins;
import org.qi4j.api.annotation.scope.ThisAs;

/**
 * TODO
 */
public class InternalMixinsTest
    extends AbstractTest
{
    public void testInternalMixins()
    {
        TestComposite test = builderFactory.newCompositeBuilder( TestComposite.class ).newInstance();
        assertEquals( "XYZ123", test.doStuff() );
    }

    @Mixins( AMixin.class )
    public interface A
    {
        String doStuff();
    }

    public static class AMixin
        implements A
    {
        @ThisAs B bRef;

        public String doStuff()
        {
            return bRef.otherStuff() + "123";
        }
    }

    public interface B
    {
        String otherStuff();
    }

    public static class BMixin
        implements B
    {
        public String otherStuff()
        {
            return "XYZ";
        }
    }

    @Mixins( { InternalMixinsTest.BMixin.class } )
    public interface TestComposite
        extends A, Composite
    {
    }
}
