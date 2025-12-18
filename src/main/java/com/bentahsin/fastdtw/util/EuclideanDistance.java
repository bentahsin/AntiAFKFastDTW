package com.bentahsin.fastdtw.util;

@SuppressWarnings("unused")
public class EuclideanDistance implements DistanceFunction {

   @Override
   public double calcDistance(double[] vector1, double[] vector2) {
      double sqSum = 0.0;
      for (int x = 0; x < vector1.length; x++) {
         double diff = vector1[x] - vector2[x];
         sqSum += diff * diff;
      }
      return Math.sqrt(sqSum);
   }
}