/*
--- Part Two ---

An Elf just remembered one more important detail: the two adjacent matching digits are not part of a larger group of matching digits.

Given this additional criterion, but still ignoring the range rule, the following are now true:

    112233 meets these criteria because the digits never decrease and all repeated digits are exactly two digits long.
    123444 no longer meets the criteria (the repeated 44 is part of a larger group of 444).
    111122 meets the criteria (even though 1 is repeated more than twice, it still contains a double 22).

How many different passwords within the range given in your puzzle input meet all of the criteria?

Your puzzle answer was 589.
*/

import java.util.*;
import java.io.*;

public class p2 {
   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      String[] range = in.nextLine().split("-");
      int low = Integer.parseInt(range[0]);
      int high = Integer.parseInt(range[1]);
      int count = 0;
      for (int i = low; i <= high; i++) {
         if (isValid(i)) {
            count++;   
         }   
      }
      System.out.println(count);
   }

   public static boolean isValid(int i) {
      return isAscending(i) && hasDouble(i);
   } 

   public static boolean isAscending(int i) {
      int next = i % 10;
      i /= 10;
      while (i > 0) {
         int digit = i % 10;
         if (digit > next) {
            return false;   
         }
         i /= 10;
         next = digit;
      }
      return true;
   }

   public static boolean hasDouble(int i) {
      int next = i % 10;
      i /= 10;
      while (i > 0) {
         int digit = i % 10;
         int dupCount = 0;
         while (i > 0 && digit == next) {
            dupCount++;
            next = digit;
            i /= 10;
            if (i > 0) {
               digit = i % 10;   
            }
         }
         if (dupCount == 1) {
            return true;   
         }
         i /= 10;
         next = digit;
      }
      return false;
   }
}
