/*
--- Day 3: Crossed Wires ---

The gravity assist was successful, and you're well on your way to the Venus refuelling station. During the rush back on Earth, the fuel management system wasn't completely installed, so that's next on the priority list.

Opening the front panel reveals a jumble of wires. Specifically, two wires are connected to a central port and extend outward on a grid. You trace the path each wire takes as it leaves the central port, one wire per line of text (your puzzle input).

The wires twist and turn, but the two wires occasionally cross paths. To fix the circuit, you need to find the intersection point closest to the central port. Because the wires are on a grid, use the Manhattan distance for this measurement. While the wires do technically cross right at the central port where they both start, this point does not count, nor does a wire count as crossing with itself.

For example, if the first wire's path is R8,U5,L5,D3, then starting from the central port (o), it goes right 8, up 5, left 5, and finally down 3:

...........
...........
...........
....+----+.
....|....|.
....|....|.
....|....|.
.........|.
.o-------+.
...........

Then, if the second wire's path is U7,R6,D4,L4, it goes up 7, right 6, down 4, and left 4:

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

These wires cross at two locations (marked X), but the lower-left one is closer to the central port: its distance is 3 + 3 = 6.

Here are a few more examples:

    R75,D30,R83,U83,L12,D49,R71,U7,L72
    U62,R66,U55,R34,D71,R55,D58,R83 = distance 159
    R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
    U98,R91,D20,R16,D67,R40,U7,R15,U6,R7 = distance 135

What is the Manhattan distance from the central port to the closest intersection?

Your puzzle answer was 209.
*/
import java.util.*;
import java.io.*;

public class p1 {
   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      Edge[] e1 = makeEdges(in.nextLine().split(","));
      Edge[] e2 = makeEdges(in.nextLine().split(","));
      int minDist = Integer.MAX_VALUE;
      int minX = 0;
      int minY = 0;

      for (int i = 0; i < e1.length; i++) {
         for (int j = 0; j < e2.length; j++) {
            Point intersection = getIntersection(e1[i], e2[j]);
            // exclude origin
            if (intersection != null && 
               intersection.x != 0 && 
               intersection.y != 0 &&
               manDist(intersection) < minDist) {
               minDist = manDist(intersection);
               minX = intersection.x;
               minY = intersection.y;
            }
         }   
      }
      System.out.println(minDist);
   }

   public static Point getClosestPoint(Edge e1, Edge e2) {
         int lower = Math.max(e1.lowerX, e2.lowerX);
         int upper = Math.min(e1.upperX, e2.upperX);
         if (e1.isVertical()) {
            lower = Math.max(e1.lowerY, e2.lowerY);
            upper = Math.min(e1.upperY, e2.upperY);
         }
         if (lower <= upper) {
            if (lower > 0 && upper > 0) {
               if (e1.isVertical()) {
                  return new Point(e1.lowerX, lower);   
               }
               return new Point(lower, e1.lowerY);
            } else if (lower < 0 && upper < 0) {
               if (e1.isVertical()) {
                  return new Point(e1.lowerX, upper);   
               }
               return new Point(upper, e1.lowerY);
            } else if (e1.isVertical()) {
               return new Point(e1.lowerX, 0);
            } else {
               return new Point(0, e1.lowerY);
            }
         }
         return null;
   }

   public static Point getIntersection(Edge e1, Edge e2) {
      // check if they have same orientation
      if (e1.isVertical() == e2.isVertical()) {
         if ((e1.isVertical() && e1.lowerX == e2.lowerX) ||
            (!e1.isVertical() && e1.lowerY == e2.lowerY)) {
            return getClosestPoint(e1, e2);
         }
         return null;
      } else {
         Edge vert = e1;
         Edge horiz = e2;
         if (!vert.isVertical()) {
            vert = e2;
            horiz = e1;
         }
         if (horiz.lowerX <= vert.lowerX && vert.lowerX <= horiz.upperX &&
             vert.lowerY <= horiz.lowerY && horiz.lowerY <= vert.upperY) {
            Point p = new Point(vert.lowerX, horiz.lowerY);   
            return p;
         }
      }
      return null;
   }

   public static Edge[] makeEdges(String[] dirs) {
      Edge[] res = new Edge[dirs.length];   
      int curX = 0;
      int curY = 0;
      for (int i = 0; i < dirs.length; i++) {
         char dir = dirs[i].charAt(0);
         int dist = Integer.parseInt(dirs[i].substring(1));
         if (dir == 'U') {
            res[i] = new Edge(curX, curY, curX, curY + dist);
            curY += dist;
         } else if (dir == 'D') {
            res[i] = new Edge(curX, curY - dist, curX, curY);
            curY -= dist;
         } else if (dir == 'L') {
            res[i] = new Edge(curX - dist, curY, curX, curY);
            curX -= dist;
         } else { // 'R'
            res[i] = new Edge(curX, curY, curX + dist, curY);
            curX += dist;
         }
      }
      return res;
   }

   // Manhattan distance from origin
   public static int manDist(Point pt) {
      return Math.abs(pt.x) + Math.abs(pt.y);
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
      int lowerX;
      int lowerY;
      int upperX;
      int upperY;
      public Edge(int lowerX, int lowerY, int upperX, int upperY)  {
         this.lowerX = lowerX;
         this.lowerY = lowerY;
         this.upperX = upperX;
         this.upperY = upperY;
      }

      public boolean isVertical() {
         return lowerX == upperX;
      }

      public String toString() {
         return String.format("(%s, %s) -> (%s, %s)\n",
            this.lowerX, this.lowerY,
            this.upperX, this.upperY);
      }
   }
}
