package wadge.fridge.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import wadge.fridge.impl.ExpirationRecall.RecallType;

public class FoodElementPredicatesFactory {
    private SimpleDateFormat dateFormater;
    private Date currentDate;
    private static FoodElementPredicatesFactory instance;

    private FoodElementPredicatesFactory() {}

    private static long dateDifference(Date d1, Date d2) {
        long diff = d1.getTime() - d2.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static FoodElementPredicatesFactory getInstance() {
        if(instance == null) {
            instance = new FoodElementPredicatesFactory();
        }
        return instance;
    }

    private long dateProcessing(String date) {
        long difference = 0;
        try {
            difference =  dateDifference(dateFormater.parse(date), this.currentDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return difference;
    }

    public Predicate<FoodElement> getPredicate(RecallType type) {
        Predicate<FoodElement> rtr;
            switch(type) {
                case TWO_DAYS:
                    rtr = x -> {
                        long result = this.dateProcessing(x.getPeremptionDate());
                        return result < 3 && result >= 0;
                    } ;
                    break;
                case FIVE_DAYS:
                    rtr = x -> {
                        long result = this.dateProcessing(x.getPeremptionDate());
                        return result < 5 && result >= 2;
                    } ;
                    break;
                case SEVEN_DAYS:
                    rtr = x -> {
                        long result = this.dateProcessing(x.getPeremptionDate());
                        return result < 7 && result >= 5;
                    } ;
                    break;
                case FORTEEN_DAYS:
                    rtr = x -> {
                        long result = this.dateProcessing(x.getPeremptionDate());
                        return result < 14 && result >= 7;
                    } ;
                    break;
                case EXPIRED:
                    rtr = x -> this.dateProcessing(x.getPeremptionDate()) < 0;
                    break;
                default:
                    rtr = x -> this.dateProcessing(x.getPeremptionDate()) > 14;
            }
        return rtr;
    }
}
