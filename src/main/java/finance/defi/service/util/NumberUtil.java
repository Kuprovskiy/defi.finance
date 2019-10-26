package finance.defi.service.util;

public final class NumberUtil {

    private NumberUtil() {
    }

    public static boolean isValidLong(String code) {
        try {
            Long.parseLong(code);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
