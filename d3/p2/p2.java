/*
--- Part Two ---

It turns out that this circuit is very timing-sensitive; you actually need to minimize the signal delay.

To do this, calculate the number of steps each wire takes to reach each intersection; choose the intersection where the sum of both wires' steps is lowest. If a wire visits a position on the grid multiple times, use the steps value from the first time it visits that position when calculating the total value of a specific intersection.

The number of steps a wire takes is the total number of grid squares the wire has entered to get to that location, including the intersection being considered. Again consider the example from above:

...........
.+-----+...
.|.....|...
.|..+--X-+.
.|..|..|.|.
.|.-X--+.|.
.|..|....|.
.|.......|.
.o-------+.
...........

In the above example, the intersection closest to the central port is reached after 8+5+5+2 = 20 steps by the first wire and 7+6+4+3 = 20 steps by the second wire for a total of 20+20 = 40 steps.

However, the top-right intersection is better: the first wire takes only 8+5+2 = 15 and the second wire takes only 7+6+2 = 15, a total of 15+15 = 30 steps.

Here are the best steps for the extra examples from above:

    R75,D30,R83,U83,L12,D49,R71,U7,L72
    U62,R66,U55,R34,D71,R55,D58,R83 = 610 steps
    R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
    U98,R91,D20,R16,D67,R40,U7,R15,U6,R7 = 410 steps

What is the fewest combined steps the wires must take to reach an intersection?
*/

import java.util.*;
import java.io.*;

public class p2 {
   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      Edge[] e1 = makeEdges(in.nextLine().split(","));
      Edge[] e2 = makeEdges(in.nextLine().split(","));
      int minSteps = Integer.MAX_VALUE;
      System.out.println(String.format(Arrays.toString(e1)));
      System.out.println(String.format(Arrays.toString(e2)));
      for (int i = 0; i < e1.length; i++) {
         for (int j = 0; j < e2.length; j++) {
            Point intersection = getIntersection(e1[i], e2[j]);
            // exclude origin
            if (intersection != null && 
               intersection.x != 0 && 
               intersection.y != 0) {
               System.out.println(String.format("intersection=%s", intersection));
               System.out.println(String.format("e1=%s,e2=%s,minsteps=%d",
                  e1[i], e2[j], getStepSum(e1[i], e2[j], intersection)));
               minSteps = Math.min(minSteps, getStepSum(e1[i], e2[j], intersection));
            }
         }   
      }
      System.out.println(minSteps);
   }

   public static Point getIntersection(Edge e1, Edge e2) {
      // check if they have same orientation
      if (e1.isVertical() == e2.isVertical()) {
         return getClosestPoint(e1, e2);
      } else {
         Edge vert = e1;
         Edge horiz = e2;
         if (!vert.isVertical()) {
            vert = e2;
            horiz = e1;
         }
         if (horiz.fromX <= vert.fromX && vert.fromX <= horiz.toX &&
             vert.fromY <= horiz.fromY && horiz.fromY <= vert.toY) {
            return new Point(vert.fromX, horiz.fromY);   
         }
      }
      return null;
   }

   public static int getStepSum(Edge e1, Edge e2, Point intersection) {
      return getDist(new Point(e1.fromX, e1.fromY), intersection) +
         e1.stepsSoFar +
         getDist(new Point(e2.fromX, e2.fromY), intersection) +
         e2.stepsSoFar;
   }

   public static int getDist(Point p1, Point p2) {
      return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);   
   }

   public static Point getClosestPoint(Edge e1, Edge e2) {
      // check if two edges intersect
      if (e1.isVertical()) {
         if (e1.fromX != e2.fromX) {
            return null;   
         }   
         if (overlap(e1.fromY, e1.toY, e2.fromY, e2.toY)) {
            int[] overlap = getOverlap(e1.fromY, e1.toY, e2.fromY, e2.toY);
            Point p1 = new Point(e1.fromX, overlap[0]);
            Point p2 = new Point(e1.fromX, overlap[1]);
            if (getStepSum(e1, e2, p1) < getStepSum(e1, e2, p2)) {
               return p1;
            } else {
               return p2;
            }
         }
      } else {
         if (e1.fromY != e2.fromY) {
            return null;   
         }
         if (overlap(e1.fromX, e1.toX, e2.fromX, e2.toX)) {
            int[] overlap = getOverlap(e1.fromX, e1.toX, e2.fromX, e2.toX);
            Point p1 = new Point(overlap[0], e1.fromY);
            Point p2 = new Point(overlap[1], e1.fromY);
            if (getStepSum(e1, e2, p1) < getStepSum(e1, e2, p2)) {
               return p1;
            } else {
               return p2;
            }
         }
      }
      return null;
   }

   public static int[] getOverlap(int x1, int x2, int x3, int x4) {
      int[] temp = new int[] { x1, x2, x3, x4 };
      Arrays.sort(temp);
      return new int[] { temp[1], temp[2] };
   }

   // assume e1 and e2 have the same orientation
   public static Point getPointWithMinSteps(Edge e1, Edge e2) {
      System.out.println(String.format("same orientaion: e1=%s, e2=%s", e1, e2));
      // don't care which point to choose in oppisite directions case
      if (e1.isAscending() && e2.isAscending()) {
         // up and right
         if (e1.isVertical()) {
            return new Point(e1.fromX, Math.max(e1.fromY, e2.fromY));   
         }
         return new Point(Math.max(e1.fromX, e2.fromX), e1.fromY);
      } else {
         // down and left
         if (e1.isVertical()) {
            return new Point(e1.fromX, Math.min(e1.fromY, e2.fromY));   
         }
         return new Point(Math.min(e1.fromX, e2.fromX), e1.fromY);
      }
   }

   public static boolean overlap(int e1From, int e1To, int e2From, int e2To) {
      return inRange(e1From, e2From, e1To) ||
         inRange(e1From, e2To, e1To) ||
         inRange(e2From, e1From, e2To) ||
         inRange(e2From, e1To, e2To); 
   }

   // double checks:
   // b1 <= target <= b2
   // b2 <= target <= b1
   public static boolean inRange(int b1, int target, int b2) {
      return (b1 <= target && target <= b2) || 
         (b2 <= target && target <= b1);
   }

   public static Edge[] makeEdges(String[] dirs) {
      Edge[] res = new Edge[dirs.length];   
      int curX = 0;
      int curY = 0;
      int stepsSoFar = 0;
      for (int i = 0; i < dirs.length; i++) {
         char dir = dirs[i].charAt(0);
         int dist = Integer.parseInt(dirs[i].substring(1));
         if (dir == 'U') {
            res[i] = new Edge(curX, curY, curX, curY + dist, stepsSoFar);
            curY += dist;
         } else if (dir == 'D') {
            res[i] = new Edge(curX, curY, curX, curY - dist, stepsSoFar);
            curY -= dist;
         } else if (dir == 'L') {
            res[i] = new Edge(curX, curY, curX - dist, curY, stepsSoFar);
            curX -= dist;
         } else { // 'R'
            res[i] = new Edge(curX, curY, curX + dist, curY, stepsSoFar);
            curX += dist;
         }
         stepsSoFar += dist;
      }
      return res;
   }

   public static class Point {
      int x;
      int y;

      public Point(int x, int y) {
         this.x = x;
         this.y = y;
      }

      public String toString() {
         return String.format("(%s, %s)", x, y);   
      }
   }

   public static class Edge {
      int fromX;
      int fromY;
      int toX;
      int toY;
      int stepsSoFar;

      public Edge(int fromX, int fromY, int toX, int toY, int stepsSoFar)  {
         this.fromX = fromX;
         this.fromY = fromY;
         this.toX = toX;
         this.toY = toY;
         this.stepsSoFar = stepsSoFar;
      }

      public boolean isVertical() {
         return fromX == toX;
      }

      // Defines direction
      public boolean isAscending() {
         return (fromX < toX) || (fromY < toY);
      }

      public String toString() {
         return String.format("(%s, %s) -> (%s, %s), steps=%d\n",
            this.fromX, this.fromY,
            this.toX, this.toY,
            this.stepsSoFar);
      }
   }
}
