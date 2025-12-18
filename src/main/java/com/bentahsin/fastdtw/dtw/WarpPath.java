package com.bentahsin.fastdtw.dtw;

import com.bentahsin.fastdtw.matrix.ColMajorCell;
import java.util.Arrays;

public class WarpPath {
   private int[] iIndexes;
   private int[] jIndexes;
   private int size = 0;

   public WarpPath(int initialCapacity) {
      this.iIndexes = new int[initialCapacity];
      this.jIndexes = new int[initialCapacity];
   }

   public void addFirst(int i, int j) {
      ensureCapacity(size + 1);
      System.arraycopy(iIndexes, 0, iIndexes, 1, size);
      System.arraycopy(jIndexes, 0, jIndexes, 1, size);
      iIndexes[0] = i;
      jIndexes[0] = j;
      size++;
   }

   private void ensureCapacity(int minCapacity) {
      if (minCapacity > iIndexes.length) {
         int newCapacity = Math.max(iIndexes.length * 2, minCapacity);
         iIndexes = Arrays.copyOf(iIndexes, newCapacity);
         jIndexes = Arrays.copyOf(jIndexes, newCapacity);
      }
   }

   public int size() { return size; }

   public int minI() { return size > 0 ? iIndexes[0] : 0; }
   public int minJ() { return size > 0 ? jIndexes[0] : 0; }

   public ColMajorCell get(int index) {
      return new ColMajorCell(iIndexes[index], jIndexes[index]);
   }

   public int getI(int index) { return iIndexes[index]; }
   public int getJ(int index) { return jIndexes[index]; }
}