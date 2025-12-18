package com.bentahsin.fastdtw.dtw;

import com.bentahsin.fastdtw.timeseries.TimeSeries;

@SuppressWarnings("unused")
public class LinearWindow extends SearchWindow {

   public LinearWindow(TimeSeries tsI, TimeSeries tsJ, int searchRadius) {
      super(tsI.size(), tsJ.size());

      double ijRatio = (double) tsI.size() / (double) tsJ.size();
      boolean isIlargest = tsI.size() >= tsJ.size();

      for (int i = 0; i < tsI.size(); ++i) {
         int j;
         if (isIlargest) {
            j = Math.min((int) Math.round(i / ijRatio), tsJ.size() - 1);
            super.markVisited(i, j);
         } else {
            j = (int) Math.round((i + 1) / ijRatio) - 1;
            int minJ = (int) Math.round(i / ijRatio);
            super.markVisited(i, minJ);
            super.markVisited(i, j);
         }
      }

      super.expandWindow(searchRadius);
   }
}