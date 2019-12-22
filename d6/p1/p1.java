/*
--- Day 6: Universal Orbit Map ---

You've landed at the Universal Orbit Map facility on Mercury. Because navigation in space often involves transferring between orbits, the orbit maps here are useful for finding efficient routes between, for example, you and Santa. You download a map of the local orbits (your puzzle input).

Except for the universal Center of Mass (COM), every object in space is in orbit around exactly one other object. An orbit looks roughly like this:

                  \
                   \
                    |
                    |
AAA--> o            o <--BBB
                    |
                    |
                   /
                  /

In this diagram, the object BBB is in orbit around AAA. The path that BBB takes around AAA (drawn with lines) is only partly shown. In the map data, this orbital relationship is written AAA)BBB, which means "BBB is in orbit around AAA".

Before you use your map data to plot a course, you need to make sure it wasn't corrupted during the download. To verify maps, the Universal Orbit Map facility uses orbit count checksums - the total number of direct orbits (like the one shown above) and indirect orbits.

Whenever A orbits B and B orbits C, then A indirectly orbits C. This chain can be any number of objects long: if A orbits B, B orbits C, and C orbits D, then A indirectly orbits D.

For example, suppose you have the following map:

COM)B
B)C
C)D
D)E
E)F
B)G
G)H
D)I
E)J
J)K
K)L

Visually, the above map of orbits looks like this:

        G - H       J - K - L
       /           /
COM - B - C - D - E - F
               \
                I

In this visual representation, when two objects are connected by a line, the one on the right directly orbits the one on the left.

Here, we can count the total number of orbits as follows:

    D directly orbits C and indirectly orbits B and COM, a total of 3 orbits.
    L directly orbits K and indirectly orbits J, E, D, C, B, and COM, a total of 7 orbits.
    COM orbits nothing.

The total number of direct and indirect orbits in this example is 42.

What is the total number of direct and indirect orbits in your map data?

*/
import java.util.*;
import java.io.*;

// node should have total orbits = direct + indirect
public class p1 {
   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      var map = makeMap(in);
      System.out.println(getOrbitCounts(map.get("COM"), 0, map));
   }

   public static int getOrbitCounts(
      Node root, 
      int height, 
      Map<String, Node> map) {
      // traverse and keep height 
      if (root.children.isEmpty()) {
         return height;   
      }
      int sum = 0;
      for (Node child : root.children) {
         sum += getOrbitCounts(child, height + 1, map);
      }
      return sum + height;
   }

   public static Map<String, Node> makeMap(Scanner in) {
      var map = new HashMap<String, Node>();
      while (in.hasNextLine()) {
         String[] relation = in.nextLine().split("\\)");
         if (!map.containsKey(relation[0])) {
            map.put(relation[0], new Node(relation[0]));   
         }
         if (!map.containsKey(relation[1])) {
            map.put(relation[1], new Node(relation[1]));   
         }
         map.get(relation[0]).children.add(map.get(relation[1]));
      }   
      return map;
   }

   public static class Node {
      String id;
      List<Node> children;
      int orbits;

      public Node(String id) {
         this.id = id;
         this.children = new ArrayList<>();
         this.orbits = 0;
      }

      public String toString() {
         return String.format("%s, children=%d\n", id, children.size()); 
      }
   }
}
