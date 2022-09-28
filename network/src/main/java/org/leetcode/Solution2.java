package org.leetcode;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import org.apache.derby.iapi.jdbc.AuthenticationService;

import java.util.*;

/**
 * @author tjp
 */
public class Solution2 {

    /**
     * 698
     */
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = Arrays.stream(nums).sum();
        if (sum % k != 0) {
            return false;
        }
        int target = sum / k;

        Arrays.sort(nums);

        int[] bucket=new int[k];
        return helper(nums, nums.length-1,bucket,k,target);
    }
    private boolean helper(int[] nums,int index, int[] bucket,int k,int target){
        if (index== -1){
            return true;
        }

        for (int i = 0; i < k; i++) {
            if (bucket[i]+nums[index]>target){
                continue;
            }
            if (i>0 && bucket[i]==bucket[i-1]){
                continue;
            }
            bucket[i]+=nums[index];
            System.out.print("添加----"+index+":"+i+"---");
            System.out.println(Arrays.toString(bucket));
            if (helper(nums,index-1,bucket,k,target)){
                return true;
            }
            bucket[i]-=nums[index];
            System.out.println(Arrays.toString(bucket));
        }
        return false;
    }

    /**
     * 5 最长回文子串
     * <b>不会做</b> <br>
     * <b>动态规划</b>
     */
    public String longestPalindrome(String s) {
       int  n = s.length();
       int maxLen=0,left=0,right=0;
       boolean[][] dp = new boolean[n][n];
        for (int i = n-1; i >= 0; i--) {
            for (int j = n-1;j >= 0 ; j--) {
                if (i<=j&&s.charAt(i)==s.charAt(j)){
                    if (j-i==1){
                        dp[i][j]=true;
                    }else if(j-i>1){
                        dp[i][j]=dp[i+1][j-1];
                    }
                }else {
                    dp[i][j]=false;
                }

                if (dp[i][j]&&j-i+1>maxLen){
                    left=i;
                    right=j;
                    maxLen=j-i+1;
                }
            }
        }
        return s.substring(left,right+1);
    }

    public boolean canFormArray(int[] arr, int[][] pieces) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            map.put(arr[i], i);
        }
        for (int[] temp : pieces) {
            if (temp.length > 1) {
                int start = temp[0];
                if (!map.containsKey(start)) {
                    return false;
                }
                int arrStart = map.get(start);
                for (int j = 1; j < temp.length; j++) {
                    if (!map.containsKey(temp[j]) || map.get(temp[j]) != arrStart + j) {
                        return false;
                    }
                }
            } else {
                if (!map.containsKey(temp[0])) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 6. Z字变换
     *    找规律
     */
    public String convert(String s, int numRows) {
        int n = s.length();
        //周期
        int t = 2 * numRows - 2;
        if (numRows == 1 || numRows >= n) {
            return s;
        }

        int column = (n+t-1) / t * (numRows - 1);
        char[][] temp = new char[numRows][column];
        for (int i = 0, x = 0, y = 0; i < numRows; i++) {
            temp[x][y] = s.charAt(i);
            if (i % t < numRows - 1) {
                x++;
            } else {
                x--;
                y++;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (char[] row : temp) {
            for (char c : row) {
                if (c != 0) {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    public int[] decrypt(int[] code, int k) {
        int[] ret = new int[code.length];
        if(k==0){
            return ret;
        }
        int flag = 1;
        if (k<0){
            flag = -1;
        }
        for (int i = 0; i < code.length; i++) {
            for (int j = 1; j <= k*flag ; j++) {
                ret[i] += code[(i+flag*j+code.length)%(code.length)];
            }
        }
        return ret;
    }

    public int reverse(int x) {
        return x;
    }

    /**
     * 788
     */
    public int rotatedDigits(int n) {
        int[] checked = {0, 0, 1, -1, -1, 1, 1, -1, 0, 1};
        boolean a = true, b = false;
        int count = 0;
        for (int i = 0; i < n; i++) {
            String s = String.valueOf(i);
            for (int j = 0; j < s.length(); j++) {
                int i1 = checked[s.charAt(i) - '0'];
                if (i1 == -1) {
                    a = false;
                    break;
                }
                if (i1 == 1) {
                    b = true;
                }
            }
            if (a && b) {
                count++;
            }
        }
        return count;
    }

    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<Integer>();
        int m = words.length, n = words[0].length(), ls = s.length();
        for (int i = 0; i < n; i++) {
            if (i + m * n > ls) {
                break;
            }
            Map<String, Integer> differ = new HashMap<String, Integer>();
            for (int j = 0; j < m; j++) {
                String word = s.substring(i + j * n, i + (j + 1) * n);
                differ.put(word, differ.getOrDefault(word, 0) + 1);
            }
            for (String word : words) {
                differ.put(word, differ.getOrDefault(word, 0) - 1);
                if (differ.get(word) == 0) {
                    differ.remove(word);
                }
            }
            for (int start = i; start < ls - m * n + 1; start += n) {
                if (start != i) {
                    String word = s.substring(start + (m - 1) * n, start + m * n);
                    differ.put(word, differ.getOrDefault(word, 0) + 1);
                    if (differ.get(word) == 0) {
                        differ.remove(word);
                    }
                    word = s.substring(start - n, start);
                    differ.put(word, differ.getOrDefault(word, 0) - 1);
                    if (differ.get(word) == 0) {
                        differ.remove(word);
                    }
                }
                if (differ.isEmpty()) {
                    res.add(start);
                }
            }
        }
        return res;
    }



    public static void main(String[] args) {
        Solution2 s=new Solution2();

        List<Integer> barfoothefoobarman = s.findSubstring("wordgoodgoodgoodbestword", new String[]{"word", "good","good","best"});
        System.out.println(barfoothefoobarman);
    }

}


