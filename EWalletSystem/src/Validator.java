import java.util.regex.Pattern;

public final class Validator {
    private static final Pattern USERNAME = Pattern.compile("[A-Z][A-Za-z0-9_]{2,19}");
    private static final Pattern PASSWORD = Pattern.compile("(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}");
    private static final Pattern EGYPT_PHONE = Pattern.compile("01[0125][0-9]{8}");

    private Validator() {}

    public static boolean validUsername(String username) {
        return username != null && USERNAME.matcher(username).matches();
    }

    public static boolean validPassword(String password) {
        return password != null && PASSWORD.matcher(password).matches();
    }

    public static boolean validAge(int age) {
        return age >= 18;
    }

    public static boolean validPhone(String phone) {
        return phone != null && EGYPT_PHONE.matcher(phone).matches();
    }
}