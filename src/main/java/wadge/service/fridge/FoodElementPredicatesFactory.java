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
    private final SimpleDateFormat dateFormatter;
    private final Date currentDate;
    private static FoodElementPredicatesFactory instance;

    private FoodElementPredicatesFactory() {
        this.dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        this.currentDate = new Date();
    }

    public static FoodElementPredicatesFactory getInstance() {
        if(instance == null) {
            instance = new FoodElementPredicatesFactory();
        }
        return instance;
    }

    protected static long dateDifference(final Date d1, final Date d2) {
        final Date truncatedD1 = DateUtils.truncate(d1, java.util.Calendar.DAY_OF_MONTH);
        final Date truncatedD2 = DateUtils.truncate(d2, java.util.Calendar.DAY_OF_MONTH);
        final long diff = truncatedD1.getTime() - truncatedD2.getTime();

        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    private long dateProcessing(final String date) {
        long difference = 0;
        try {
            difference =  dateDifference(dateFormatter.parse(date), this.currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return difference;
    }

    // TODO refactor -> use directly FoodElement::getExpirationDate's value ?
    public Predicate<FoodElement> getPredicate(final RecallType type) {
        return switch(type) {
            case TWO_DAYS -> x -> {
                    final long result = dateProcessing(x.getPeremptionDate());
                    return result < 3 && result >= 0;
                } ;
            case FIVE_DAYS -> x -> {
                    final long result = dateProcessing(x.getPeremptionDate());
                    return result < 6 && result > 2;
                } ;
            case SEVEN_DAYS -> x -> {
                    final long result = dateProcessing(x.getPeremptionDate());
                    return result < 8 && result > 5;
                } ;
            case FOURTEEN_DAYS -> x -> {
                    final long result = dateProcessing(x.getPeremptionDate());
                    return result < 15 && result > 7;
                } ;
            case EXPIRED -> x -> dateProcessing(x.getPeremptionDate()) < 0;
            default -> x -> dateProcessing(x.getPeremptionDate()) > 14;
        };
    }
}
