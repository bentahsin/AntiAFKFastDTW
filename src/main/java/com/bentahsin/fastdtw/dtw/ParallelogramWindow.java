package com.bentahsin.fastdtw.dtw;

import com.bentahsin.fastdtw.timeseries.TimeSeries;

@SuppressWarnings("unused")
public class ParallelogramWindow extends SearchWindow {

   public ParallelogramWindow(TimeSeries tsI, TimeSeries tsJ, int searchRadius) {
      super(tsI.size(), tsJ.size());

      double upperCornerI = Math.max(maxI() / 2.0 - searchRadius * ((double) maxI() / maxJ()), minI());
      double upperCornerJ = Math.min(maxJ() / 2.0 + searchRadius * ((double) maxJ() / maxI()), maxJ());
      double lowerCornerI = Math.min(maxI() / 2.0 + searchRadius * ((double) maxI() / maxJ()), maxI());
      double lowerCornerJ = Math.max(maxJ() / 2.0 - searchRadius * ((double) maxJ() / maxI()), minI());

      for (int i = 0; i < tsI.size(); ++i) {
         boolean isIlargest = tsI.size() >= tsJ.size();
         int maxJ;
         double interpRatio;

         if (i < upperCornerI) {
            if (isIlargest) {
               interpRatio = i / upperCornerI;
               maxJ = (int) Math.round(interpRatio * upperCornerJ);
            } else {
               interpRatio = (i + 1) / upperCornerI;
               maxJ = (int) Math.round(interpRatio * upperCornerJ) - 1;
            }
         } else {
            double num = (isIlargest ? i : i + 1) - upperCornerI;
            double den = maxI() - upperCornerI;
            interpRatio = num / den;
            maxJ = (int) Math.round(upperCornerJ + interpRatio * (maxJ() - upperCornerJ));
            if (!isIlargest) maxJ -= 1;
         }

         int minJ;
         if (i <= lowerCornerI) {
            interpRatio = i / lowerCornerI;
            minJ = (int) Math.round(interpRatio * lowerCornerJ);
         } else {
            interpRatio = (i - lowerCornerI) / ((double) maxI() - lowerCornerI);
            minJ = (int) Math.round(lowerCornerJ + interpRatio * (maxJ() - lowerCornerJ));
         }

         super.markVisited(i, minJ);
         super.markVisited(i, maxJ);
      }
   }
}