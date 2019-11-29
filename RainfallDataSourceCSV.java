package cs3318.datastore;

import cs3318.exceptions.IllegalRainfallDataSourceException;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Class extends RainfallDataSource to read from CSV file.
 */
public class RainfallDataSourceCSV extends RainfallDataSource {
    public RainfallDataSourceCSV(String station, String dataSource) throws IllegalRainfallDataSourceException {
        super(station, dataSource);
    }

    /**
     * Override attempts to read data one line at a time using the readCSVDatafrom() method.
     * If error, then eception is thrown.
     * @param source String representing Rainfall Data.
     * @throws IllegalRainfallDataSourceException
     */
    @Override
    protected void getDataFrom(String source) throws IllegalRainfallDataSourceException {
        try (Scanner scanner = new Scanner(new File(this.getDataSource()))) {
            this.readCSVDataFrom(scanner);
        } catch (FileNotFoundException e) {
            throw new IllegalRainfallDataSourceException();
        }
    }

    private void readCSVDataFrom(Scanner scanner) {
        Integer recordNumber = 0;
        this.precipitation = new ArrayList<>();
        this.recordingDates = new HashMap<>();

        while (scanner.hasNext()) {
            String record = scanner.next();
            try {
                this.recordingDates.put(recordNumber, this.getDateFrom(record));
                this.precipitation.add(this.getPrecipitationFrom(record));
                recordNumber += 1;
            } catch (NumberFormatException | DateTimeParseException e) {
                // ignore the record ... is this a wise course of action
                System.out.println(e);
            }
        }
    }

    private Double getPrecipitationFrom(String line) {
        String[] fields = line.split(",");

        return Double.parseDouble(fields[1]);

    }

    /**
     * Distinguishes rainfall data and date from line from dataSource and formats date
     * to localDate. Refractored code adds 19 or 20 to beginning of each year.
     * @param line String from the dataSource.
     * @return ld localDate rainfall occurred on.
     */
    private LocalDate getDateFrom(String line) {
        String[] fields = line.split(",");
        String[] holds = fields[0].split("-");

        int year = Integer.parseInt(holds[2]);
        if(year<62){
            holds[2] = "20" + holds[2];
        } else{
            holds[2] = "19" + holds[2];
        }
        fields[0] = holds[0] + "-" + holds[1] + "-" + holds[2];

        DateTimeFormatter f = DateTimeFormatter.ofPattern( "dd-MMM-yy" );
        LocalDate ld = LocalDate.parse( fields[0], f );

        return ld;
    }
}
