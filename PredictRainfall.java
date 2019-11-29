package cs3318.application;

import cs3318.datastore.RainfallDataSource;
import cs3318.datastore.RainfallDataSourceCSV;
import cs3318.datastore.RainfallDataSourceRandom;
import cs3318.exceptions.IllegalRainfallDataSourceException;
import cs3328.model.AveragingPrediction;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Main class
 */
public class PredictRainfall {
    /**
     * RainfallDataSource called to process dataSource file.
     * AveragingPrediction called on returned data to generate rainfall prediction
     * @throws IllegalRainfallDataSourceException thrown if there error occurs accessing data source
     */
    public static void main(String [] args) throws IllegalRainfallDataSourceException {
        RainfallDataSource corkAirport;
        AveragePrediction prediction;
        String date = "22-Nov-2019";
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        LocalDate ld = LocalDate.parse(date, f);

        try {
            corkAirport = new RainfallDataSourceCSV("Cork Airport", "resources/Rainfall-daily-cork-airport-1962-present.csv");
            prediction = new AveragingPrediction(corkAirport);
            System.out.printf("%.1f\n", prediction.predict(ld));
        }
        catch (IllegalRainfallDataSourceException e) {
            corkAirport = new RainfallDataSourceRandom("Cork Airport (dummy)");
        }

        corkAirport.getRecordingDates().forEach(System.out::println);
        corkAirport.getPrecipitation().forEach(System.out::println);
    }
}
