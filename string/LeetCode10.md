## 正则表达式匹配_10

> 题目：
> 给你一个字符串 `s` 和一个字符规律 `p`，请你来实现一个支持 `'.'` 和 `'*'` 的正则表达式匹配。
>
> '.' 匹配任意单个字符
> '*' 匹配零个或多个前面的那一个元素
>
> 所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。
>
> 说明:
>
> s 可能为空，且只包含从 a-z 的小写字母。
> p 可能为空，且只包含从 a-z 的小写字母，以及字符 . 和 *。
>
> 示例 1:
>
> ```txt
> 输入:
> s = "aa"
> p = "a"
> 输出: false
> 解释: "a" 无法匹配 "aa" 整个字符串。
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/regular-expression-matching/)
>
> Edited by xiaotudou on 2020/3/13

----

### 回溯

第六行存在冗余计算，如果第一种试探往后走了很多步发现不能匹配，第二种试探方法会再次重复这个过程。

Java实现

```java
private boolean helper(String s, int start, String p, int index){
        if(index == p.length()) return start == s.length();
        char c = p.charAt(index);
        boolean first = start<s.length() && (c == s.charAt(start)|| c == '.');
        if(p.length() - index >= 2 && p.charAt(index+1) == '*'){
            return helper(s,start,p,index+2) ||  // 匹配0个
              (first && helper(s,start+1,p,index));
        }
        else{
            return first && helper(s,start+1,p,index+1);
        }
}
public boolean isMatch(String s, String p){
		   return helper(s,0,p,0);
}
```

### 动态规划

使用中间表缓存结果，每次递归先查表，匹配过就直接返回结果；如果没匹配过就匹配，结果存表。

- boolean数组默认false，不能识别该字符是遍历过，还是不能匹配。因此使用Boolean对象数组，没遍历过就是null

- 数组长度为length + 1，这样省去了处理边界的操作

- 之前直接return的结果存中间表

Java实现，时间复杂度O(mn), m 和 n 分别是字符串和模式串的长度；空间复杂度O(mn)，忽略了递归过程栈的消耗
```java
private boolean helper(String s, int start, String p, int index, Boolean[][] dp){
        if(dp[start][index] != null) // 先查表
            return dp[start][index];

        boolean res;  
        if(index == p.length()){
            res = start == s.length();
        }else{
            char c = p.charAt(index);
            boolean first = start<s.length() && (c == s.charAt(start)|| c == '.');
            if(p.length() - index >= 2 && p.charAt(index+1) == '*'){
                res = helper(s,start,p,index+2,dp) ||  //匹配0个
                (first && helper(s,start+1,p,index,dp));
            }
            else{
                res = first && helper(s,start+1,p,index+1,dp);
            }
        }
        dp[start][index] = new Boolean(res); //之前直接return的计算结果存到中间表
        return res;
    }

    public boolean isMatch(String s, String p){
        Boolean[][] dp = new Boolean[s.length()+1][p.length()+1];
        return helper(s,0,p,0,dp);
    }
```