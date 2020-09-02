package martin.dev.pricer.state;

public class ScraperTools {

    public static Integer parseIntegerFromString(String string) {
        String digits = string.replaceAll("[^\\d]", "");
        return Integer.parseInt(digits);
    }

    public static Double parseDoubleFromString(String string) {
        String digits = string.replaceAll("[^\\d.]", "");
        return Double.parseDouble(digits);
    }
}
