/**
 * Copyright 2015-2016 by Metanome Project
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
package de.metanome.backend.input.file;

import de.metanome.algorithm_integration.configuration.ConfigurationSettingFileInput;
import de.metanome.algorithm_integration.input.InputGenerationException;
import de.metanome.algorithm_integration.input.InputIterationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Tests for {@link JsonIterator}
 *
 * @author Michael Mior
 */
public class JsonIteratorTest {
  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  JsonFileOneLineFixture fixture;
  JsonIterator jsonIterator;

  @Before
  public void setUp() throws Exception {
    this.fixture = new JsonFileOneLineFixture(folder);
    this.jsonIterator = this.fixture.getTestData();
  }

  @After
  public void tearDown() throws Exception {
  }

  /**
   * Has next should be true once and false after calling next once (one file line).
   */
  @Test
  public void testHasNext() throws InputIterationException {
    // Check result
    assertTrue(this.jsonIterator.hasNext());
    this.jsonIterator.next();
    assertFalse(this.jsonIterator.hasNext());
  }

  /**
   * A one line file should be parsed correctly. And all the values in the line should be equal.
   */
  @Test
  public void testNext() throws InputIterationException {
    // Check result
    assertEquals(this.fixture.getExpectedStrings(), this.jsonIterator.next());
  }
}
