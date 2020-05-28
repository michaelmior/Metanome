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

import au.com.bytecode.opencsv.CSVParser;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.grack.nanojson.JsonStringWriter;
import com.grack.nanojson.JsonWriter;
import de.metanome.algorithm_integration.configuration.ConfigurationSettingFileInput;
import de.metanome.algorithm_integration.input.InputGenerationException;
import de.metanome.algorithm_integration.input.InputIterationException;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class JsonFileOneLineFixture {
  protected TemporaryFolder folder;
  protected ConfigurationSettingFileInput setting;

  public JsonFileOneLineFixture(TemporaryFolder folder) {
    this.folder = folder;
    this.setting = new ConfigurationSettingFileInput(this.getExpectedRelationName());
  }

  public JsonIterator getTestData() throws InputIterationException, InputGenerationException, IOException {
    this.setting.setSkipLines(0);

    // Write the generated JSON to file
    File jsonFile = folder.newFile("test.json");
    try (PrintWriter out = new PrintWriter(jsonFile)) {
      out.println(getJsonInputString());
      out.flush();
    }

    return new JsonIterator(getExpectedRelationName(),
      new RandomAccessFile(jsonFile, "r"), setting);
  }

  protected String getJsonInputString() {
    JsonStringWriter writer = JsonWriter.string().object();
    List<String> keys = getExpectedColumnNames();
    List<String> values = getExpectedStrings();

    for (int i = 0; i < keys.size(); i++) {
        writer.value(keys.get(i), values.get(i));
    }

    return writer.end().done();
  }

  public ImmutableList<String> getExpectedStrings() {
    return ImmutableList.of("value1", "value2", "value3");
  }

  public String getExpectedRelationName() {
    return "some_relation";
  }

  public ImmutableList<String> getExpectedColumnNames() {
    return ImmutableList.of("column1", "column2", "column3");
  }

  public int getExpectedNumberOfColumns() {
    return getExpectedStrings().size();
  }

}
