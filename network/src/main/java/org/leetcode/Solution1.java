package org.leetcode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 刷题记录
 * @author leetcode
 */
public class Solution1 {

    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> ret=new ArrayList<>();
        for (int i = 0; i < nums.length-2; i++) {
            for (int j = i+1; j < nums.length-1; j++) {
                int k=nums.length-1;
                int low=j;
                while(k>low){
                    int pivot=(j+k)/2;
                    int temp=nums[i]+nums[low]+nums[pivot];
                    if (temp>0){
                        k=pivot-1;
                    }else if (temp==0){
                        ret.add(Arrays.asList(nums[i],nums[low],nums[pivot]));
                        break;
                    } else {
                        low=pivot+1;
                    }
                }
            }
        }
        ret=ret.stream().distinct().collect(Collectors.toList());
        return ret;
    }

    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map=new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            if (map.get(target-nums[i])!=null){
                return new int[]{map.get(target-nums[i]),i};
            }
            map.put(nums[i],i);
        }
        return null;
    }

    public int lengthOfLongestSubstring(String s) {
        int n=s.length(),res=0;
        Map<Character,Integer> map=new HashMap<>();
        int left=0;
        for (int i = 0; i < n; i++) {

            if (map.containsKey(s.charAt(i))){
                left=Math.max(left,map.get(s.charAt(i))+1);
            }

            map.put(s.charAt(i),i);
            res=Math.max(res,i-left+1);
        }

        return res;
    }

    public String minWindow(String s, String t) {
        //滑动窗口
        String res="";
        //mapT保存 t 的所有字符，及其个数
        HashMap<Character,Integer> mapT=new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            mapT.put(t.charAt(i),mapT.getOrDefault(t.charAt(i),0)+1);
        }

        //mapS 用于 保存 s中的字符，及其个数
        HashMap<Character,Integer> mapS=new HashMap<>();

        //count用于记录已匹配到的字符数，当count==t.length()时，找到一个满足的子串
        int count=0;
        //len用于保存 已找到满足条件的字串的最小长度， 用于更新
        int len=Integer.MAX_VALUE;
        //left、right 左右指针，指向字串的两端
        for (int right = 0,left=0; right < s.length(); right++) {
            //每一个字符添加到 mapS 中
            char cur=s.charAt(right);
            mapS.put(cur,mapS.getOrDefault(cur,0)+1);
            // 找到T中的字符且个数小于T中，count++，由于这里先更新mapS中的数目，所以有等号
            if (mapT.containsKey(cur) && mapS.get(cur) <= mapT.get(cur)) {
                count++;
            }

            //更新 left 指针
            //情况1: 左指针指向的char 不在 t 中
            //情况1: 左指针指向的char 在 t 中 ， 但当前字符数量 已 超过 t中对应数量
            char leftC=s.charAt(left);
            while (left<right && (!mapT.containsKey(leftC)|| mapS.get(leftC) > mapT.get(leftC))){
                int n=mapS.get(leftC)-1;
                mapS.put(leftC,n);
                left++;
                leftC=s.charAt(left);
            }
            if (count==t.length()   &&  right-left<len ){
                len=right-left;
                res=s.substring(left,right+1);
            }
        }

        return res;
    }

    public int maxLengthBetweenEqualCharacters(String s) {
        int ret=-1;
        for (int i = 0; i < s.length(); i++) {
            int lastIndex=s.lastIndexOf(s.charAt(i));
            if (lastIndex-i-1>ret){
                ret=lastIndex-i-1;
            }
        }
        return ret;
    }

    public void nextPermutation(int[] nums) {
        if (nums.length==1){
            return;
        }
        int i=nums.length-2;
        while (i>=0&&nums[i]>=nums[i+1]){
            i--;
        }
        if (i==-1){
            Arrays.sort(nums);
        }
        int minIndex= nums.length-1;
        while (nums[minIndex]<=nums[i]){
            minIndex--;
        }
        int temp=nums[i];
        nums[i]=nums[minIndex];
        nums[minIndex]=temp;

        Arrays.sort(nums,i+1,nums.length);
    }

    public int longestValidParentheses(String s) {
        Deque<Integer> stack=new ArrayDeque<>();
        int res=0;
        stack.push(-1);
        //栈保留未被匹配的索引信息
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i)=='('){
                stack.push(i);
            }else {
                stack.pop();
                if(stack.isEmpty()){
                    stack.push(i);
                }else {
                    res=Math.max(res,i-stack.peek());
                }
            }
        }
        return res;
    }

    public int search(int[] nums, int target) {
        // 对于一个部分有序的数组， 同样可以使用二分法
        //将数组一分为二，其中一定有一个是有序的，另一个可能是有序的，也能是部分有序。
        //此时有序部分用二分法查找。无序部分再一分为二，其中一个一定有序，另一个可能有序，可能无序。就这样循环.
        // 但是要格外注意细节
        int left = 0, right = nums.length - 1;
        if (right == -1) {
            return -1;
        }
        if (right == 0) {
            return nums[0] == target ? 0 : -1;
        }
        while (left <= right) {
            int pivot = (left + right) / 2;
            if (nums[pivot] == target) {
                return pivot;
            }
            if (nums[0] <= nums[pivot]) {
                if (nums[left] <= target && target < nums[pivot]) {
                    right = pivot - 1;
                } else {
                    left = pivot + 1;
                }
            } else {
                if (nums[pivot] < target && target <= nums[right]) {
                    left = pivot + 1;
                } else {
                    right = pivot - 1;
                }
            }
        }
        return -1;
    }

    public int largestIsland(int[][] grid) {
        int ret = 0;
        int n = grid.length;
        int[][] tag = new int[n][n];
        Map<Integer, Integer> area = new HashMap<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && tag[i][j] == 0) {
                    int t = i * n + j + 1;
                    area.put(t, largesIslandEvery(grid, i, j, tag, t));
                    ret = Math.max(ret, area.get(t));
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    int retZ = 1;
                    int[][] directions = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
                    Set<Integer> connectIsland = new HashSet<>();
                    for (int[] direction : directions) {
                        int nextI = i + direction[0], nextJ = j + direction[1];
                        if (!(nextI >= 0 && nextI < grid.length && nextJ >= 0 && nextJ < grid.length) || connectIsland.contains(tag[nextI][nextJ]) || tag[nextI][nextJ] == 0) {
                            continue;
                        } else {
                            retZ += area.get(tag[nextI][nextJ]);
                            connectIsland.add(tag[nextI][nextJ]);
                        }
                    }
                    ret = Math.max(ret, retZ);
                }
            }
        }
        return ret;
    }
    private int largesIslandEvery(int[][] grid, int i, int j, int[][] tag, int t) {
        int[][] directions = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
        Deque<Integer[]> deque = new ArrayDeque<>();
        deque.offer(new Integer[]{i, j});
        tag[i][j] = t;
        int ret = 1;
        while (!deque.isEmpty()) {
            Integer[] index = deque.poll();
            for (int[] direction : directions) {
                int nextI = index[0] + direction[0], nextJ = index[1] + direction[1];
                if (nextI >= 0 && nextI < grid.length && nextJ >= 0 && nextJ < grid.length && grid[nextI][nextJ] == 1 && tag[nextI][nextJ] == 0) {
                    deque.offer(new Integer[]{nextI, nextJ});
                    tag[nextI][nextJ] = t;
                    ret++;
                }
            }
        }
        return ret;
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);

        List<List<Integer>> ret=new ArrayList<>();
        List<Integer> list=new ArrayList<>();
        if (target<candidates[0]){
            return ret;
        }
        helper(candidates,target,0,list,ret);
        return ret;

    }
    private void helper(int[] candidates,int target,int start,List<Integer> list,List<List<Integer>> ret){
        if (target<0){
            return;
        }
        if (target==0){
            ret.add(new ArrayList<>(list));
            return;
        }
        for (int i = start; i < candidates.length ; i++) {
            list.add(candidates[i]);
            helper(candidates,target-candidates[i],i,list,ret);
            list.remove(list.size()-1);
        }
    }

    public int[] frequencySort(int[] nums) {
        Map<Integer, Integer> count = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            count.put(num, count.getOrDefault(num, 0) + 1);
            list.add(num);
        }
        list.sort((n1, n2) -> {
            int cnt1 = count.get(n1), cnt2 = count.get(n2);
            return cnt1 != cnt2 ? cnt1 - cnt2 : n2 - n1;
        });
        for (int i = 0; i < nums.length; i++) {
            nums[i] = list.get(i);
        }
        return nums;
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String,List<String>> map=new HashMap<>();
        for (String str:strs){
            char[] charOfStr=str.toCharArray();
            Arrays.sort(charOfStr);
            String key=new String(charOfStr);
            List<String> list = map.getOrDefault(key, new ArrayList<>());
            list.add(str);
            map.put(key,list);
        }
        return new ArrayList<List<String>>(map.values());
    }


    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> ret = new ArrayList<>();
        if (candidates[0] > target) {
            return ret;
        }
        List<Integer> list = new ArrayList<>();
        combinationSum2Helper(candidates, target, 0, list, ret);
        return ret;
    }

    private void combinationSum2Helper(int[] candidates, int target, int start, List<Integer> list, List<List<Integer>> ret) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            ret.add(new ArrayList<>(list));
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue;
            }
            list.add(candidates[i]);
            combinationSum2Helper(candidates, target - candidates[i], i + 1, list, ret);
            list.remove(list.size() - 1);
        }
    }




    public static void main(String[] args) {
        Solution1 solution1=new Solution1();

        List<List<Integer>> lists = solution1.combinationSum2(new int[]{1,2,2,2,5}, 3);
        System.out.println(lists);

    }

}

