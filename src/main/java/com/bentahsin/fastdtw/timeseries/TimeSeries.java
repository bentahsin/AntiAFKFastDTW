package com.bentahsin.fastdtw.timeseries;

public class TimeSeries {

   private final double[][] data;

   private final int size;
   private final int dimensions;

   public TimeSeries(double[][] data) {
      this.data = data;
      this.size = data.length;
      this.dimensions = (size > 0) ? data[0].length : 0;
   }

   public int size() {
      return size;
   }

   public int numOfDimensions() {
      return dimensions;
   }

   public double[] getMeasurementVector(int index) {
      return data[index];
   }

   public double getMeasurement(int pointIndex, int valueIndex) {
      return data[pointIndex][valueIndex];
   }
}