import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

	private static final String FILE = "properties.messages";

	private Messages() {
	}

	public static String getString(String key, String language) {
		ResourceBundle res_bundle;
		if (language.equals("")) {
			res_bundle = ResourceBundle.getBundle(FILE + language);
		} else {
			res_bundle = ResourceBundle.getBundle(FILE + "_" + language);
		}
		System.out.println(res_bundle.getKeys().toString());
		try {
			return res_bundle.getString(key);
		} catch (MissingResourceException e) {
			return "!!! " + key + " !!!";
		}
	}

	public static String[] getStringPack(String key, int start, int stop, String language) {
		String[] buffer = new String[stop];
		for (int i = 0; i < stop; i++) {
			buffer[i] = getString(key + (i + 1), language);
		}
		return buffer;
	}
}
