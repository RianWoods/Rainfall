package cs3318.model;

import cs3318.datastore.RainfallDataSource;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Predict the rainfall a date post 30th sep 2019,
 * using an abstract class.
 */
public abstract class RainfallPrediction {
    protected RainfallDataSource data;

    /**
     * Set and ensure value of data property of class is nonNull
     * @param source RainfallDataSource object containing rainfall data
     */
    public RainfallPrediction(RainfallDataSource source) {
        Objects.requireNonNull(source);
        this.data = source;
    }

    public abstract Double predict(LocalDate date);
}