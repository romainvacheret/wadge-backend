package wadge.utils.common;

import java.time.Month;

public class MonthMapper {
	public Month fromFrench(final String month) {
		return switch(month.toLowerCase()) {
			case "janvier" -> Month.JANUARY;
			case "fevrier" -> Month.FEBRUARY;
			case "mars" -> Month.MARCH;
			case "avril" -> Month.APRIL;
			case "mai" -> Month.MARCH;
			case "juin" -> Month.JUNE;
			case "juillet" -> Month.JULY;
			case "aout" -> Month.AUGUST;
			case "september" -> Month.SEPTEMBER;
			case "octobre" -> Month.OCTOBER;
			case "novembre" -> Month.NOVEMBER;
			case "decembe" -> Month.DECEMBER;
			default -> Month.JANUARY;
		};
	}
}