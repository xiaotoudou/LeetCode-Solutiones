## 无重复字符的最长子串_3

> 题目：
> 给定一个字符串，请你找出其中不含有重复字符的 **最长子串** 的长度。
>
> 示例 :
>
> ```txt
> 输入: "abcabcbb"
> 输出: 3 
> 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
> 
> 输入: "bbbbb"
> 输出: 1
> 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/)
>
> Edited by xiaotudou on 2020/4/11

----

### 暴力法

穷举所有子串 $O(n^2)$ ，检查是否重复 O(n) ，时间复杂度 $O(n^3)$ 。

### 动态规划

使用二维数组模型穷举，$dp[i][j]$ 表示字符串下标从i 到j的状态，下面是剪枝策略：

1. 每一行的遍历遇到一个字符已经包含在此之前的子串中，那么该行中剩余部分就不用检查，因为已经包含了重复子串
2. 下一行检查的位置可以从上一行中断的位置开始，因为前面的子串删去上一行第一个字符就是当前行的状态
3. 使用Set来缓存已经遍历过子串的状态，实现O(1)时间检查一个子串是否符合要求

时间复杂度 O(2n)，每个字符会访问两次。空间复杂度O(n) 

```java
public int lengthOfLongestSubstring(String s) {
        if(s == null ||s.length()<1) return 0;

        int maxLen=0;
        Set<Character> set = new HashSet();
        for(int i=0,start=0;i<s.length();++i){
            for(int j=start;j<s.length();++j){
                if(set.contains(s.charAt(j))){
                    start=j;
                    break;
                }
                set.add(s.charAt(j));
            }
            if(maxLen<set.size()){
                maxLen=set.size();
            }
            set.remove(s.charAt(i));
        }
        return maxLen;
    }
```

### 滑动窗口

使用滑动窗口模型穷举，和二维数组很相似，窗口右侧遍历数组新进入的字符可能会带来重复字符，修改左侧窗口边界的位置，使窗口中的子串复核要求为止。滑动窗口移动的过程中记录窗口长度的最大值。

时间复杂度 O(2n)，可看作是left 和 right两个指针都走了一遍字符串。

```java
public int lengthOfLongestSubstring(String s) {
        if(s == null ||s.length()<1) return 0;

        int left=0,right=0;
        int maxLen=0;
        Set<Character> state = new HashSet();
        while(right<s.length()){
            while(left<right && state.contains(s.charAt(right))){
                state.remove(s.charAt(left));
                ++left;
            }
            state.add(s.charAt(right));
            int newLen=right-left+1;
            if(newLen>maxLen){
                maxLen = newLen;
            }
            ++right;
        }
        return maxLen;
    }
```

### 优化滑动窗口

left没必要逐个增加。使用HashMap存已经遍历过的char和下标，left直接跳到s.charAt(right)重复字符的右侧。

时间复杂度 减为$O(n)$ ，只有right指针会遍历一遍字符串。

```java
public int lengthOfLongestSubstring(String s) {
        if(s == null ||s.length()<1) return 0;

        int left=0,right=0;
        int maxLen=0;
        HashMap<Character,Integer> state = new HashMap();
        while(right<s.length()){
            if(state.containsKey(s.charAt(right))){ 
              	// 每次比较left与已包含字符的下标，如果是在left左侧的字符是不会更新left的。
                // 不需要删除移动left之后左侧的字符
                left = Math.max(left,state.get(s.charAt(right))+1); 
            }
            state.put(s.charAt(right), right);
            maxLen = right-left+1>maxLen?right-left+1:maxLen;
            ++right;
        }
        return maxLen;
    }
```