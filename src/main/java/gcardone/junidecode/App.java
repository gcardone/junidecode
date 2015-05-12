/*
 * Copyright (c) 2009, Giuseppe Cardone <ippatsuman@gmail.com>
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *  * Neither the name of the author nor the names of the contributors may be
 *    used to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY GIUSEPPE CARDONE ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL GIUSEPPE CARDONE BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package gcardone.junidecode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static net.sf.junidecode.Junidecode.*;

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
