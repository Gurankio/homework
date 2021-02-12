package gurankio.util;

// Source: https://bitbucket.org/clearer/iosifovitch/src/master/
public class Iosifovitch {

    public static Integer levenshtein(String a, String b) {
        if (a.equalsIgnoreCase(b)) return 0;
        if (a.length() < b.length()) {
            String t = a;
            a = b;
            b = t;
        }

        // reduce(a, b)
        int prefix;
        for (prefix=0; prefix<b.length(); prefix++) if (a.charAt(prefix) != b.charAt(prefix)) break;
        a = a.substring(prefix);
        b = b.substring(prefix);
        int suffix;
        for (suffix=0; suffix<b.length(); suffix++) if (a.charAt(a.length() - 1 - suffix) != b.charAt(b.length() - 1 - suffix)) break;
        a = a.substring(0, a.length() - suffix);
        b = b.substring(0, b.length() - suffix);

        // iota
        int[] buffer = new int[b.length() + 1];
        for (int i=0; i<buffer.length; i++) buffer[i] = i;

        // dynamic
        for (int i = 1; i < a.length() + 1; i++) {
            int temp = buffer[0]++;
            for (int j = 1; j < buffer.length; j++) {
                int p = buffer[j - 1];
                int r = buffer[j];
                temp = Math.min(
                        Math.min(r, p) + 1,
                        temp + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1)
                );
                int swap = buffer[j];
                buffer[j] = temp;
                temp = swap;
            }
        }

        return buffer[buffer.length - 1];
    }

}
