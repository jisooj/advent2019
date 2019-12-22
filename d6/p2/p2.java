/*
--- Part Two ---

Now, you just need to figure out how many orbital transfers you (YOU) need to take to get to Santa (SAN).

You start at the object YOU are orbiting; your destination is the object SAN is orbiting. An orbital transfer lets you move from any object to an object orbiting or orbited by that object.

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
K)YOU
I)SAN

Visually, the above map of orbits looks like this:

                          YOU
                         /
        G - H       J - K - L
       /           /
COM - B - C - D - E - F
               \
                I - SAN

In this example, YOU are in orbit around K, and SAN is in orbit around I. To move from K to I, a minimum of 4 orbital transfers are required:

    K to J
    J to E
    E to D
    D to I

Afterward, the map of orbits looks like this:

        G - H       J - K - L
       /           /
COM - B - C - D - E - F
               \
                I - SAN
                 \
                  YOU

What is the minimum number of orbital transfers required to move from the object YOU are orbiting to the object SAN is orbiting? (Between the objects they are orbiting - not between YOU and SAN.)

Your puzzle answer was 304.
*/

import java.util.*;
import java.io.*;

public class p2 {
   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      // class node { id, edges: node[] }
      // build map<id, node> 
      Map<String, Node> map = buildMap(in);

      // bfs("YOU", "SAN"): keep track dist
      int minDist = bfs(map.get("YOU"), map.get("SAN"));
      System.out.println(minDist - 2);
   }

   public static int bfs(Node src, Node dest) {
      Queue<Node> q = new LinkedList<>();
      q.add(src);
      Set<Node> visited = new HashSet<>();
      int dist = 0;
      int remaining = 1;
      int next = 0;
      while (!q.isEmpty()) {
         Node cur = q.remove();
         visited.add(cur);
         if (cur == dest) {
            return dist;   
         }
         for (Node e : cur.edges) {
            if (!visited.contains(e)) {
               q.add(e); 
               next++;
            }
         }
         remaining--;
         if (remaining == 0) {
            dist++;
            remaining = next;
            next = 0;
         }
      }
      return -1;
   }

   public static Map<String, Node> buildMap(Scanner in) {
      Map<String, Node> map = new HashMap<>();
      while (in.hasNextLine()) {
         String[] relation = in.nextLine().split("\\)");
         if (!map.containsKey(relation[0])) {
            map.put(relation[0], new Node(relation[0]));   
         }
         if (!map.containsKey(relation[1])) {
            map.put(relation[1], new Node(relation[1]));   
         }
         Node n1 = map.get(relation[0]);
         Node n2 = map.get(relation[1]);
         n1.edges.add(n2);
         n2.edges.add(n1);
      }
      return map;
   }

   public static class Node {
      String id;
      List<Node> edges;

      public Node(String id) {
         this.id = id;
         this.edges = new ArrayList<>();
      }
   }
}
