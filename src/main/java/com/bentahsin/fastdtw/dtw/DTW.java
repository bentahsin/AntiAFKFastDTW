package com.bentahsin.fastdtw.dtw;

import com.bentahsin.fastdtw.timeseries.TimeSeries;
import com.bentahsin.fastdtw.util.DistanceFunction;

@SuppressWarnings("unused")
public class DTW {

   public static double getWarpDistBetween(TimeSeries tsI, TimeSeries tsJ, DistanceFunction distFn) {
      if (tsI.size() == 0 || tsJ.size() == 0) return 0.0;

      double[] lastCol = new double[tsJ.size()];
      double[] currCol = new double[tsJ.size()];

      currCol[0] = distFn.calcDistance(tsI.getMeasurementVector(0), tsJ.getMeasurementVector(0));
      for(int j = 1; j < tsJ.size(); j++) {
         currCol[j] = currCol[j - 1] + distFn.calcDistance(tsI.getMeasurementVector(0), tsJ.getMeasurementVector(j));
      }

      for(int i = 1; i < tsI.size(); i++) {
         double[] temp = lastCol; lastCol = currCol; currCol = temp;

         currCol[0] = lastCol[0] + distFn.calcDistance(tsI.getMeasurementVector(i), tsJ.getMeasurementVector(0));

         for(int j = 1; j < tsJ.size(); j++) {
            double minGlobalCost = Math.min(lastCol[j], Math.min(lastCol[j - 1], currCol[j - 1]));
            currCol[j] = minGlobalCost + distFn.calcDistance(tsI.getMeasurementVector(i), tsJ.getMeasurementVector(j));
         }
      }
      return currCol[tsJ.size() - 1];
   }

   public static WarpPath getWarpPathBetween(TimeSeries tsI, TimeSeries tsJ, SearchWindow window, DistanceFunction distFn) {
      return constrainedTimeWarp(tsI, tsJ, window, distFn).getPath();
   }

   public static TimeWarpInfo getWarpInfoBetween(TimeSeries tsI, TimeSeries tsJ, SearchWindow window, DistanceFunction distFn) {
      return constrainedTimeWarp(tsI, tsJ, window, distFn);
   }

   private static TimeWarpInfo constrainedTimeWarp(TimeSeries tsI, TimeSeries tsJ, SearchWindow window, DistanceFunction distFn) {
      CostMatrix costMatrix = new MemoryResidentMatrix(window);
      int maxI = tsI.size() - 1;
      int maxJ = tsJ.size() - 1;

      for (int i = window.minI(); i <= window.maxI(); i++) {
         int startJ = window.minJforI(i);
         int endJ = window.maxJforI(i);

         for (int j = startJ; j <= endJ; j++) {
            double dist = distFn.calcDistance(tsI.getMeasurementVector(i), tsJ.getMeasurementVector(j));

            if (i == 0 && j == 0) {
               costMatrix.put(i, j, dist);
            } else if (i == 0) {
               costMatrix.put(i, j, dist + costMatrix.get(i, j - 1));
            } else if (j == 0) {
               costMatrix.put(i, j, dist + costMatrix.get(i - 1, j));
            } else {
               double minGlobalCost = Math.min(costMatrix.get(i - 1, j),
                       Math.min(costMatrix.get(i - 1, j - 1),
                               costMatrix.get(i, j - 1)));
               costMatrix.put(i, j, minGlobalCost + dist);
            }
         }
      }

      double minimumCost = costMatrix.get(maxI, maxJ);
      WarpPath minCostPath = new WarpPath(tsI.size() + tsJ.size());
      int i = maxI;
      int j = maxJ;
      minCostPath.addFirst(i, j);

      while (i > 0 || j > 0) {
         double diagCost = Double.POSITIVE_INFINITY;
         double leftCost = Double.POSITIVE_INFINITY;
         double downCost = Double.POSITIVE_INFINITY;

         if (i > 0 && j > 0) diagCost = costMatrix.get(i - 1, j - 1);
         if (i > 0)          leftCost = costMatrix.get(i - 1, j);
         if (j > 0)          downCost = costMatrix.get(i, j - 1);

         if (diagCost <= leftCost && diagCost <= downCost) {
            i--; j--;
         } else if (leftCost < diagCost && leftCost < downCost) {
            i--;
         } else {
            j--;
         }
         minCostPath.addFirst(i, j);
      }

      return new TimeWarpInfo(minimumCost, minCostPath);
   }
}