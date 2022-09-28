package org.dataStructure;

import java.util.*;

class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(-1, head), prev = dummy;
        //dummy 锚节点，始终指向 应返回链表的 头节点
        //prev  辅助节点，记录上一次翻转完毕之后的最后一个节点
        while (true) {
            ListNode tail = prev;
            //判断是否还有K个节点
            for (int i = 1; i <= k; i++) {
                tail = tail.next;
                if (tail == null) {
                    return dummy.next;
                }
            }

            //依次翻转k个节点,需要翻转k-1次
            //两个辅助节点，分别记录当前节点， 初始当前节点是上一阶段最后一个节点的next
            //                          与需要翻转的下一个节点
            //头插法，在for循环中，prev始终指向这一截链表的第一个节点，翻转完毕后，调整为最后一个节点(
            ListNode cur = prev.next, next;
            for (int i = 1; i < k; i++) {
                next = cur.next;
                cur.next = next.next;
                next.next = prev.next;
                prev.next = next;
            }
            prev = cur;
        }


    }

    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode node = head.next;
        head.next = swapPairs(node.next);
        node.next = head;
        return node;
    }

    public int flipLights(int n, int presses) {
        Set<Integer> seen = new HashSet<Integer>();
        for (int i = 0; i < 1 << 4; i++) {
            int[] pressArr = new int[4];
            for (int j = 0; j < 4; j++) {
                pressArr[j] = (i >> j) & 1;
            }
            int sum = Arrays.stream(pressArr).sum();
            if (sum % 2 == presses % 2 && sum <= presses) {
                int status = pressArr[0] ^ pressArr[1] ^ pressArr[3];
                if (n >= 2) {
                    status |= (pressArr[0] ^ pressArr[1]) << 1;
                }
                if (n >= 3) {
                    status |= (pressArr[0] ^ pressArr[2]) << 2;
                }
                if (n >= 4) {
                    status |= (pressArr[0] ^ pressArr[1] ^ pressArr[3]) << 3;
                }
                seen.add(status);
                System.out.print(Arrays.toString(pressArr));
                System.out.print("------");
                System.out.println(status);
            }
        }
        seen.forEach(System.out::println);
        return seen.size();
    }

    /**
     * 剑指 Offer 12. 矩阵中的路径
     * @param board 矩阵
     * @param word  字符串
     * @return 是否存在
     */
    public boolean exist(char[][] board, String word) {
        //对每一个点 dfs
        int m = board.length, n = board[0].length;
        //标记数组，防止重复访问
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                boolean flag = check(board, word, visited, i, j, 0);
                if (flag) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param board 矩阵
     * @param word  字符串
     * @param visited 标记数组
     * @param i 矩阵中的i，j位置
     * @param k 字符串的已匹配长度
     */
    private boolean check(char[][] board, String word, boolean[][] visited, int i, int j, int k) {
        if (word.charAt(k) != board[i][j]) {
            return false;
        }

        if (k == word.length() - 1) {
            return true;
        }
        //方向数组
        int[][] direction = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
        visited[i][j] = true;
        boolean result = false;
        for (int[] d : direction) {
            int newi = i + d[0], newj = j + d[1];
            if (newi >= 0 && newi < board.length && newj >= 0 && newj < board[0].length) {
                if (!visited[newi][newj]) {
                    boolean flag = check(board, word, visited, newi, newj, k + 1);
                    if (flag) {
                        result = true;
                        break;
                    }
                }
            }
        }
        visited[i][j]=false;
        return result;
    }


    public static void main(String[] args) {
        Solution s = new Solution();
        boolean abcd = s.exist(new char[][]{{'a', 'b'}, {'c', 'd'}}, "abdc");
        System.out.println(abcd);

    }
}