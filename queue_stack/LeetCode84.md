## 柱状图中最大的矩形_84

> 题目：
> 给定 *n* 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
>
> 示例 :
>
> ```txt
> 输入: [2,1,5,6,2,3]
> 输出: 10
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/largest-rectangle-in-histogram/)
>
> Edited by xiaotudou on 2020/4/12

----

### 暴力法

二维数组模型穷举所有子矩形，子矩形的高度由最低的矩形决定，$area = (i-j+1)*minHeight$ 。

时间复杂度 $O(n^2)$ ，空间复杂度 O(1)。

```java
public int largestRectangleArea(int[] heights) {
        if(heights == null||heights.length<1) return 0;
        int maxRes=0;
        for(int i=0;i<heights.length;++i){
            int minCol=Integer.MAX_VALUE;
            for(int j=i;j<heights.length;++j){
                if(heights[j]<minCol){
                    minCol=heights[j];
                }
                int s=(j-i+1)*minCol;
                if(s>maxRes){
                    maxRes=s;
                }
            }
        }
        return maxRes;
    }
```

### 分治

矩形串中的最低矩形决定了整个矩形串的高度，最低矩形可以将矩形串分成左右两部分，形成两个相同的子问题。

合并时，当前矩形串的最大面积 取自左侧或右侧的值，以及包含中间最低矩形时的面积。

时间复杂度，平均 $O(nlogn)$ ，每次找最低矩形耗费 O(n)；最差情况是有序矩形，一次递归只能减少一个矩形，退化成  $O(n^2)$。空间复杂度 O(1)。

```java
public int helper(int[] heights,int left,int right){
        if(left>right) return 0;
        
        int index=left;
        for(int i=left;i<=right;++i){
            if(heights[i]<heights[index]){
                index=i;
            }
        }
        return Math.max((right-left+1)*heights[index], 
            Math.max(helper(heights,left,index-1),helper(heights,index+1,right)));
    }
    public int largestRectangleArea(int[] heights) {
        if(heights == null||heights.length<1) return 0;
        return helper(heights,0,heights.length-1);
    }
```

### 单调栈

"极大值"矩形形成的子串要么是只包含自身，否则它的面积受制于左右两侧较低矩形的高度。一种极端情况就是单调递增的矩形串，那么应该从最高的矩形开始考虑最大面积，然后取左侧的一个矩形，此时最高矩形会被截取一段，以此类推，直到第一个最低矩形，再次过程中记录可能出现的最大值。

推广一下，其实最高的矩形出现矩形串中间也可以，只用考虑它自身作为子串时的面积，被右侧较低矩形截取时，不必考虑它的高度。维护一个栈，如果是矩形面积单调递增就入栈，出现了低于栈顶的矩形，就将栈中的矩形弹出栈，直到栈顶矩形低于当前矩形。因为这些高个的面积会被相邻的矮个矩形截断，它的高度不会再影响之后矩形的解。

```java
public int largestRectangleArea(int[] heights) {
        if(heights == null||heights.length<1) return 0;
        // 新的矩形开头是0，使得所有非负矩形都会入栈；结尾也是0，将栈中矩形全部弹出
  			// 这样不需要考虑开头结尾的逻辑，统一处理。
        int[] newHeights = new int[heights.length+2];
        for(int i=0;i<heights.length;++i) 
          	newHeights[i+1]=heights[i];
        Deque<Integer> stock = new ArrayDeque();
        int res=0;
        for(int i=0;i<newHeights.length;++i){
           // 栈顶矩形大于当前矩形就出栈，直到栈中矩形满足单调递增
            while(!stock.isEmpty() && newHeights[i]<newHeights[stock.peek()]){
                int cur = stock.pop(); // 弹出最高个
                res = Math.max(res,(i-stock.peek()-1)*newHeights[cur]); //此时栈顶是最高个左侧矩形
            }
            stock.push(i);
        }
        return res;
    }
```

这一题与[接雨水](http://coco66.info:88/leetcode/array/LeetCode42.html) 很相似，却又有不同之处。接雨水中，一个矩形的蓄水量由两侧最高的矩形决定，且确定之后不会变化。本题中一个矩形面积是由两侧较低的矩形或是自身高度决定，而且截断面积会随着新遍历到的最低矩形而改变，因此双指针法在这里不再适用。