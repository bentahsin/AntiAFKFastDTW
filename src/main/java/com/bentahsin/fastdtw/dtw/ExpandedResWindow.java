package com.bentahsin.fastdtw.dtw;

import com.bentahsin.fastdtw.timeseries.PAA;
import com.bentahsin.fastdtw.timeseries.TimeSeries;

public class ExpandedResWindow extends SearchWindow {
   public ExpandedResWindow(TimeSeries tsI, TimeSeries tsJ, PAA shrunkI, PAA shrunkJ, WarpPath shrunkWarpPath, int searchRadius) {
      super(tsI.size(), tsJ.size());

      int currentI = shrunkWarpPath.minI();
      int currentJ = shrunkWarpPath.minJ();
      int lastWarpedI = Integer.MAX_VALUE;
      int lastWarpedJ = Integer.MAX_VALUE;

      for(int w = 0; w < shrunkWarpPath.size(); ++w) {
         int warpedI = shrunkWarpPath.getI(w);
         int warpedJ = shrunkWarpPath.getJ(w);

         int blockISize = shrunkI.aggregatePtSize(warpedI);
         int blockJSize = shrunkJ.aggregatePtSize(warpedJ);

         if (warpedJ > lastWarpedJ) {
            currentJ += shrunkJ.aggregatePtSize(lastWarpedJ);
         }
         if (warpedI > lastWarpedI) {
            currentI += shrunkI.aggregatePtSize(lastWarpedI);
         }
         if (warpedJ > lastWarpedJ && warpedI > lastWarpedI) {
            markVisited(currentI - 1, currentJ);
            markVisited(currentI, currentJ - 1);
         }
         for(int x = 0; x < blockISize; ++x) {
            markVisited(currentI + x, currentJ);
            markVisited(currentI + x, currentJ + blockJSize - 1);
         }
         lastWarpedI = warpedI;
         lastWarpedJ = warpedJ;
      }
      expandWindow(searchRadius);
   }
}