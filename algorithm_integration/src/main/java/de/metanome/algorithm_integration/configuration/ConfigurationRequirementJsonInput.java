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
package de.metanome.algorithm_integration.configuration;

import com.fasterxml.jackson.annotation.JsonTypeName;
import de.metanome.algorithm_integration.AlgorithmConfigurationException;

import javax.xml.bind.annotation.XmlTransient;


/**
 * Concrete {@link ConfigurationRequirement} for {@link de.metanome.algorithm_integration.input.JsonInput}s.
 *
 * @author Michael Mior
 * @see ConfigurationRequirement
 */
@JsonTypeName("ConfigurationRequirementJsonInput")
public class ConfigurationRequirementJsonInput
  extends ConfigurationRequirement<ConfigurationSettingJsonInput> {

  private static final long serialVersionUID = 6007734923218603744L;

  // Needed for restful serialization
  public String type = "ConfigurationRequirementJsonInput";

  public ConfigurationRequirementJsonInput() {
  }

  public ConfigurationRequirementJsonInput(String identifier) {
    super(identifier);
  }

  public ConfigurationRequirementJsonInput(String identifier, int numberOfSettings) {
    super(identifier, numberOfSettings);
  }

  public ConfigurationRequirementJsonInput(String identifier, int minNumberOfSetting,
                                                 int maxNumberOfSetting) {
    super(identifier, minNumberOfSetting, maxNumberOfSetting);
  }

  /**
   * {@inheritDoc}
   */
  @XmlTransient
  @Override
  public ConfigurationValue build(ConfigurationFactory factory)
    throws AlgorithmConfigurationException {
    return factory.build(this);
  }

}
