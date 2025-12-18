package com.bentahsin.fastdtw.dtw;

class MemoryResidentMatrix implements CostMatrix {
   private final SearchWindow window;
   private final double[] cellValues;
   private final int[] colOffsets;

   MemoryResidentMatrix(SearchWindow searchWindow) {
      this.window = searchWindow;
      this.cellValues = new double[this.window.size()];
      this.colOffsets = new int[this.window.maxI() + 1];

      int currentOffset = 0;
      for(int i = this.window.minI(); i <= this.window.maxI(); ++i) {
         this.colOffsets[i] = currentOffset;
         currentOffset += this.window.maxJforI(i) - this.window.minJforI(i) + 1;
      }
   }

   public void put(int col, int row, double value) {
      this.cellValues[this.colOffsets[col] + row - this.window.minJforI(col)] = value;
   }

   public double get(int col, int row) {
      if (row >= this.window.minJforI(col) && row <= this.window.maxJforI(col)) {
         return this.cellValues[this.colOffsets[col] + row - this.window.minJforI(col)];
      }
      return Double.POSITIVE_INFINITY;
   }

   public int size() { return this.cellValues.length; }
}