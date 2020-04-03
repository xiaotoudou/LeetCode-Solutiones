## 通配符匹配_44

> 题目：
> 给定一个字符串 (s) 和一个字符模式 (p) ，实现一个支持 '?' 和 '*' 的通配符匹配。
>
> - '?' 可以匹配任何单个字符。
> - '*' 可以匹配任意字符串（包括空字符串）。
> - 两个字符串完全匹配才算匹配成功。
>
> 说明:
>
> - s 可能为空，且只包含从 a-z 的小写字母。
> - p 可能为空，且只包含从 a-z 的小写字母，以及字符 ? 和 *。
>
> 示例 :
>
> ```txt
> 输入:
> s = "aa"
> p = "a"
> 输出: false
> 解释: "a" 无法匹配 "aa" 整个字符串。
> 示例 2:
> 
> 输入:
> s = "aa"
> p = "*"
> 输出: true
> 解释: '*' 可以匹配任意字符串。
> 示例 3:
> 
> 输入:
> s = "cb"
> p = "?a"
> 输出: false
> 解释: '?' 可以匹配 'c', 但第二个 'a' 无法匹配 'b'。
> 示例 4:
> 
> 输入:
> s = "adceb"
> p = "*a*b"
> 输出: true
> 解释: 第一个 '*' 可以匹配空字符串, 第二个 '*' 可以匹配字符串 "dce".
> 示例 5:
> 
> 输入:
> s = "acdcb"
> p = "a*c?b"
> 输入: false
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/wildcard-matching/)
>
> Edited by xiaotudou on 2020/4/1

----

### 递归

这题和之前的正则表达式匹配很相似，分解成两个部分来处理。

1. 如果没有星号，遇到问号直接跳过一个字符，其他字符就一一匹配。字符串s遍历完之后，p应该也会匹配完。
2. 加入处理星号的判断。遇到星号，模式串的星号去掉，星号可以匹配字符串的0个或多个字符。

有一种很糟糕的情况，如果最后一个星号没有匹配上，会逐个往前递归，中间存在很多冗余计算，结果超时。可以带记忆表缓存中间结果来优化。

Java实现

```java
public boolean isMatch(String s, String p) {
        int i=0,j=0;
        while(i<s.length()){
            if(j<p.length() && (p.charAt(j)=='?' || p.charAt(j) == s.charAt(i))){
                ++i;
                ++j;
            }else if(j<p.length() && p.charAt(j)=='*'){
              // a****b 多个* 可以简化成一个
                while(j<p.length() && p.charAt(j)=='*') ++j;
                String tmpP=p.substring(j);
                for(int k=0; k<s.length()-i+1; ++k){
                    if(isMatch(s.substring(i+k), tmpP))
                        return true;
                }
                return false;
            }else{
                return false;
            }
        }
        for(;j<p.length();++j){
            if(p.charAt(j)!='*') 
                return false;
        }
        return true;
    }
```

### 动态规划

s串从前往后逐个增加，可以根据前一个字符的结果线性时间得到当前字符的解。和 “最长重复子串”的解法相似，利用S*P的二维数组缓存S的前 i 个字符与P的前 j 个字符的匹配结果。

动态转移方程：

1. p的字符是'?' ，或是字符能匹配上，跳过当前字符的结果与上一个字符相同， $dp[i+1][j+1]=dp[i][j]$ 
2. p是星号，那么可以不匹配或者匹配当前字符，$dp[i+1][j+1]=dp[i+1][j]||dp[i][j+1]$ , $dp[i][j+1]$ 表示不匹配该字符，直接取当前s的子串与p的上一个字符的匹配结果。$dp[i+1][j]$ 表示匹配当前字符，直接取 s 上一个字符匹配到这的结果。

 Java实现， 时间复杂度O(SP)，空间复杂度O(SP)

```java
public boolean isMatch(String s, String p) {
        int sLen = s.length();
        int pLen = p.length();
  
   //   多一行和一列，处理 s="" 和 p="" 的情况
  //    s串逐个增加与模式串p匹配，可根据上一个字符的结果得到当前的结果
        boolean[][] dp = new boolean[sLen+1][pLen+1];
       
        dp[0][0]=true;  // s="" 且 p=""
        for(int i=0;i<pLen && p.charAt(i)=='*';++i){ // 处理 s="" ,p可能是"***" 的情况
            dp[0][i+1]=true;
        }

        for(int i=0;i<sLen;++i){
            for(int j=0;j<pLen;++j){
                if(p.charAt(j)==s.charAt(i) || p.charAt(j)=='?'){
                    dp[i+1][j+1]=dp[i][j];
                }else if(p.charAt(j)=='*'){
                    dp[i+1][j+1]=dp[i+1][j] || dp[i][j+1];
                }
            }
        }
        return dp[sLen][pLen];
    }
```

### 贪心

这个问题中有一个特性可以利用，**那就是只用记录最后一个星号即可。如果后面再遇到星号，说明在当前星号之前的字符都是可以匹配的，那么之前的字符可以直接滤过，将当前星号作为新的起点。**

Java实现， 时间复杂度 O(min(s,p))，空间复杂度 O(1)

```java
public boolean isMatch(String s, String p) {
        int i=0,j=0;
        int startP=-1;
        int sTmp=-1;
        while(i<s.length()){
            if(j<p.length() && (p.charAt(j)=='?' || p.charAt(j) == s.charAt(i))){
                ++i;
                ++j;
            }else if(j<p.length() && p.charAt(j)=='*'){
                startP=j+1;
                sTmp=i;
                ++j;
            }else if(startP == -1){
                return false;
            }
            else{
                j=startP;
                i=sTmp+1;
                sTmp=i;
            }
        }
        for(;j<p.length();++j){
            if(p.charAt(j)!='*') 
                return false;
        }
        return true;
    }
```

