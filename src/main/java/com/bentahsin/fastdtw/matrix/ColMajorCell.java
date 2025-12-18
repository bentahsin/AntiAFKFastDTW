package com.bentahsin.fastdtw.matrix;

public final class ColMajorCell {
   private final int col;
   private final int row;

   public ColMajorCell(int col, int row) {
      this.col = col;
      this.row = row;
   }

   public int getCol() { return col; }
   public int getRow() { return row; }
}