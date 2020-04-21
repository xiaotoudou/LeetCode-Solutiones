## 最大矩形_85

> 题目：
> 给定一个仅包含 0 和 1 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
>
> 示例 :
>
> ```txt
> 输入:
> [
>   ["1","0","1","0","0"],
>   ["1","0","1","1","1"],
>   ["1","1","1","1","1"],
>   ["1","0","0","1","0"]
> ]
> 输出: 6
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/maximal-rectangle/submissions/)
>
> Edited by xiaotudou on 2020/4/13

----

### 动态规划

先理解题，全“1”才算一个矩形。

根据对角两个坐标可以穷举所有矩形，先缓存每个点从左到右最长的宽度，以这个点作为矩形右下角，向上一行寻找宽不为0的点，宽度会被最窄的边限制。

时间复杂度， 穷举所有子矩形 O(mn) ,每个点去检查最大矩形O(n) ，总的时间复杂度 $O(mn^2)$ 。空间复杂度 $O(n^2)$ .

````java
public int maximalRectangle(char[][] matrix) {
        if(matrix.length<1) return 0;
        // 初始化一个而未举证，存储每个点可能的宽
        int[][] dp = new int[matrix.length][matrix[0].length+1];
        for(int i=0;i<matrix.length;++i){
            for(int j=0;j<matrix[0].length;++j){
                dp[i][j+1]= matrix[i][j] == '1'?dp[i][j]+1:0;
            }
        }
        int res=0;
        for(int i=0;i<matrix.length;++i){
            for(int j=0;j<matrix[0].length;++j){
                if(dp[i][j+1]>0){
                    int minWidth=Integer.MAX_VALUE;
                    for(int h=i;h>=0 && dp[h][j+1]>0;--h){
                        if(dp[h][j+1]<minWidth){ // 子矩形的宽度被最窄的宽限制
                            minWidth=dp[h][j+1];
                        }
                        int area=minWidth*(i-h+1);  // 向上遍历每一个子矩形都要计算面积
                        res =res>area?res:area;
                    }
                }
            }
        }
        return res;
    }
````

### 单调栈

以每一行作为基准线，全1的列构成一个柱子，要求当前状态下的最大矩形面积，就是84题（柱状图中的最大矩形）问题。

因此每一列的数累加到一行中，遍历所有行，即可找到最大矩形。

````java
private int helper(int[] heights){
        int[] newHeights = new int[heights.length+2];
        for(int i=0;i<heights.length;++i) newHeights[i+1]=heights[i];
        Deque<Integer> stock = new ArrayDeque();
        int res=0;
        for(int i=0;i<newHeights.length;++i){
            while(!stock.isEmpty() && newHeights[i]<newHeights[stock.peek()]){
                int cur = stock.pop();
                res = Math.max(res,(i-stock.peek()-1)*newHeights[cur]);
            }
            stock.push(i);
        }
        return res;
    }
    
    public int maximalRectangle(char[][] matrix) {
        if(matrix.length<1) return 0;
        int[] dp = new int[matrix[0].length];
        int res=0;
        for(int i=0;i<matrix.length;++i){
            for(int j=0;j<matrix[0].length;++j){
                dp[j]=matrix[i][j]=='1'?dp[j]+1:0;
            }
            res=Math.max(res,helper(dp));
        }
        return res;
    }
````