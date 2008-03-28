/*
 * Copyright 2008 Alin Dreghiciu.
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
package org.qi4j.query.grammar.impl;

import org.qi4j.query.grammar.NotEqualsPredicate;
import org.qi4j.query.grammar.PropertyReference;
import org.qi4j.query.grammar.ValueExpression;

/**
 * Default {@link org.qi4j.query.grammar.NotEqualsPredicate} implementation.
 *
 * @author Alin Dreghiciu
 * @since March 25, 2008
 */
public class NotEqualsPredicateImpl<T>
    extends ComparisonPredicateImpl<T>
    implements NotEqualsPredicate<T>
{

    /**
     * Constructor.
     *
     * @param propertyReference property reference; cannot be null
     * @param valueExpression   value expression; cannot be null
     * @throws IllegalArgumentException - If property reference is null
     *                                  - If value expression is null
     */
    public NotEqualsPredicateImpl( final PropertyReference<T> propertyReference,
                                   final ValueExpression<T> valueExpression )
    {
        super( propertyReference, valueExpression );
    }

    @Override public String toString()
    {
        return new StringBuilder()
            .append( "( " )
            .append( getPropertyReference() )
            .append( " != " )
            .append( "\"" )
            .append( getValueExpression() )
            .append( "\"^^" )
            .append( getPropertyReference().getType().getSimpleName() )
            .append( " )" )
            .toString();
    }

}