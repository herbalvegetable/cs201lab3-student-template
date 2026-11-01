public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 

      // write your codes here
     private static class Node {
          int val;
          Node left, right;

          Node(int val) {
               this.val = val;
          }
     }

     private static final String[] NAMES = { "Inorder", "Preorder", "Postorder" };
     private static final int[][] ASSIGNMENTS = {
               { 0, 1, 2 }, { 0, 2, 1 }, { 1, 0, 2 }, { 1, 2, 0 }, { 2, 0, 1 }, { 2, 1, 0 } };

     public static String verify(String traversal1, String traversal2, String traversal3) {
          String[] given = { traversal1, traversal2, traversal3 };

          // try every way of labelling the 3 strings as inorder/preorder/postorder
          for (int[] a : ASSIGNMENTS) {
               // a[i] = the traversal type given[i] is assumed to be (0=in, 1=pre, 2=post)
               String[] byType = new String[3];
               for (int i = 0; i < 3; i++) {
                    byType[a[i]] = given[i];
               }
               if (isConsistent(byType[0], byType[1], byType[2])) {
                    return "Traversal 1 - " + NAMES[a[0]] + ", Traversal 2 - " + NAMES[a[1]]
                              + ", Traversal 3 - " + NAMES[a[2]];
               }
          }
          return "Invalid traversals";
     }

     // Rebuild the BST by inserting the preorder values, then compare all 3 traversals
     private static boolean isConsistent(String in, String pre, String post) {
          int[] inArr = parse(in), preArr = parse(pre), postArr = parse(post);
          if (inArr == null || preArr == null || postArr == null) {
               return false;
          }
          if (inArr.length != preArr.length || inArr.length != postArr.length) {
               return false;
          }

          Node root = null;
          for (int v : preArr) {
               root = insert(root, v);
          }

          StringBuilder inSb = new StringBuilder(), preSb = new StringBuilder(), postSb = new StringBuilder();
          inorder(root, inSb);
          preorder(root, preSb);
          postorder(root, postSb);
          return inSb.toString().equals(join(inArr)) && preSb.toString().equals(join(preArr))
                    && postSb.toString().equals(join(postArr));
     }

     private static int[] parse(String s) {
          if (s == null || s.isEmpty()) {
               return new int[0];
          }
          String[] parts = s.split("-");
          int[] arr = new int[parts.length];
          try {
               for (int i = 0; i < parts.length; i++) {
                    arr[i] = Integer.parseInt(parts[i].trim());
               }
          } catch (NumberFormatException e) {
               return null;
          }
          return arr;
     }

     private static String join(int[] arr) {
          StringBuilder sb = new StringBuilder();
          for (int v : arr) {
               sb.append(v).append('-');
          }
          return sb.toString();
     }

     private static Node insert(Node node, int v) {
          if (node == null) {
               return new Node(v);
          }
          if (v < node.val) {
               node.left = insert(node.left, v);
          } else if (v > node.val) {
               node.right = insert(node.right, v);
          }
          return node;
     }

     private static void inorder(Node n, StringBuilder sb) {
          if (n == null) {
               return;
          }
          inorder(n.left, sb);
          sb.append(n.val).append('-');
          inorder(n.right, sb);
     }

     private static void preorder(Node n, StringBuilder sb) {
          if (n == null) {
               return;
          }
          sb.append(n.val).append('-');
          preorder(n.left, sb);
          preorder(n.right, sb);
     }

     private static void postorder(Node n, StringBuilder sb) {
          if (n == null) {
               return;
          }
          postorder(n.left, sb);
          postorder(n.right, sb);
          sb.append(n.val).append('-');
     }
}