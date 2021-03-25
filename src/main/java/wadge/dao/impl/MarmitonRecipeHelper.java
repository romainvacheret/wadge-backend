package wadge.dao.impl;

public class MarmitonRecipeHelper {
    private MarmitonRecipeHelper() {}

    public static int timeToMinutes(String time) {
		String[] arr = time.replace("\n", "").split(" ");
		String[] arr2 = arr[0].split("h");
		int result = Integer.valueOf(arr2[0].replace(" ", ""));
		if(arr2.length == 2 || arr[1].contains("h")) {
			result *= 60;
			if(arr2.length == 2) {
				result += Integer.valueOf(arr2[1]);
			}
		}
		return result;
	}

	public static int convertDifficulty(String difficulty) {
		int rtr = 0;
		switch(difficulty) {
			case "très facile":
				rtr = 1;
				break;
			case "facile":
				rtr = 2;
				break;
			case "Niveau moyen":
				rtr = 3;
				break;
			case "difficile":
				rtr = 4;
				break;
			default:
				rtr = 0;
		}
		return rtr;
	}
}
