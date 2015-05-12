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

/**
 * Root class of JUnidecode.
 * @author Giuseppe Cardone
 * @version 0.1
 */
public class Junidecode {

    /**
     * Array to cache already loaded maps.
     */
    private static final String[][] cache = new String[256][];

    /**
     * Private constructor to avoid instantiation.
     */
    private Junidecode() {
    }

    /**
     * Strip diacritic marks and transliterates a unicode string to a valid
     * 7-bit ASCII String.
     * @since 0.1
     * @param s Unicode String to transliterate.
     * @return 7-bit ASCII valid string.
     */
    public static String unidecode(final String s) {
        StringBuilder sb = new StringBuilder();
        String[] map;
        for (int i = 0; i < s.length(); i++) {
            int codepoint = s.codePointAt(i);
            int hi = (codepoint >> 8) & 0xff;
            int low = codepoint & 0xff;
            /*
             * Try to load the code mapping from cache. We could of course keep
             * a big String[256][256] in memory - which would be a little bit
             * faster, but using this array we can keep the memory footprint
             * smaller since the class loader loads the needed classes lazily.
             * When transliterating from cyrillic we'll never load hiragana
             * or greek mappings.
             */
            map = cache[hi];
            if (null == map) {
                switch (hi) {
                    case 0x00:
                        map = X00.map;
                        break;
                    case 0x01:
                        map = X01.map;
                        break;
                    case 0x02:
                        map = X02.map;
                        break;
                    case 0x03:
                        map = X03.map;
                        break;
                    case 0x04:
                        map = X04.map;
                        break;
                    case 0x05:
                        map = X05.map;
                        break;
                    case 0x06:
                        map = X06.map;
                        break;
                    case 0x07:
                        map = X07.map;
                        break;
                    case 0x09:
                        map = X09.map;
                        break;
                    case 0x0a:
                        map = X0a.map;
                        break;
                    case 0x0b:
                        map = X0b.map;
                        break;
                    case 0x0c:
                        map = X0c.map;
                        break;
                    case 0x0d:
                        map = X0d.map;
                        break;
                    case 0x0e:
                        map = X0e.map;
                        break;
                    case 0x0f:
                        map = X0f.map;
                        break;
                    case 0x10:
                        map = X10.map;
                        break;
                    case 0x11:
                        map = X11.map;
                        break;
                    case 0x12:
                        map = X12.map;
                        break;
                    case 0x13:
                        map = X13.map;
                        break;
                    case 0x14:
                        map = X14.map;
                        break;
                    case 0x15:
                        map = X15.map;
                        break;
                    case 0x16:
                        map = X16.map;
                        break;
                    case 0x17:
                        map = X17.map;
                        break;
                    case 0x18:
                        map = X18.map;
                        break;
                    case 0x1e:
                        map = X1e.map;
                        break;
                    case 0x1f:
                        map = X1f.map;
                        break;
                    case 0x20:
                        map = X20.map;
                        break;
                    case 0x21:
                        map = X21.map;
                        break;
                    case 0x22:
                        map = X22.map;
                        break;
                    case 0x23:
                        map = X23.map;
                        break;
                    case 0x24:
                        map = X24.map;
                        break;
                    case 0x25:
                        map = X25.map;
                        break;
                    case 0x26:
                        map = X26.map;
                        break;
                    case 0x27:
                        map = X27.map;
                        break;
                    case 0x28:
                        map = X28.map;
                        break;
                    case 0x2e:
                        map = X2e.map;
                        break;
                    case 0x2f:
                        map = X2f.map;
                        break;
                    case 0x30:
                        map = X30.map;
                        break;
                    case 0x31:
                        map = X31.map;
                        break;
                    case 0x32:
                        map = X32.map;
                        break;
                    case 0x33:
                        map = X33.map;
                        break;
                    case 0x4d:
                        map = X4d.map;
                        break;
                    case 0x4e:
                        map = X4e.map;
                        break;
                    case 0x4f:
                        map = X4f.map;
                        break;
                    case 0x50:
                        map = X50.map;
                        break;
                    case 0x51:
                        map = X51.map;
                        break;
                    case 0x52:
                        map = X52.map;
                        break;
                    case 0x53:
                        map = X53.map;
                        break;
                    case 0x54:
                        map = X54.map;
                        break;
                    case 0x55:
                        map = X55.map;
                        break;
                    case 0x56:
                        map = X56.map;
                        break;
                    case 0x57:
                        map = X57.map;
                        break;
                    case 0x58:
                        map = X58.map;
                        break;
                    case 0x59:
                        map = X59.map;
                        break;
                    case 0x5a:
                        map = X5a.map;
                        break;
                    case 0x5b:
                        map = X5b.map;
                        break;
                    case 0x5c:
                        map = X5c.map;
                        break;
                    case 0x5d:
                        map = X5d.map;
                        break;
                    case 0x5e:
                        map = X5e.map;
                        break;
                    case 0x5f:
                        map = X5f.map;
                        break;
                    case 0x60:
                        map = X60.map;
                        break;
                    case 0x61:
                        map = X61.map;
                        break;
                    case 0x62:
                        map = X62.map;
                        break;
                    case 0x63:
                        map = X63.map;
                        break;
                    case 0x64:
                        map = X64.map;
                        break;
                    case 0x65:
                        map = X65.map;
                        break;
                    case 0x66:
                        map = X66.map;
                        break;
                    case 0x67:
                        map = X67.map;
                        break;
                    case 0x68:
                        map = X68.map;
                        break;
                    case 0x69:
                        map = X69.map;
                        break;
                    case 0x6a:
                        map = X6a.map;
                        break;
                    case 0x6b:
                        map = X6b.map;
                        break;
                    case 0x6c:
                        map = X6c.map;
                        break;
                    case 0x6d:
                        map = X6d.map;
                        break;
                    case 0x6e:
                        map = X6e.map;
                        break;
                    case 0x6f:
                        map = X6f.map;
                        break;
                    case 0x70:
                        map = X70.map;
                        break;
                    case 0x71:
                        map = X71.map;
                        break;
                    case 0x72:
                        map = X72.map;
                        break;
                    case 0x73:
                        map = X73.map;
                        break;
                    case 0x74:
                        map = X74.map;
                        break;
                    case 0x75:
                        map = X75.map;
                        break;
                    case 0x76:
                        map = X76.map;
                        break;
                    case 0x77:
                        map = X77.map;
                        break;
                    case 0x78:
                        map = X78.map;
                        break;
                    case 0x79:
                        map = X79.map;
                        break;
                    case 0x7a:
                        map = X7a.map;
                        break;
                    case 0x7b:
                        map = X7b.map;
                        break;
                    case 0x7c:
                        map = X7c.map;
                        break;
                    case 0x7d:
                        map = X7d.map;
                        break;
                    case 0x7e:
                        map = X7e.map;
                        break;
                    case 0x7f:
                        map = X7f.map;
                        break;
                    case 0x80:
                        map = X80.map;
                        break;
                    case 0x81:
                        map = X81.map;
                        break;
                    case 0x82:
                        map = X82.map;
                        break;
                    case 0x83:
                        map = X83.map;
                        break;
                    case 0x84:
                        map = X84.map;
                        break;
                    case 0x85:
                        map = X85.map;
                        break;
                    case 0x86:
                        map = X86.map;
                        break;
                    case 0x87:
                        map = X87.map;
                        break;
                    case 0x88:
                        map = X88.map;
                        break;
                    case 0x89:
                        map = X89.map;
                        break;
                    case 0x8a:
                        map = X8a.map;
                        break;
                    case 0x8b:
                        map = X8b.map;
                        break;
                    case 0x8c:
                        map = X8c.map;
                        break;
                    case 0x8d:
                        map = X8d.map;
                        break;
                    case 0x8e:
                        map = X8e.map;
                        break;
                    case 0x8f:
                        map = X8f.map;
                        break;
                    case 0x90:
                        map = X90.map;
                        break;
                    case 0x91:
                        map = X91.map;
                        break;
                    case 0x92:
                        map = X92.map;
                        break;
                    case 0x93:
                        map = X93.map;
                        break;
                    case 0x94:
                        map = X94.map;
                        break;
                    case 0x95:
                        map = X95.map;
                        break;
                    case 0x96:
                        map = X96.map;
                        break;
                    case 0x97:
                        map = X97.map;
                        break;
                    case 0x98:
                        map = X98.map;
                        break;
                    case 0x99:
                        map = X99.map;
                        break;
                    case 0x9a:
                        map = X9a.map;
                        break;
                    case 0x9b:
                        map = X9b.map;
                        break;
                    case 0x9c:
                        map = X9c.map;
                        break;
                    case 0x9d:
                        map = X9d.map;
                        break;
                    case 0x9e:
                        map = X9e.map;
                        break;
                    case 0x9f:
                        map = X9f.map;
                        break;
                    case 0xa0:
                        map = Xa0.map;
                        break;
                    case 0xa1:
                        map = Xa1.map;
                        break;
                    case 0xa2:
                        map = Xa2.map;
                        break;
                    case 0xa3:
                        map = Xa3.map;
                        break;
                    case 0xa4:
                        map = Xa4.map;
                        break;
                    case 0xac:
                        map = Xac.map;
                        break;
                    case 0xad:
                        map = Xad.map;
                        break;
                    case 0xae:
                        map = Xae.map;
                        break;
                    case 0xaf:
                        map = Xaf.map;
                        break;
                    case 0xb0:
                        map = Xb0.map;
                        break;
                    case 0xb1:
                        map = Xb1.map;
                        break;
                    case 0xb2:
                        map = Xb2.map;
                        break;
                    case 0xb3:
                        map = Xb3.map;
                        break;
                    case 0xb4:
                        map = Xb4.map;
                        break;
                    case 0xb5:
                        map = Xb5.map;
                        break;
                    case 0xb6:
                        map = Xb6.map;
                        break;
                    case 0xb7:
                        map = Xb7.map;
                        break;
                    case 0xb8:
                        map = Xb8.map;
                        break;
                    case 0xb9:
                        map = Xb9.map;
                        break;
                    case 0xba:
                        map = Xba.map;
                        break;
                    case 0xbb:
                        map = Xbb.map;
                        break;
                    case 0xbc:
                        map = Xbc.map;
                        break;
                    case 0xbd:
                        map = Xbd.map;
                        break;
                    case 0xbe:
                        map = Xbe.map;
                        break;
                    case 0xbf:
                        map = Xbf.map;
                        break;
                    case 0xc0:
                        map = Xc0.map;
                        break;
                    case 0xc1:
                        map = Xc1.map;
                        break;
                    case 0xc2:
                        map = Xc2.map;
                        break;
                    case 0xc3:
                        map = Xc3.map;
                        break;
                    case 0xc4:
                        map = Xc4.map;
                        break;
                    case 0xc5:
                        map = Xc5.map;
                        break;
                    case 0xc6:
                        map = Xc6.map;
                        break;
                    case 0xc7:
                        map = Xc7.map;
                        break;
                    case 0xc8:
                        map = Xc8.map;
                        break;
                    case 0xc9:
                        map = Xc9.map;
                        break;
                    case 0xca:
                        map = Xca.map;
                        break;
                    case 0xcb:
                        map = Xcb.map;
                        break;
                    case 0xcc:
                        map = Xcc.map;
                        break;
                    case 0xcd:
                        map = Xcd.map;
                        break;
                    case 0xce:
                        map = Xce.map;
                        break;
                    case 0xcf:
                        map = Xcf.map;
                        break;
                    case 0xd0:
                        map = Xd0.map;
                        break;
                    case 0xd1:
                        map = Xd1.map;
                        break;
                    case 0xd2:
                        map = Xd2.map;
                        break;
                    case 0xd3:
                        map = Xd3.map;
                        break;
                    case 0xd4:
                        map = Xd4.map;
                        break;
                    case 0xd5:
                        map = Xd5.map;
                        break;
                    case 0xd6:
                        map = Xd6.map;
                        break;
                    case 0xd7:
                        map = Xd7.map;
                        break;
                    case 0xf9:
                        map = Xf9.map;
                        break;
                    case 0xfa:
                        map = Xfa.map;
                        break;
                    case 0xfb:
                        map = Xfb.map;
                        break;
                    case 0xfc:
                        map = Xfc.map;
                        break;
                    case 0xfd:
                        map = Xfd.map;
                        break;
                    case 0xfe:
                        map = Xfe.map;
                        break;
                    case 0xff:
                        map = Xff.map;
                        break;
                    default:
                        continue;
                }
                /*
                 * Cache the new map using the high byte of the code point
                 * as index.
                 */
                cache[hi] = map;
            }
            /*
             * Some code maps contain only 254 elements because the last
             * one is reserved.
             */
            if (low < map.length) {
                sb.append(map[low]);
            }
        }
        return sb.toString();
    }
}
