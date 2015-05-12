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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Giuseppe Cardone
 */
public class JunidecodeTest {

    public JunidecodeTest() {
    }

    /**
     *
     * @param s
     */
    private void sevenBitPurity(String s) {
        byte[] bytes = s.getBytes();
        for (byte b : bytes) {
            assertFalse((b & 0x80) != 0x00);
        }
    }

    @Test
    public void simpleASCII() {
        String[] simple = new String[]{
            "",
            "Answer is 42",
            "\n",
            "Answer is 42\n"
        };
        for (String s : simple) {
            String decoded = Junidecode.unidecode(s);
            assertEquals(s, decoded);
            sevenBitPurity(decoded);
        }
    }

    /**
     * Testing <em>all</em> the values is infeasible. Let's test just some known
     * strings and hope that everything is ok. This test is copied from Sean M.
     * Burke's Unidecode Perl module
     * (<a href="http://interglacial.com/~sburke/tpj/as_html/tpj22.html">
     * http://interglacial.com/~sburke/tpj/as_html/tpj22.html</a>.
     */
    @Test
    public void someTests() {
        StringPair[] spvalues = new StringPair[]{
            new StringPair("\u00C6neid", "AEneid"),
            new StringPair("\u00e9tude", "etude"),
            // Chinese
            new StringPair("\u5317\u4eb0", "Bei Jing "),
            // Canadian syllabics
            new StringPair("\u1515\u14c7\u14c7", "shanana"),
            // Cherokee
            new StringPair("\u13d4\u13b5\u13c6", "taliqua"),
            // Syriac
            new StringPair("\u0726\u071b\u073d\u0710\u073a", "ptu'i"),
            //Devangari
            new StringPair("\u0905\u092d\u093f\u091c\u0940\u0924", "abhijiit"),
            // Bengali
            new StringPair("\u0985\u09ad\u09bf\u099c\u09c0\u09a4", "abhijiit"),
            //Malayalaam
            new StringPair("\u0d05\u0d2d\u0d3f\u0d1c\u0d40\u0d24", "abhijiit"),
            /*
             * Malayalaam. The correct transliteration is "malayaalam", but
             * since junidecode is context insentitive this is the best we can
             * do.
             */
            new StringPair("\u0d2e\u0d32\u0d2f\u0d3e\u0d32\u0d2e\u0d4d", "mlyaalm"),
            // Japanese
            new StringPair("\u3052\u3093\u307e\u3044\u8336", "genmaiCha ")
        };
        for (StringPair sp : spvalues) {
            String decoded = Junidecode.unidecode(sp.getLeft());
            assertEquals(sp.getRight(), decoded);
            sevenBitPurity(decoded);
        }
    }

    /**
     * Simple nested class to hold pair values.
     */
    private class StringPair {

        private final String left;
        private final String right;

        public StringPair(final String left, final String right) {
            this.left = left;
            this.right = right;
        }

        public String getLeft() {
            return left;
        }

        public String getRight() {
            return right;
        }

        @Override
        public int hashCode() {
            return left.hashCode() ^ right.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (!(o instanceof StringPair)) {
                return false;
            }
            StringPair pairo = (StringPair) o;
            return this.left.equals(pairo.getLeft()) &&
                    this.right.equals(pairo.getRight());
        }
    }
}
