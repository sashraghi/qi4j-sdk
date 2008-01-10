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

package org.qi4j.spi.service;

import org.qi4j.spi.injection.InjectionContext;
import org.qi4j.spi.injection.InjectionResolution;

/**
 * TODO
 */
public final class Instance
    implements ServiceProvider
{
    Object instance;

    public Instance( Object instance )
    {
        this.instance = instance;
    }

    public Object getService( InjectionResolution injectionResolution, InjectionContext injectionContext )
    {
        return instance;
    }

    public void releaseService( Object service )
    {
        // Ignore for now
    }
}