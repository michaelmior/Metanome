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

import de.metanome.algorithm_integration.AlgorithmConfigurationException;
import de.metanome.algorithm_integration.input.InputGenerationException;
import de.metanome.algorithm_integration.input.InputIterationException;
import de.metanome.algorithm_integration.input.JsonInput;
import de.metanome.algorithm_integration.input.JsonInputGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Generator for {@link de.metanome.algorithm_integration.input.RelationalInput}s based on JSON
 * files.
 *
 * @author Michael Mior
 */
public class DefaultJsonInputGenerator implements JsonInputGenerator {
  File inputFile;

  public DefaultJsonInputGenerator(File inputFile)
          throws AlgorithmConfigurationException, FileNotFoundException {
    try {
      this.setInputFile(inputFile);
    } catch (FileNotFoundException e) {
      throw new AlgorithmConfigurationException("File not found!", e);
    }
  }


  public JsonInput generateNewCopy() throws InputGenerationException {
    try {
      return new JsonIterator(inputFile.getName(), new FileReader(inputFile));
    } catch (FileNotFoundException e) {
      throw new InputGenerationException("File not found!", e);
    } catch (InputIterationException e) {
      throw new InputGenerationException("Could not iterate over the first line of the file input", e);
    }
  }

  /**
   * @return inputFile
   */
  public File getInputFile() {
    return inputFile;
  }

  private void setInputFile(File inputFile) throws FileNotFoundException {
    if (inputFile.isFile()) {
      this.inputFile = inputFile;
    } else {
      throw new FileNotFoundException();
    }
  }

  @Override
  public void close() throws Exception {
    // Nothing to close
  }
}
