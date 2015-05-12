/*
 * Copyright (C) 2015 Giuseppe Cardone <ippatsuman@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gcardone.junidecode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static gcardone.junidecode.Junidecode.*;

/**
 * Simple example application for JUnidecode. If launched with arguments
 * will strip diacritics and transliterate the arguments. If launched without
 * arguments will read lines from stdin, convert input to ASCII 7-bit and
 * write to stdout. For example:
 * <em>&#917;&#955;&#955;&#951;&#957;&#953;&#954;&#940;</em> becomes
 * <em>Ellenika</em>.
 * @author Giuseppe Cardone
 * @version 0.1
 */
public class App {

    /**
     * Private constructor to avoid instatiation.
     */
    private App() {
    }

    /**
     * Main.
     * @param args Strings to transliterate. If <code>args.length == 0</code>
     * then the input will be read from stdin.
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (String s : args) {
                sb.append(unidecode(s)).append(" ");
            }
            System.out.println(sb.toString().trim());
        } else {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(unidecode(line));
                }
            } catch (IOException ex) {
                System.err.println(ex.getLocalizedMessage());
            }
        }
    }
}
