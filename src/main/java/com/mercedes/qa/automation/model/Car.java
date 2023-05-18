package com.mercedes.qa.automation.model;

import java.math.BigDecimal;
import java.util.Currency;

public class Car {

    private final String model;
    private final BigDecimal price;
    private final Currency currency;
    private final BigDecimal mileage;
    private final int year;
    private final FuelType fuelType;

    // Private constructor to enforce the use of the builder
    private Car(final Builder builder) {
        this.model = builder.model;
        this.price = builder.price;
        this.currency = builder.currency;
        this.mileage = builder.mileage;
        this.year = builder.year;
        this.fuelType = builder.fuelType;
    }

    // Getters for all the fields

    public String getModel() {
        return model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getMileage() {
        return mileage;
    }

    public int getYear() {
        return year;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public static class Builder {
        private String model;
        private BigDecimal price;
        private Currency currency;
        private BigDecimal mileage;
        private int year;
        private FuelType fuelType;

        public Builder() {
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public Builder mileage(BigDecimal mileage) {
            this.mileage = mileage;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder fuelType(FuelType fuelType) {
            this.fuelType = fuelType;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }
}

