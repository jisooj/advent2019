/*
--- Part Two ---

Now you're ready to decode the image. The image is rendered by stacking the layers and aligning the pixels with the same positions in each layer. The digits indicate the color of the corresponding pixel: 0 is black, 1 is white, and 2 is transparent.

The layers are rendered with the first layer in front and the last layer in back. So, if a given position has a transparent pixel in the first and second layers, a black pixel in the third layer, and a white pixel in the fourth layer, the final image would have a black pixel at that position.

For example, given an image 2 pixels wide and 2 pixels tall, the image data 0222112222120000 corresponds to the following image layers:

Layer 1: 02
         22

Layer 2: 11
         22

Layer 3: 22
         12

Layer 4: 00
         00

Then, the full image can be found by determining the top visible pixel in each position:

    The top-left pixel is black because the top layer is 0.
    The top-right pixel is white because the top layer is 2 (transparent), but the second layer is 1.
    The bottom-left pixel is white because the top two layers are 2, but the third layer is 1.
    The bottom-right pixel is black because the only visible pixel in that position is 0 (from layer 4).

So, the final image looks like this:

01
10

What message is produced after decoding your image?

Your puzzle answer was GZKJY

*/
import java.util.*;
import java.io.*;

public class p2 {
   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      String layers = in.nextLine();
      int width = 25;
      int height = 6;
      char[][] img = new char[height][width];
      decodeImage(layers, img, width, height);
      printImage(img);
   }

   public static void decodeImage(String layers, 
      char[][] img, 
      int w, 
      int h) {
      int area = w * h;
      // insert if existing pixel value is 0 or '2'
      for (int i = 0; i < layers.length() / area; i++) {
         for (int j = 0; j < area; j++) {
            int r = j / w;
            int c = j % w;
            if ((int)img[r][c] == 0 || img[r][c] == '2') {
               // overwrite
               int idx = i * area + j;
               img[r][c] = layers.charAt(idx);
            } // else ignore
         }
      }
   }

   public static void printImage(char[][] img) {
      for (int i = 0; i < img.length; i++) {
         System.out.println(img[i]);
      }   
   }
}
