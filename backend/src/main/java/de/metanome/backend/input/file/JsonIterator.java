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

import com.google.gson.Gson;
import de.metanome.algorithm_integration.configuration.ConfigurationSettingFileInput;
import de.metanome.algorithm_integration.input.InputIterationException;
import de.metanome.algorithm_integration.input.JsonInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link JsonIterator}s are Iterators over lines in a JSON file.
 *
 * @author Michael Mior
 */
public class JsonIterator implements JsonInput {

  protected BufferedReader fileReader;
  protected Gson gson;
  protected Map nextLine;
  protected String jsonName;
  // Initialized to -1 because of lookahead
  protected int currentLineNumber = -1;
  protected int numberOfSkippedLines = 0;

  public JsonIterator(String jsonName, Reader reader) throws InputIterationException {
    this.jsonName = jsonName;
    this.fileReader = new BufferedReader(reader);
    this.gson = new Gson();
    this.nextLine = readNextLine();
  }

  @Override
  public boolean hasNext() {
    return !(this.nextLine == null);
  }

  @Override
  public Map next() throws InputIterationException {
    Map currentLine = this.nextLine;

    if (currentLine == null) {
      return null;
    }
    this.nextLine = readNextLine();

    return currentLine;
  }

  protected void collectValues(Map<String, Object> map, String key, Object value) {
      if (value instanceof Map) {
          for (Map.Entry<String, Object> valueEntry : ((Map<String, Object>) value).entrySet()) {
              collectValues(map, key + "." + valueEntry.getKey(), valueEntry.getValue());
          }
      } else if (value instanceof List) {
          for (Object listValue : (List<Object>) value) {
              collectValues(map, key + "[]", listValue);
          }
      } else if (value != null && !"".equals(value)) {
          map.put(key, value);
      }
  }

  protected Map readNextLine() throws InputIterationException {
    Map<String, Object> lineMap;
    try {
      String line = fileReader.readLine();
      lineMap = gson.fromJson(line, Map.class);
      currentLineNumber++;
    } catch (IOException e) {
      throw new InputIterationException("Could not read next line in file input", e);
    }
    if (lineMap == null) {
      return null;
    }

    Map<String, Object> flatMap = new HashMap<>();
    for (Map.Entry<String, Object> entry : lineMap.entrySet()) {
        this.collectValues(flatMap, entry.getKey(), entry.getValue());
    }

    // Return an immutable list
    return Collections.unmodifiableMap(flatMap);
  }

  @Override
  public void close() throws IOException {
    fileReader.close();
  }

  @Override
  public String jsonName() {
    return jsonName;
  }
}
