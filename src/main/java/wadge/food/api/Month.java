package wadge.food.api;

public enum Month {
    JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUN, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER;
    
    public String valueOf() {
        switch(this) {
            case JANUARY:
                return "janvier";
            case FEBRUARY:
                return "fevrier";
            case MARCH:
                return "mars";
            case APRIL:
                return "avril";
            case MAY:
                return "mai";
            case JUN:
                return "juin";
            case JULY:
                return "juillet";
            case AUGUST:
                return "aout";
            case SEPTEMBER:
                return "septembre";
            case OCTOBER:
                return "octobre";
            case NOVEMBER:
                return "nomvembre";
            case DECEMBER:
                return "decembre";
            default:
                return "";
        }
    }
}
