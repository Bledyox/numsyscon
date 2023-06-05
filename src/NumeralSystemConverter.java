public final class NumeralSystemConverter {

	private static String BASE_SYSTEM_TABLE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private NumeralSystemConverter() {}

	private static String checkException(String val, int val_base, int base) {
		if (val_base == 1) {
			if (val.toUpperCase().replaceAll("[1]", "").length() != 0) {
				return "nsc.exception.val";
			}
		} else if (val.toUpperCase().replaceAll("[" + BASE_SYSTEM_TABLE.substring(0, val_base) + "]", "")
				.length() != 0) {
			return "nsc.exception.val";
		} else if (base <= 0 || val_base <= 0 || base > BASE_SYSTEM_TABLE.length()
				|| val_base > BASE_SYSTEM_TABLE.length()) {
			return "nsc.exception.base";
		}
		return "";
	}

	public static String parse(String val, int val_base, int base) throws NumeralSystemConverterException {
		String check = checkException(val, val_base, base);
		if (check.length() != 0) {
			throw new NumeralSystemConverterException(check);
		} else if (val_base == base) {
			return val;
		} else if (base == 1) {
			return parseToUnary(val, val_base);
		} else if (val_base == 10) {
			return parseFromDecimal(Integer.parseInt(val), base);
		} else if (base == 10) {
			return Integer.toString(parseToDecimal(val, val_base));
		} else {
			return parseFromDecimal(parseToDecimal(val, val_base), base);
		}
	}

	public static int parseToDecimal(String val, int base) throws NumeralSystemConverterException {
		String check = checkException(val, base, 10);
		if (check.length() != 0) {
			throw new NumeralSystemConverterException(check);
		}
		int buf = 0;
		for (int i = 0; i < val.length(); i++) {
			buf += BASE_SYSTEM_TABLE.indexOf(val.charAt(i)) * Math.pow(base, val.length() - 1 - i);
		}
		return buf;
	}

	public static String parseFromDecimal(int val, int base) throws NumeralSystemConverterException {
		String check = checkException(Integer.toString(val), 10, base);
		if (check.length() != 0) {
			throw new NumeralSystemConverterException(check);
		}
		String buf = "";
		while (val != 0) {
			buf += BASE_SYSTEM_TABLE.charAt(val % base);
			val /= base;
		}
		return new StringBuffer(buf).reverse().toString();
	}

	public static String parseToUnary(String val, int base) throws NumeralSystemConverterException {
		String check = checkException(val, base, 1);
		if (check.length() != 0) {
			throw new NumeralSystemConverterException(check);
		}
		String buf = "";
		for (int i = 0; i < parseToDecimal(val, base); i++) {
			buf += "1";
		}
		return buf;
	}

}
