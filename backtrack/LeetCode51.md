## N皇后_51

> 题目：
> *n* 皇后问题研究的是如何将 *n* 个皇后放置在 *n*×*n* 的棋盘上，并且使皇后彼此之间不能相互攻击。
>
> 皇后走法与吃法都是横直斜，一个皇后可以占据一行、一列和两条对角线
>
> 示例 :
>
> ```txt
> 输入: 4
> 输出: [
>  [".Q..",  // 解法 1
>   "...Q",
>   "Q...",
>   "..Q."],
> 
>  ["..Q.",  // 解法 2
>   "Q...",
>   "...Q",
>   ".Q.."]
> ]
> 解释: 4 皇后问题存在两个不同的解法。
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/n-queens)
>
> Edited by xiaotudou on 2020/4/5

----

### 回溯

每一行可以放一个皇后，但是放在哪一列需要试探。如果放到最后不能放进去，说明当前解法不对，回溯到上一个皇后，试探下一个位置。如果能够正确放完所有皇后，说明这是一种解法，保存起来。

和[解数独](http://coco66.info:88/leetcode/backtrack/LeetCode37.html)的问题很相似，都是带约束条件的组合问题，约束条件大幅减少需要试探的可能。

第一个皇后的选择有n种，下一个皇后的选择是n-1种，因此所有可能性是n! 种，时间复杂度 $O(n!)$ , 空间复杂度$O(n^2)$ 

```java
		private int[] rows; // 标记放置皇后的行
    private int[] cols; // 标记放置皇后的列
    private int[] hills; // 主对角线上的row-col为常数 从-（n-1）~（n-1）
    private int[] dales; // 副对角线上的row+col为常数，从0到n-1
    private boolean[][] queue; // queue放置的位置，使用标记数组，可以常数时间判断当前位置是否可以放置
    private int n;
    private List<List<String>> res;

    private boolean isNotAttack(int row, int col){
        int flag = rows[row] + cols[col] + hills[row-col + n]+dales[row+col];
        return flag==0;
    }

	// 将相应标记数组置位
    private void place(int row,int col){
        rows[row]=1;
        cols[col]=1;
        hills[row-col+n]=1; // row - col为负数的对角线做一个n的偏移
        dales[row+col]=1;
        queue[row][col]=true;
    }

    private void remove(int row,int col){
        rows[row]=0;
        cols[col]=0;
        hills[row-col+n]=0;
        dales[row+col]=0;
        queue[row][col]=false;
    }

    private List<String> print(){
        List<String> strs=new ArrayList();
        for(int i=0;i<n;++i){
            StringBuffer sb = new StringBuffer();
            for(int j=0;j<n;++j){
                sb.append(queue[i][j]?'Q':'.');
            }
            strs.add(sb.toString());
        }
        return strs;
    }

// 以行为单位放皇后
    private void backtrack(int row){
        // 行数已超过n, 说明此时Queue表示的就是一个正确解，对此时的棋盘状态”截图“
        if(row >= n) {
            res.add(print()); 
            return;
        }
				// 对当前行的每一列试探，如果可以放置就放，然后试探下一行，回溯时清空状态
        for(int i=0; i<n; ++i){
            if(isNotAttack(row,i)){
                place(row,i);
                backtrack(row+1);
                remove(row,i);
            }
        }
    }

    public List<List<String>> solveNQueens(int n) {
        res = new ArrayList();
        if(n<1) return res;
        this.n=n;

        rows=new int[n];
        cols=new int[n];
        hills=new int[2*n];
        dales=new int[2*n];
        queue=new boolean[n][n];
        backtrack(0);  // 从第一行开始放置
        return res;
    }
```