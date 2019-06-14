/**
 * Copyright 2014-2016 by Metanome Project
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
package de.metanome.backend.input;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import de.metanome.algorithm_integration.AlgorithmConfigurationException;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementJsonInput;
import de.metanome.algorithm_integration.configuration.ConfigurationSettingFileInput;
import de.metanome.algorithm_integration.configuration.ConfigurationSettingJsonInput;
import de.metanome.algorithm_integration.input.JsonInputGenerator;
import de.metanome.algorithm_integration.input.JsonInputGeneratorInitializer;
import de.metanome.backend.algorithm_loading.InputDataFinder;
import de.metanome.backend.configuration.ConfigurationValueJsonInputGenerator;
import de.metanome.backend.input.file.DefaultJsonInputGenerator;

/**
 * Initializes {@link de.metanome.algorithm_integration.input.JsonInputGenerator}s that are
 * based on files or database tables.
 */
public class DefaultJsonInputGeneratorInitializer
  implements JsonInputGeneratorInitializer {

  List<JsonInputGenerator> generatorList = new ArrayList<>();
  String identifier;

  /**
   * @param requirementJsonInput the requirement to initialize from
   * @throws AlgorithmConfigurationException if one of the settings from the requirement cannot be
   *                                         converted
   */
  public DefaultJsonInputGeneratorInitializer(
    ConfigurationRequirementJsonInput requirementJsonInput)
    throws AlgorithmConfigurationException {
    this.identifier = requirementJsonInput.getIdentifier();

    ConfigurationSettingJsonInput[] settings = requirementJsonInput.getSettings();
    for (ConfigurationSettingJsonInput setting : settings) {
      setting.generate(this);
    }
  }

  /**
   * Initialize {@link de.metanome.algorithm_integration.input.JsonInputGenerator} from a
   * {@link de.metanome.algorithm_integration.configuration.ConfigurationSettingFileInput}.
   *
   * @param setting the setting used to initialize the input
   * @throws AlgorithmConfigurationException if the input cannot be initialized
   */
  @Override
  public void initialize(ConfigurationSettingFileInput setting)
    throws AlgorithmConfigurationException {

    File currFile = new File(setting.getFileName());
    try {
      if (currFile.isFile()) {
          generatorList.add(new DefaultJsonInputGenerator(currFile));
      } else if (currFile.isDirectory()) {
          File[] filesInDirectory = currFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
              for (String fileEnding : InputDataFinder.ACCEPTED_FILE_ENDINGS) {
                if (name.endsWith(fileEnding)) {
                  return true;
                }
              }
              return false;
            }
          });
          for (File file : filesInDirectory) {
            generatorList.add(new DefaultJsonInputGenerator(file));
          }
        }
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }

  /**
   * @return the initialized {@link de.metanome.algorithm_integration.input.JsonInputGenerator}s
   */
  public ConfigurationValueJsonInputGenerator getConfigurationValue() {
    return new ConfigurationValueJsonInputGenerator(identifier,
      generatorList.toArray(
        new JsonInputGenerator[generatorList
          .size()]));
  }
}
