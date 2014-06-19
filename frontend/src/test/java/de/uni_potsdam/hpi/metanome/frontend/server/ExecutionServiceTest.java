/*
 * Copyright 2014 by the Metanome project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.uni_potsdam.hpi.metanome.frontend.server;

import static junit.framework.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class ExecutionServiceTest {

    ExecutionServiceImpl executionService = new ExecutionServiceImpl();

    /**
     * Test method for {@link ExecutionServiceImpl#fetchProgress(String)}
     * <p/>
     * When fetching the current progress for an execution the correct progress should be returned.
     *
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    @Test
    public void testFetchProgress() throws FileNotFoundException, UnsupportedEncodingException {
        // Setup
        // Expected values
        String expectedExecutionIdentifier = "executionIdentifier";
        executionService.buildExecutor(expectedExecutionIdentifier);
        float expectedProgress = 0.42f;

        // Execute functionality
        executionService.currentProgressCaches.get(expectedExecutionIdentifier).updateProgress(expectedProgress);
        float actualProgress = executionService.fetchProgress(expectedExecutionIdentifier);

        // Check result
        assertEquals(expectedProgress, actualProgress);
    }

}
