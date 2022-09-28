package org.dataStructure;


import com.sun.org.apache.bcel.internal.generic.ARETURN;

import javax.swing.tree.TreeNode;
import java.util.*;

/**
 * @author cy
 * @date 2022/9/7
 */
public class Test {

    public static int er(int[] arr, int target) {
        int left = 0, right = arr.length - 1, mid;
        while (left <= right) {
            mid = (left + right) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    public static void sorted1(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int flag = i;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[flag] < arr[j]) {
                    int tem = arr[j];
                    arr[j] = arr[flag];
                    arr[flag] = tem;
                    flag--;
                }
            }
            System.out.println(Arrays.toString(arr));
        }
    }

    public static void shell(int[] arr) {
        int skip = 2;
        for (int gap = arr.length / skip; gap > 0; gap /= skip) {
            for (int i = gap; i < arr.length; i++) {
                int cur = arr[i];
                int j = i - gap;
                while (j >= 0 && arr[j] >= cur) {
                    arr[j + gap] = arr[j];
                    j -= gap;
                }
                arr[j + gap] = cur;
            }
        }
    }

    public static void pop(int[] arr) {
        boolean exchange;
        for (int i = 0; i < arr.length - 1; i++) {
            exchange = false;
            for (int j = arr.length - 1; j > i; j--) {
                if (arr[j] < arr[j - 1]) {
                    exchange = true;
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                }
            }
            if (!exchange) {
                break;
            }
            System.out.println(Arrays.toString(arr));
        }
    }

    public static void quick(int[] arr, int low, int high) {
        int loc;
        if (low < high) {
            loc = part(arr, low, high);
            quick(arr, low, loc - 1);
            quick(arr, loc + 1, high);
        }

    }

    private static int part(int[] arr, int low, int high) {
        int temp = arr[low];
        while (low < high) {
            while (low < high && arr[high] >= temp) {
                high--;
            }
            if (low < high) arr[low] = arr[high];
            while (low < high && arr[low] <= temp) {
                low++;
            }
            if (low < high) arr[high] = arr[low];
        }
        arr[low] = temp;
        return low;
    }


    public static int p(int n) {
        if (n == 1)
            return 1;
        else
            return n * p(n - 1);
    }

    public static void di(int a, int b) {
        if (a % b == 0) {
            System.out.println(b);
        } else {
            a = a - b * (a / b);
            di(Math.max(a, b), Math.min(a, b));
        }
    }


    public static void main(String[] args) {
//        int[] arr={49,38,65,97,76,13,27,49,55,4,2131,12,1,2,23,4,6,7,112};
//        int[] arr={49,38,65,97,76};
//        Test.quick(arr,0,arr.length-1);
//        System.out.println(Arrays.toString(arr));
        System.out.println(Math.pow(10,8));
    }
}
    class ListNode {
      int val;
      ListNode next;
      public ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

class Solution2 {
    public ListNode mergeKLists(ListNode[] lists) {

       ArrayList<ListNode> list=new ArrayList<>();
       for (ListNode node : lists){
           while (node != null){
               list.add(node);
               node=node.next;
           }
       }
       list.sort(((o1, o2) -> o1.val - o2.val));
       if (list.size()==0){
           return null;
       }
       ListNode head=new ListNode();
       ListNode node=head;
        for (ListNode listNode : list) {
            node.next = new ListNode(listNode.val);
            node = node.next;
        }
        return head.next;
    }

    public static void main(String[] args) {

    }
}

