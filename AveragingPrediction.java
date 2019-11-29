package cs3318.model;
import cs3318.exceptions.IllegalRainfallDataSourceException;
import cs3318.datastore.RainfallDataSource;
import java.io.Console;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

/**
 * Class extends RainfallPrediction to calculate average rainfall
 * prediction from 3 previous samples.
 */
public class AveragingPrediction extends RainfallPrediction {
    private int numberOfSamples = 3;

    public AveragingPrediction(RainfallDataSource source) {
        super(source);
    }

    public void setNumberOfSamples(int n) {
        this.numberOfSamples = n;
    }

    /**
     * Makes Rainfall prediction for a future date.
     * If non-valid argument is entered, an Exception is thrown
     * @param date LocalDate date for prediction
     * @return Double that makes prediction
     */
    @Override
    public Double predict(LocalDate date) {
        Double [] predictionData = new Double[this.numberOfSamples];
        LocalDate sampleYear = date.minus(1, ChronoUnit.YEARS);

        for (int i = 0; i < this.numberOfSamples; i += 1) {
            /**
             * Ensures a nonNull value for sampleYear
             */
            if(sampleYear != null){
                try{
                    Integer indexOfDate = data.getRecordingDates().indexOf(sampleYear);
                    predictionData[i] = data.getPrecipitation().get(i);
                    sampleYear = date.minus(1, ChronoUnit.YEARS);
                }
                catch(IndexOutOfBoundsException e){
                    System.out.ptintln("Index is out of bounds" + e.getMessage());
                }
            }
        }

        return Arrays.stream(predictionData).mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
    }
}
