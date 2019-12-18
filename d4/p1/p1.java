/*
--- Day 4: Secure Container ---

You arrive at the Venus fuel depot only to discover it's protected by a password. The Elves had written the password on a sticky note, but someone threw it out.

However, they do remember a few key facts about the password:

    It is a six-digit number.
    The value is within the range given in your puzzle input.
    Two adjacent digits are the same (like 22 in 122345).
    Going from left to right, the digits never decrease; they only ever increase or stay the same (like 111123 or 135679).

Other than the range rule, the following are true:

    111111 meets these criteria (double 11, never decreases).
    223450 does not meet these criteria (decreasing pair of digits 50).
    123789 does not meet these criteria (no double).

How many different passwords within the range given in your puzzle input meet these criteria?

Your puzzle answer was 889.
*/
import java.util.*;
import java.io.*;

public class p1 {
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
         if (digit == next) {
            return true;
         } else {
            i /= 10;
            next = digit;
         }
      }
      return false;
   }
}
