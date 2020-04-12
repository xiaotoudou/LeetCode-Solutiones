## 编辑距离_72

> 题目：
> 给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的**最少操作数** 。
>
> 你可以对一个单词进行如下三种操作：
>
> 1. 插入一个字符
> 2. 删除一个字符
> 3. 替换一个字符
>
> 示例 :
>
> ```txt
> 输入：word1 = "horse", word2 = "ros"
> 输出：3
> 解释：
> horse -> rorse (将 'h' 替换为 'r')
> rorse -> rose (删除 'r')
> rose -> ros (删除 'e')
> 
> 输入：word1 = "intention", word2 = "execution"
> 输出：5
> 解释：
> intention -> inention (删除 't')
> inention -> enention (将 'i' 替换为 'e')
> enention -> exention (将 'n' 替换为 'x')
> exention -> exection (将 'n' 替换为 'c')
> exection -> execution (插入 'u')
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/edit-distance)
>
> Edited by xiaotudou on 2020/4/10

----

### 动态规划

看到最少操作数，属于最优子结构问题，考虑用动态规划方向。两个字符串的匹配问题，通常是利用二维数组表示子串的匹配情况，匹配后面的子串时，只需要通过上一个子串的状态就可以在常数时间得到当前子串的状态。

1. 如果word1或者word2是空串，只需要删除或者插入length次就可以转换。
2. 二维数组 $dp[i][j]$ 表示word1的 前i个字符子串转换成 word2 的前j个字符子串最少需要多少步。此时有两种情况：
   - $word1[i] == word2[j]$，说明当前字符可以匹配，不需要转换,  $dp[i][j]=dp[i-1][j-1]$ 最少需要操作数与忽略这个字符时相同。
   - $word1[i]  != word2[j]$ ，该字符需要转换，但是需要讨论哪种方法转换，步数最少，取最少的步数作为当前结果
     1. $dp[i-1][j]$  删除当前字符，取上一行，也就是没有当前字符的结果加一。
     2. $dp[i][j-1]$ 插入当前字符，取上一个字符匹配的步数加一。例如word1 的长度短于word2 时。
     3. $dp[i-1][j-1]$ 替换当前字符匹配，两个子串都减1时的情况加一。例如 "a" 匹配 "b" 。
3. 初始化第一行处理空串的情况，也方便统一字符串的处理逻辑。模式串从第一个字符开始去转换成目的子串，每个字符的结果根据第二步中的动态转移方程来处理。直到模式串的字符转换完成后，结果在$dp[word1.length][word2.length]$

时间复杂度 O(mn)， m和n分别是两个字符串的长度。空间复杂度 O(mn) ，其实每个节点的状态只与上一行和当前行的结果相关，比较容易优化到O(n)。但是代码变得复杂，可读性变差。

```java
public int minDistance(String word1, String word2) {
        int[][] dp = new int[word1.length()+1][word2.length()+1];
  			
  			// 初始化第一列，目的串为空串
        for(int i=0;i<=word1.length();++i){
            dp[i][0]=i;
        }
  			// 初始化第一行，模式串为空串
        for(int j=0;j<=word2.length();++j){
            dp[0][j]=j;
        }
  
        for(int i=1;i <= word1.length();++i){
            for(int j=1;j <= word2.length();++j){
                dp[i][j] = word1.charAt(i-1)==word2.charAt(j-1)?
                  dp[i-1][j-1]  
                  :1 + Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1])); 
            }
        }
        return dp[word1.length()][word2.length()];
    }
```