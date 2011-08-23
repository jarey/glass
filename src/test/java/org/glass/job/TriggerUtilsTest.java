/*
 * Copyright 2011 Damien Bourdette
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.glass.job;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author damien bourdette
 */
public class TriggerUtilsTest {
    @Test
    public void getPlanification() {
        Assert.assertEquals("repeat 10 times every 100ms", TriggerUtils.getPlanification(10, 100));
        Assert.assertEquals("repeat forever every 100ms", TriggerUtils.getPlanification(-1, 100));
        Assert.assertEquals("execute once", TriggerUtils.getPlanification(0, 100));
        Assert.assertEquals("repeat one time in 100ms", TriggerUtils.getPlanification(1, 100));
    }
}
