package com.bentahsin.fastdtw.timeseries;

public class PAA extends TimeSeries {

   private final int[] aggPtSize;

    public PAA(TimeSeries ts, int shrunkSize) {
      super(createPAAData(ts, shrunkSize));
      this.aggPtSize = calculateAggSizes(ts.size(), shrunkSize);
   }

   private static double[][] createPAAData(TimeSeries ts, int shrunkSize) {
      int originalSize = ts.size();
      int dimensions = ts.numOfDimensions();
      double[][] reducedData = new double[shrunkSize][dimensions];

      double reducedPtSize = (double) originalSize / (double) shrunkSize;
      int ptToReadFrom = 0;
      int ptToReadTo;

      for (int i = 0; i < shrunkSize; i++) {
         ptToReadTo = (int) Math.round(reducedPtSize * (i + 1)) - 1;
         int ptsToRead = ptToReadTo - ptToReadFrom + 1;

         for (int dim = 0; dim < dimensions; dim++) {
            double sum = 0.0;
            for (int j = ptToReadFrom; j <= ptToReadTo; j++) {
               sum += ts.getMeasurement(j, dim);
            }
            reducedData[i][dim] = sum / (double) ptsToRead;
         }

         ptToReadFrom = ptToReadTo + 1;
      }
      return reducedData;
   }

   private static int[] calculateAggSizes(int originalSize, int shrunkSize) {
      int[] aggSizes = new int[shrunkSize];
      double reducedPtSize = (double) originalSize / (double) shrunkSize;
      int ptToReadFrom = 0;

      for (int i = 0; i < shrunkSize; i++) {
         int ptToReadTo = (int) Math.round(reducedPtSize * (i + 1)) - 1;
         aggSizes[i] = ptToReadTo - ptToReadFrom + 1;
         ptToReadFrom = ptToReadTo + 1;
      }
      return aggSizes;
   }


   public int aggregatePtSize(int ptIndex) {
      return aggPtSize[ptIndex];
   }
}