package com.bentahsin.fastdtw.dtw;

interface CostMatrix {
   void put(int var1, int var2, double var3);

   double get(int var1, int var2);

   int size();
}
