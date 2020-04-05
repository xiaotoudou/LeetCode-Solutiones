<p> 
<a href="http://coco66.info:88">
<img src="http://coco66.info:88/leetcode/picture/home.png" alt="小土豆" style="zoom:50%;" /></a>
</p>

## 回溯

### 简介

回溯是走迷宫的一种抽象，从起点到终点之间有很多的分叉口，在迷宫中时，并不知道哪一个分叉口该如何选择。最有效率的办法就是逐个试探，要么能够到达终点，要么是死路。这时我们回退到上一个分叉口，选择另一个分叉，并记录下已经试探过的分叉口。这个问题可以用一个图模型来描述（迷宫中存在环）。

### 模板

- 终止条件，成功，或是失败
- 遍历每一个点的多个选择
  1. 选择一个分叉时，标记该分叉
  2. 试探下一个点
  3. 回退时清除标记

### 经典问题

1. DFS遍历二叉树，图

### 常用技巧

- 约束编程，有的问题是当前节点做出选择后，会生成约束条件来缩小后面选择的可能性。

----

### 题目列表 

1. [电话号码的字母组合 17](http://coco66.info:88/leetcode/backtrack/LeetCode17.html)
2. [解数独_37](http://coco66.info:88/leetcode/backtrack/LeetCode37.html)
3. [组合总和 39](http://coco66.info:88/leetcode/backtrack/LeetCode39.html)
4. [N皇后_51](http://coco66.info:88/leetcode/backtrack/LeetCode51.html)

未完成

40，46，47，78，90












