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

package org.qi4j.api.constraint;

import java.lang.annotation.Annotation;
import java.io.Serializable;

/**
 * All Constraints must implement this interface, which is used for each
 * value validation.
 */
public interface Constraint<A extends Annotation, P> extends Serializable
{
    /**
     * For each value or parameter which should be checked this method will be invoked.
     * If the method returns true the value is valid. If it returns false the value
     * is considered invalid. When all constraints have been checked a ConstraintViolationException
     * will be thrown with all the constraint violations that were found.
     *
     * @param annotation the annotation to match
     * @param value      the value to be checked
     * @return true if valid, false if invalid
     * @throws NullPointerException NPE can be thrown if the value was null
     */
    boolean isValid( A annotation, P value )
        throws NullPointerException;
}