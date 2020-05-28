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

import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParserException;
import com.grack.nanojson.JsonReader;

import de.metanome.algorithm_integration.configuration.ConfigurationSettingFileInput;
import de.metanome.algorithm_integration.input.InputIterationException;
import de.metanome.algorithm_integration.input.RelationalInput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * {@link JsonIterator}s are Iterators over lines in a file file.
 *
 * @author Michael Mior
 */
public class JsonIterator implements RelationalInput {

  protected BufferedReader reader;
  protected List<String> nextLine;
  protected String relationName;
  // Initialized to -1 because of lookahead
  protected int currentLineNumber = -1;
  protected int numberOfSkippedLines = 0;
  protected LinkedHashMap<String, String> rowTemplate;

  protected boolean hasHeader;
  protected boolean skipDifferingLines;
  protected String nullValue;


  public JsonIterator(String relationName, RandomAccessFile file, ConfigurationSettingFileInput setting)
    throws InputIterationException {

    this.relationName = relationName;

    this.hasHeader = setting.hasHeader();
    this.skipDifferingLines = setting.isSkipDifferingLines();
    this.nullValue = setting.getNullValue();

    this.rowTemplate = new LinkedHashMap<String, String>();

    try {
      this.reader = new BufferedReader(new FileReader(file.getFD()));

      String line;
      while ((line = this.reader.readLine()) != null) {
        JsonReader jsonReader = JsonReader.from(line);
        jsonReader.object();
        while (jsonReader.next()) {
          rowTemplate.put(jsonReader.key(), null);
        }
      }

      file.seek(0);
      this.nextLine = readNextLine();
    } catch (IOException e) {
      throw new InputIterationException("Error finding attribute list", e);
    } catch (JsonParserException e) {
      throw new InputIterationException("Error finding attribute list", e);
    }
  }

  @Override
  public boolean hasNext() {
    return !(this.nextLine == null);
  }

  @Override
  public List<String> next() throws InputIterationException {
    List<String> currentLine = this.nextLine;

    if (currentLine == null) {
      return null;
    }
    this.nextLine = readNextLine();

    if (this.skipDifferingLines) {
      readToNextValidLine();
    } else {
      failDifferingLine(currentLine);
    }

    return currentLine;
  }

  protected void failDifferingLine(List<String> currentLine)
    throws InputIterationException {
    if (currentLine.size() != this.numberOfColumns()) {
      throw new InputIterationException(
        "JSON object did not have correct number of keys on line " + currentLineNumber);
    }
  }

  protected void readToNextValidLine() throws InputIterationException {
    if (!hasNext()) {
      return;
    }

    while (this.nextLine.size() != this.rowTemplate.size()) {
      this.nextLine = readNextLine();
      this.numberOfSkippedLines++;
      if (!hasNext()) {
        break;
      }
    }
  }

  protected List<String> readNextLine() throws InputIterationException {
    LinkedHashMap<String, String> row = (LinkedHashMap<String, String>) rowTemplate.clone();
    try {
      String line = this.reader.readLine();
      if (line == null) {
        return null;
      }

      JsonReader jsonReader = JsonReader.from(line);
      jsonReader.object();
      while (jsonReader.next()) {
          String key = jsonReader.key();
          String value = jsonReader.value().toString();
          if (value.equals(this.nullValue)) {
            value = null;
          }
          row.put(key, value);
      }
      currentLineNumber++;
    } catch (IOException e) {
      throw new InputIterationException("Could not read next line in file input", e);
    } catch (JsonParserException e) {
      throw new InputIterationException("Could not read next line in file input", e);
    }

    // Return a list of values
    return new ArrayList(row.values());
  }

  @Override
  public void close() throws IOException {
    reader.close();
  }

  @Override
  public int numberOfColumns() {
    return this.rowTemplate.size();
  }

  @Override
  public String relationName() {
    return relationName;
  }

  @Override
  public List<String> columnNames() {
    return new ArrayList(rowTemplate.keySet());
  }

  public int getNumberOfSkippedDifferingLines() {
    return numberOfSkippedLines;
  }

}
