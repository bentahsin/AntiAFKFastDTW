package com.bentahsin.fastdtw.dtw;

import com.bentahsin.fastdtw.timeseries.TimeSeries;

public class FullWindow extends SearchWindow {
   public FullWindow(TimeSeries tsI, TimeSeries tsJ) {
      super(tsI.size(), tsJ.size());

      for(int i = 0; i < tsI.size(); ++i) {
         super.markVisited(i, this.minJ());
         super.markVisited(i, this.maxJ());
      }

   }
}
