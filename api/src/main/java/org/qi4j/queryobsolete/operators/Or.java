/*
 * Copyright 2006 Niclas Hedhman.
 *
 * Licensed  under the  Apache License,  Version 2.0  (the "License");
 * you may not use  this file  except in  compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed  under the  License is distributed on an "AS IS" BASIS,
 * WITHOUT  WARRANTIES OR CONDITIONS  OF ANY KIND, either  express  or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package org.qi4j.queryobsolete.operators;

import java.util.Map;
import org.qi4j.queryobsolete.BinaryOperator;
import org.qi4j.queryobsolete.BooleanExpression;

public final class Or
    implements BinaryOperator, BooleanExpression
{
    private BooleanExpression left;
    private BooleanExpression right;

    public Or( BooleanExpression left, BooleanExpression right )
    {
        this.left = left;
        this.right = right;
    }

    public BooleanExpression getLeftArgument()
    {
        return left;
    }

    public BooleanExpression getRightArgument()
    {
        return right;
    }


    public boolean evaluate( Object candidate, Map<String, Object> variables )
    {
        return left.evaluate( candidate, variables ) || right.evaluate( candidate, variables );
    }

    public String toString()
    {
        return "(" + left + " OR " + right + ")";
    }
}