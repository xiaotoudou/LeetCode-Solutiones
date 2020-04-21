## xxx_xx

> 题目：
> xxx
>
> 示例 :
>
> ```txt
> 
> ```
>
> 题目来源： 力扣 (LeetCode)
>
> Edited by xiaotudou on 2020/4/15

----

### 回溯

```java
public boolean isInterleave(String s1, String s2, String s3) {
        if(s1.isEmpty() && s2.isEmpty() && s3.isEmpty()){
            return true;
        }
        if(s1.length()+s2.length()!=s3.length()){
            return false;
        }
        if(s1.isEmpty()){
            return s2.equals(s3);
        }else if(s2.isEmpty()){
            return s1.equals(s3);
        }

        char c=s3.charAt(0);
        if(s1.charAt(0)==c
            && isInterleave(s1.substring(1),s2,s3.substring(1))){
            return true;
        }else if(s2.charAt(0)==c
            && isInterleave(s1,s2.substring(1),s3.substring(1))){
            return true;
        }
        return false;
    }
```



### 动态规划

```java
public boolean isInterleave(String s1, String s2, String s3) {
        if(s1.length() +s2.length() != s3.length()) return false;
        boolean[][] dp = new boolean[s1.length()+1][s2.length()+1];
        dp[0][0]=true;
        for(int i=0;i<s1.length();++i){
            dp[i+1][0]=dp[i][0]&&s1.charAt(i)==s3.charAt(i);
        }
        for(int j=0;j<s2.length();++j){
            dp[0][j+1]=dp[0][j]&&s2.charAt(j)==s3.charAt(j);
        }
        for(int i=1;i<s1.length()+1;++i){
            for(int j=1;j<s2.length()+1;++j){
                dp[i][j]=(dp[i][j-1]&&s2.charAt(j-1)==s3.charAt(i+j-1))
                ||(dp[i-1][j]&&s1.charAt(i-1)==s3.charAt(i+j-1));
            }
        }
        return dp[s1.length()][s2.length()];
    }
```

