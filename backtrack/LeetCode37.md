## 解数独_37

> 题目：
> 编写一个程序，通过已填充的空格来解决数独问题。
>
> 一个数独的解法需遵循如下规则：
>
> 数字 1-9 在每一行只能出现一次。
> 数字 1-9 在每一列只能出现一次。
> 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
> 空白格用 '.' 表示。
>
> 说明 :
>
> ```txt
> 给定的数独序列只包含数字 1-9 和字符 '.' 。
> 你可以假设给定的数独只有唯一解。
> 给定数独永远是 9x9 形式的。
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/sudoku-solver)
>
> Edited by xiaotudou on 2020/3/23

----

### 暴力

9*9网格可以展开为一个长度为81的数组，其中有一些位置已经填充了数字。现在需要填充剩下的数字，同时满足三个条件，这个数字才能填入。从前往后填入的过程中，可能会遇到某个位置没数可满足条件，说明上一个数填的不对，回撤试别的数。

小技巧

- 9*9网格展开成一位数组，减少很多边界判断

```java
// 检查当前位置填入的数是否满足三个条件
private boolean isSucc(char[][] board, int row,int col, char c){
    for(int i=0; i<9; ++i){
      if((i!=col && board[row][i] == c)
         ||(i!=row && board[i][col] == c)){
        return false;
      }
    }
    for(int i = (row/3)*3, endRow=i+3; i<endRow; ++i){
      if(i == row) continue;
      for(int j=(col/3)*3, endCol=j+3;j<endCol; ++j){
        if(j==col) continue;
        if(board[i][j] == c){
          return false;
        }
      }
    }
    return true;
}

private boolean backtrack(char[][] board, int row, int col){
    if(row >= 9){
      return true; // 所有数字都成功填入即成功。
    }else if(col>=9){  // 当前行遍历完，就转到下一行
      return backtrack(board,row+1,0);  
    }else if(board[row][col]!='.'){  // 已经有数字就跳过
      return backtrack(board,row,col+1); 
    }
	// 试探1~9， 可以填入就走到下一步，如果后面走不通，回溯试探下一个数
    for(int i=1; i<=9; ++i){
      char c = (char)(i + '0');
      if(isSucc(board, row, col, c)){
        board[row][col]=c;
        if(backtrack(board, row, col+1)){
          return true;
        }
      }
    }
		// 当前节点没有数字满足时，要置位'.'
    board[row][col]='.'; 
    return false;
}

public void solveSudoku(char[][] board) {
  	backtrack(board,0,0);
}
```

### 位集

采用位集优化isSucc方法，快速得到一个节点能够填的数字，bitset 或操作的实现是逐位进行或操作，没有本质上减少回溯的计算量。

```java
    private BitSet[] bsRow = new BitSet[9];
    private BitSet[] bsCol = new BitSet[9];
    private BitSet[] bsBox = new BitSet[9];

    private void setC(char[][] board, int row, int col){
        int d = board[row][col] - '0';
        int box = (row/3)*3 + col/3;
        bsRow[row].set(d);
        bsCol[col].set(d);
        bsBox[box].set(d);
    }

    private boolean backtrack(char[][] board, int row, int col){
        if(row >= 9){
            return true;
        }else if(col>=9){
            return backtrack(board,row+1,0); // 将9*9的网格转化成长度为81的数组处理
        }else if(board[row][col]!='.'){
            return backtrack(board,row,col+1);
        }
        
        // 位集求并集，找到当前位置还可以填入的数字
        BitSet remain = new BitSet(10);
        remain.or(bsRow[row]);
        remain.or(bsCol[col]);
        int box = (row/3)*3 + col/3;
        remain.or(bsBox[box]); 

        int start=0;
        while((start = remain.nextClearBit(start+1)) < 10){
            char c = (char)(start + '0');
            board[row][col] = c;
            setC(board,row,col);

            if(backtrack(board, row, col+1)){
                return true;
            }
            // 当前数字不满足时，回退
            board[row][col]='.'; 
            bsRow[row].clear(start);
            bsCol[col].clear(start);
            bsBox[box].clear(start);
        }
        return false;
    }

    public void solveSudoku(char[][] board) {
        for(int i=0;i<9;++i){
            bsRow[i] = new BitSet(10);
            bsCol[i] = new BitSet(10);
            bsBox[i] = new BitSet(10);
        }
        for(int i=0;i<9;++i){
            for(int j=0;j<9;++j){
                if(board[i][j] != '.'){ // 初始化起始位
                    setC(board,i,j);
                }
            }
        }
        backtrack(board,0,0);
    }
```

### 剪枝

上面的方法可以不重复、不遗漏的遍历所有网格，枚举出所有可能性，但是效率不够。我们填数独的时候，不会从头开始填，而是**先确定可能性最少的网格，能够确定的网格越多，剩余网格的可能性会更快收敛**。

