package com.bentahsin.fastdtw.dtw;

public class TimeWarpInfo {
   private final double distance;
   private final WarpPath path;

   TimeWarpInfo(double dist, WarpPath wp) {
      this.distance = dist;
      this.path = wp;
   }

   public double getDistance() {
      return this.distance;
   }

   public WarpPath getPath() {
      return this.path;
   }

   public String toString() {
      return "(Warp Distance=" + this.distance + ", Warp Path=" + this.path + ")";
   }
}
