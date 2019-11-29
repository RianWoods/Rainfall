package cs3318.datastore;

import cs3318.exceptions.IllegalRainfallDataSourceException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Class extends RainfallDataSource, generates random rainfall data previous 10-110 days
 * from current date.
 */
public class RainfallDataSourceRandom extends RainfallDataSource {
    public RainfallDataSourceRandom(String station) throws IllegalRainfallDataSourceException {
        super(station, "Random Source");
    }

    /**
     * getDataFrom overridden to not get data from a data source but generate random
     * rainfall data between 10 and 110 days previous from current date.
     * @param source String is ignored as class has no data source.
     * @throws IllegalRainfallDataSourceException never thrown as data source never used
     */
    @Override
    protected void getDataFrom(String source) throws IllegalRainfallDataSourceException {
        Random numberGenerator = new Random();
        int randomSize = 10 + numberGenerator.nextInt(100);
        this.precipitation = new ArrayList<>(randomSize);
        this.recordingDates = new HashMap<>(randomSize);

        for (int i = 0; i < this.precipitation.size(); i += 1) {
            this.precipitation.set(i, numberGenerator.nextDouble());
            this.recordingDates.put(i, this.generateDate(i));
        }
    }

    private LocalDate generateDate(int i) {
        LocalDate today =  LocalDate.now();

        return today.minusDays(i);
    }
}
