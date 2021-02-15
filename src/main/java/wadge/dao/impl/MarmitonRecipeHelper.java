package wadge.dao.impl;

public class MarmitonRecipeHelper {
    private MarmitonRecipeHelper() {}

    public static int timeToMinutes(String time) {
		String[] arr = time.split(" ");
		String[] arr2 = arr[0].split("h");
		if(arr2.length == 2) {
			return Integer.valueOf(arr2[0]) * 60 + Integer.valueOf(arr2[1]);
		} else {
			return Integer.valueOf(arr2[0]);
		}
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
