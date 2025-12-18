package com.bentahsin.fastdtw.dtw;

import com.bentahsin.fastdtw.timeseries.PAA;
import com.bentahsin.fastdtw.timeseries.TimeSeries;
import com.bentahsin.fastdtw.util.DistanceFunction;

@SuppressWarnings("unused")
public class FastDTW {

   static final int DEFAULT_SEARCH_RADIUS = 1;

   public static double getWarpDistBetween(TimeSeries tsI, TimeSeries tsJ, DistanceFunction distFn) {
      return fastDTW(tsI, tsJ, DEFAULT_SEARCH_RADIUS, distFn).getDistance();
   }

   public static double getWarpDistBetween(TimeSeries tsI, TimeSeries tsJ, int searchRadius, DistanceFunction distFn) {
      return fastDTW(tsI, tsJ, searchRadius, distFn).getDistance();
   }

   public static WarpPath getWarpPathBetween(TimeSeries tsI, TimeSeries tsJ, DistanceFunction distFn) {
      return fastDTW(tsI, tsJ, DEFAULT_SEARCH_RADIUS, distFn).getPath();
   }

   public static WarpPath getWarpPathBetween(TimeSeries tsI, TimeSeries tsJ, int searchRadius, DistanceFunction distFn) {
      return fastDTW(tsI, tsJ, searchRadius, distFn).getPath();
   }

   public static TimeWarpInfo getWarpInfoBetween(TimeSeries tsI, TimeSeries tsJ, int searchRadius, DistanceFunction distFn) {
      return fastDTW(tsI, tsJ, searchRadius, distFn);
   }

   private static TimeWarpInfo fastDTW(TimeSeries tsI, TimeSeries tsJ, int searchRadius, DistanceFunction distFn) {
      if (searchRadius < 0) {
         searchRadius = 0;
      }

      int minTSsize = searchRadius + 2;

      if (tsI.size() > minTSsize && tsJ.size() > minTSsize) {
         PAA shrunkI = new PAA(tsI, tsI.size() / 2);
         PAA shrunkJ = new PAA(tsJ, tsJ.size() / 2);

         WarpPath coarsePath = getWarpPathBetween(shrunkI, shrunkJ, searchRadius, distFn);
         SearchWindow window = new ExpandedResWindow(tsI, tsJ, shrunkI, shrunkJ, coarsePath, searchRadius);

         return DTW.getWarpInfoBetween(tsI, tsJ, window, distFn);
      } else {
         return DTW.getWarpInfoBetween(tsI, tsJ, new FullWindow(tsI, tsJ), distFn);
      }
   }
}