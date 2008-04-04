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
package org.qi4j.queryobsolete.value;

import java.util.Map;

public final class BooleanValueExpression
    implements ValueExpression
{
    private Boolean value;

    public BooleanValueExpression( Boolean value )
    {
        this.value = value;
    }

    public Boolean getValue( Object candidate, Map<String, Object> variables )
    {
        return value;
    }

    public String toString()
    {
        return value.toString().toUpperCase();
    }
}