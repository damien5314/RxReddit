package rxreddit.api;


class StringUtils {

    /**
     * Returns a string containing the tokens joined by delimiters.
     *
     * @param tokens an array objects to be joined. Strings will be formed from
     *               the objects by calling object.toString().
     */
    public static String join(CharSequence delimiter, Object[] tokens) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (Object token : tokens) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(token);
        }
        return sb.toString();
    }

    /**
     * Returns a string containing the tokens joined by delimiters.
     *
     * @param tokens an array objects to be joined. Strings will be formed from
     *               the objects by calling object.toString().
     */
    public static String join(CharSequence delimiter, Iterable tokens) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (Object token : tokens) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(token);
        }
        return sb.toString();
    }

    public static boolean isEmpty(String string) {
        return string == null || "".equals(string);
    }

    public static String getCommaDelimitedString(Iterable<String> string) {
        StringBuilder commaDelimited = new StringBuilder();
        for (String subreddit : string) {
            commaDelimited.append(subreddit).append(",");
        }
        commaDelimited.deleteCharAt(commaDelimited.length()-1);
        return commaDelimited.toString();
    }
}
