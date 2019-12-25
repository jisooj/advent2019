/*
--- Day 24: Planet of Discord ---

You land on Eris, your last stop before reaching Santa. As soon as you do, your sensors start picking up strange life forms moving around: Eris is infested with bugs! With an over 24-hour roundtrip for messages between you and Earth, you'll have to deal with this problem on your own.

Eris isn't a very large place; a scan of the entire area fits into a 5x5 grid (your puzzle input). The scan shows bugs (#) and empty spaces (.).

Each minute, The bugs live and die based on the number of bugs in the four adjacent tiles:

    A bug dies (becoming an empty space) unless there is exactly one bug adjacent to it.
    An empty space becomes infested with a bug if exactly one or two bugs are adjacent to it.

Otherwise, a bug or empty space remains the same. (Tiles on the edges of the grid have fewer than four adjacent tiles; the missing tiles count as empty space.) This process happens in every location simultaneously; that is, within the same minute, the number of adjacent bugs is counted for every tile first, and then the tiles are updated.

Here are the first few minutes of an example scenario:

Initial state:
....#
#..#.
#..##
..#..
#....

After 1 minute:
#..#.
####.
###.#
##.##
.##..

After 2 minutes:
#####
....#
....#
...#.
#.###

After 3 minutes:
#....
####.
...##
#.##.
.##.#

After 4 minutes:
####.
....#
##..#
.....
##...

To understand the nature of the bugs, watch for the first time a layout of bugs and empty spaces matches any previous layout. In the example above, the first layout to appear twice is:

.....
.....
.....
#....
.#...

To calculate the biodiversity rating for this layout, consider each tile left-to-right in the top row, then left-to-right in the second row, and so on. Each of these tiles is worth biodiversity points equal to increasing powers of two: 1, 2, 4, 8, 16, 32, and so on. Add up the biodiversity points for tiles with bugs; in this example, the 16th tile (32768 points) and 22nd tile (2097152 points) have bugs, a total biodiversity rating of 2129920.

What is the biodiversity rating for the first layout that appears twice?

Your puzzle answer was 24662545.
*/

import java.util.*;
import java.io.*;

public class p1 {
   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      char[][] curr = makeMap(in);
      Set<String> maps = new HashSet<>();
      print(curr);
      String currStr = mapToStr(curr);
      while (!maps.contains(currStr)) {
         maps.add(currStr);
         char[][] next = getNextIter(curr);
         currStr = mapToStr(next);
         print(next);
         curr = next;
      }

      System.out.println("----------");
      print(curr);
      System.out.println(getPoints(curr));
   }

   public static String mapToStr(char[][] map) {
      StringBuilder sb = new StringBuilder();
      for (char[] line : map) {
         sb.append(line);   
      }
      return sb.toString();
   }

   public static char[][] getNextIter(char[][] map) {
      int n = map.length;
      int m = map[0].length;
      char[][] next = new char[n][m];
      for (int i = 0; i < n; i++) {
         for (int j = 0; j < m; j++) {
            char ch = map[i][j];
            int bugs = bugCount(map, i, j);
            if (ch == '.') {
               if (bugs == 1 || bugs == 2) {
                  next[i][j] = '#'; 
               } else {
                  next[i][j] = ch;   
               }
            } else {
               if (bugs != 1) {
                  next[i][j] = '.';   
               } else {
                  next[i][j] = ch;   
               }
            }
         }   
      }
      return next;
   }

   public static int bugCount(char[][] map, int i, int j) {
      int cnt = 0;
      if (i - 1 >= 0 && map[i - 1][j] == '#') {
         cnt++;   
      }
      if (j - 1 >= 0 && map[i][j - 1] == '#') {
         cnt++;   
      }
      if (i + 1 < map.length && map[i + 1][j] == '#') {
         cnt++;
      }
      if (j + 1 < map[0].length && map[i][j + 1] == '#') {
         cnt++;   
      }
      return cnt;
   }

   public static int getPoints(char[][] map) {
      int power = 0;
      int pts = 0;
      for (int i = 0; i < map.length; i++) {
         for (int j = 0; j < map[0].length; j++) {
            if (map[i][j] == '#') {
               pts += (int)Math.pow(2, power);
            }
            power++;
         }   
      }
      return pts;
   }

   public static void print(char[][] map) {
      for (char[] line : map) {
         System.out.println(line);   
      }
      System.out.println();
   }

   public static char[][] makeMap(Scanner in) {
      char[][] map = new char[5][];
      int i = 0;
      while (in.hasNextLine()) {
         char[] n = in.nextLine().toCharArray();   
         map[i] = n;
         i++;
      }
      return map;
   }
}
