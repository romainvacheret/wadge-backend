package wadge.service.fridge;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.time.DateUtils;

import wadge.model.fridge.FoodElement;
import wadge.service.fridge.FridgeService.RecallType;

public class FoodElementPredicatesFactory {
    private SimpleDateFormat dateFormater;
    private Date currentDate;
    private static FoodElementPredicatesFactory instance;
    private static Logger logger = Logger.getLogger(FoodElementPredicatesFactory.class.getName());


    private FoodElementPredicatesFactory() {
        this.dateFormater = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        this.currentDate = new Date();
        logger.log(Level.INFO, "test");
    }

    protected static long dateDifference(Date d1, Date d2) {
        d1 = DateUtils.truncate(d1, java.util.Calendar.DAY_OF_MONTH);
        d2 = DateUtils.truncate(d2, java.util.Calendar.DAY_OF_MONTH);
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
            logger.log(Level.FINE, e.getMessage(), e);
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
                        return result < 6 && result > 2;
                    } ;
                    break;
                case SEVEN_DAYS:
                    rtr = x -> {
                        long result = this.dateProcessing(x.getPeremptionDate());
                        return result < 8 && result > 5;
                    } ;
                    break;
                case FORTEEN_DAYS:
                    rtr = x -> {
                        long result = this.dateProcessing(x.getPeremptionDate());
                        return result < 15 && result > 7;
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

    public static void main(String[] args) {
        new FoodElementPredicatesFactory().getPredicate(RecallType.EXPIRED);
    }
}
