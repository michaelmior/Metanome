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

package de.uni_potsdam.hpi.metanome.input.csv;

import com.google.common.collect.ImmutableList;
import de.uni_potsdam.hpi.metanome.algorithm_integration.input.InputGenerationException;
import de.uni_potsdam.hpi.metanome.algorithm_integration.input.InputIterationException;
import de.uni_potsdam.hpi.metanome.algorithm_integration.input.RelationalInput;
import de.uni_potsdam.hpi.metanome.algorithm_integration.input.RelationalInputGenerator;
import de.uni_potsdam.hpi.metanome.algorithm_integration.result_receiver.CouldNotReceiveResultException;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static org.junit.Assert.*;

/**
 * Tests for {@link de.uni_potsdam.hpi.metanome.algorithm_integration.input.RelationalInput}
 * <p/>
 * Tests using different input file encodings
 * current tested encodings are:
 * utf8, utf8 without Bom, utf16 little endian, utf16 big endian
 *
 * @author Jens Hildebrandt
 */

public class InputEncodingTest {
    protected String utf16littleEndian = "utf_input/utf16little.csv";
    protected String utf16BigEndian = "utf_input/utf16big.csv";
    protected String utf8 = "utf_input/utf8.csv";
    protected String utf8WithoutBom = "utf_input/utf8withoutbom.csv";

    public InputEncodingTest() throws CouldNotReceiveResultException {
    }

    /**
     * TODO docs
     *
     * @param relationName
     * @return
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    public RelationalInputGenerator getInputGenerator(String relationName) throws UnsupportedEncodingException, FileNotFoundException {
        String pathToInputFile = URLDecoder.decode(Thread.currentThread().getContextClassLoader().getResource(relationName).getPath(), "utf-8");
        RelationalInputGenerator inputGenerator = new CsvFileGenerator(new File(pathToInputFile));
        return inputGenerator;
    }

    /**
     * TODO docs
     *
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     * @throws InputGenerationException
     * @throws InputIterationException
     */
    @Test
    public void testUTF16LittleEndian() throws UnsupportedEncodingException, FileNotFoundException, InputGenerationException, InputIterationException {
        RelationalInputGenerator generator = getInputGenerator(this.utf16littleEndian);
        RelationalInput input = generator.generateNewCopy();
        assertTrue(input.hasNext());
        ImmutableList<String> firstLine = input.next();
        assertEquals("hello", firstLine.get(0));
        assertEquals("world", firstLine.get(1));
        assertFalse(input.hasNext());
    }

    /**
     * TODO docs
     *
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     * @throws InputGenerationException
     * @throws InputIterationException
     */
    @Test
    public void testUTF16BigEndian() throws UnsupportedEncodingException, FileNotFoundException, InputGenerationException, InputIterationException {
        RelationalInputGenerator generator = getInputGenerator(this.utf16BigEndian);
        RelationalInput input = generator.generateNewCopy();
        assertTrue(input.hasNext());
        ImmutableList<String> firstLine = input.next();
        assertEquals("hello", firstLine.get(0));
        assertEquals("world", firstLine.get(1));
        assertFalse(input.hasNext());
    }

    /**
     * TODO docs
     *
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     * @throws InputGenerationException
     * @throws InputIterationException
     */
    @Test
    public void testUTF8() throws UnsupportedEncodingException, FileNotFoundException, InputGenerationException, InputIterationException {
        RelationalInputGenerator generator = getInputGenerator(this.utf8);
        RelationalInput input = generator.generateNewCopy();
        assertTrue(input.hasNext());
        ImmutableList<String> firstLine = input.next();
        assertEquals("hello", firstLine.get(0));
        assertEquals("world", firstLine.get(1));
        assertFalse(input.hasNext());
    }

    /**
     * TODO docs
     *
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     * @throws InputGenerationException
     * @throws InputIterationException
     */
    @Test
    public void testUTF8WithoutBom() throws UnsupportedEncodingException, FileNotFoundException, InputGenerationException, InputIterationException {
        RelationalInputGenerator generator = getInputGenerator(this.utf8WithoutBom);
        RelationalInput input = generator.generateNewCopy();
        assertTrue(input.hasNext());
        ImmutableList<String> firstLine = input.next();
        assertEquals("hello", firstLine.get(0));
        assertEquals("world", firstLine.get(1));
        assertFalse(input.hasNext());
    }
}
