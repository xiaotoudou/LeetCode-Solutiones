## 最长回文串_5

> 题目：
> 给定一个字符串 `s`，找到 `s` 中最长的回文子串。你可以假设 `s` 的最大长度为 1000。
>
> 示例 :
>
> ```txt
> 输入: "babad"
> 输出: "bab"
> 注意: "aba" 也是一个有效答案。
> 
> 输入: "cbbd"
> 输出: "bb"
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/longest-palindromic-substring/)
>
> Edited by xiaotudou on 2020/4/2

----

### 中心扩展法

s字符串有n 个字符，有n个字符，n-1个空档。

1. 以**字符为中心**向两边匹配，回文串长度为奇数，长度为odd*2-1
2. 以两个字符中间的空档作为中心往两边匹配，长度为偶数，长度为even*2

java 实现， 最坏情况，所有字符都是相同的，时间复杂度$O(n^2)$， 空间复杂度 O(1)

```java
private int match(String s,int left,int right){
        int len=0;
        while(left>=0 && right<s.length() 
              && s.charAt(left--) == s.charAt(right++)) ++len; 
        return len;
    }
    public String longestPalindrome(String s) {
        if(s.isEmpty()) return "";

        String res=s.substring(0,1); // 取第一个字符作为初始条件

        for(int i=1;i<s.length();++i){
            int even = match(s,i-1,i); // 空档为中心
            int odd = match(s,i,i); // 字符为中心
            if(even*2>(odd*2-1) && even*2>res.length()){
                res=s.substring(i-even,i+even);
            }else if((odd*2-1)>res.length()){
                res=s.substring(i-odd+1,i+odd);
            }
        }
        return res;
    }
```

### 暴力法

列举s的所有子串$O(n^2)$，检查该子串是不是回文串O(n)，时间复杂度$O(n^3)$ 。

### 动态规划

如果一个子串的从 i 到 j，已知$p[i][j]$ 的状态，$p[i-1][j+1]=p[i][j]\&\&s[i-1]==s[j+1]$ ，可以常数时间判断该子串是不是回文串，暴力法的时间复杂度可优化到$O(n^2)$ ，空间复杂度 $O(n^2)$。

先初始化一个字符的子串，两个字符的子串。三个字符子串根据一个字符可以得出，四个字符根据两个字符的子串可以得出，以此类推，直到字符长度等于s。

```java
public String longestPalindrome(String s) {
        int len = s.length();
        boolean[][] dp = new boolean[len][len];
        String res = len > 0 ? s.substring(0, 1) : "";
        for(int subLen=1;subLen<=len;++subLen){ // 从长度为1开始逐个试探子串
            for(int start=0, end = subLen-1;end<len;++start,++end){
                if((subLen==1||subLen==2||dp[start+1][end-1]) // 一个字符和两个字符放在前面单独处理
                &&s.charAt(start)==s.charAt(end)){
                    dp[start][end]=true;
                    if(subLen>res.length()){
                        res=s.substring(start,end+1);
                    }
                }
            }
        }
        return res;
    }
```

### Manacher 算法

时间复杂度O(n), 空间复杂度O(n)