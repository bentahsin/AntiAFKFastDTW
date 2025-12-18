package com.bentahsin.fastdtw.dtw;

import java.util.Arrays;

public abstract class SearchWindow {
   private final int[] minValues;
   private final int[] maxValues;
   private final int maxJ;
   private int size;

   public SearchWindow(int tsIsize, int tsJsize) {
      this.minValues = new int[tsIsize];
      this.maxValues = new int[tsIsize];
      Arrays.fill(this.minValues, -1);
      this.maxJ = tsJsize - 1;
      this.size = 0;
   }

   public final int minI() { return 0; }
   public final int maxI() { return minValues.length - 1; }
   public final int minJ() { return 0; }
   public final int maxJ() { return maxJ; }

   public final int minJforI(int i) { return minValues[i]; }
   public final int maxJforI(int i) { return maxValues[i]; }
   public final int size() { return size; }

   protected final void expandWindow(int radius) {
      if (radius > 0) {
         for (int i = 0; i < minValues.length; i++) {
            if (minValues[i] != -1) {
               minValues[i] = Math.max(0, minValues[i] - radius);
               maxValues[i] = Math.min(maxJ, maxValues[i] + radius);
            }
         }
         recalculateSize();
      }
   }

   protected final void markVisited(int col, int row) {
      if (col >= minValues.length) return;

      if (minValues[col] == -1) {
         minValues[col] = row;
         maxValues[col] = row;
      } else {
         if (row < minValues[col]) minValues[col] = row;
         if (row > maxValues[col]) maxValues[col] = row;
      }
   }

   private void recalculateSize() {
      size = 0;
      for (int i = 0; i < minValues.length; i++) {
         if (minValues[i] != -1) {
            size += (maxValues[i] - minValues[i] + 1);
         }
      }
   }
}