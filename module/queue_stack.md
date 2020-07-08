<p> 
<a href="http://coco66.info:88">
<img src="http://coco66.info:88/leetcode/picture/home.png" alt="小土豆" style="zoom:50%;" /></a>
</p>

## 栈和队列

### 简介

> 数组元素 arr [1, 2, 3, 4], 顺序访问数组压栈 和 进队，栈和队列中的状态
>
> 栈：(栈顶) 4 -> 3 -> 2 -> 1 (栈底)
>
> 队列：(队头) 1 -> 2 -> 3 -> 4 (队尾)
>
> 栈是从栈顶压栈，也是从栈顶出栈；队列是从队尾入队，队头出队。

### 基本款式

#### 栈

栈的实现方式有两种，一是数组实现，但是数组的容量有限；二是更常见的实现方式，链表使用**头插法**实现。

栈的方法主要有三个，如下。方法名与栈是绑定的，约定俗成的规则，至于方法怎么实现可以自己决定。

1. push() 压栈
2. pop() 出栈
3. peek()  返回栈顶元素，不出栈
4. size() 栈大小

#### 队列

队列的实现方式有两种，一是数组实现，**环形数组**实现队列是很经典的方法；二是链表使用**尾插法**实现。

队列的方法名同样是约定俗成的。

1. offer() 入队
2. poll() 出队
3. peek() 返回队头元素，不出队
4. size() 队列大小

### 算法题基础方法

1. 括号配对问题 --- 栈的典型应用

   

2. 单调栈 

   数组中从左向右遍历，从一个元素右侧找到第一个大于当前元素的值。暴力解法就是遇到每一个元素后往后遍历，直到找到一个大于它的元素，或是到末尾。暴力法时间复杂度 O(n2)。单调栈的原理是维护一个单调递减的栈

3. 栈 --- 深度优先遍历

4. 队列 --- 广度优先遍历

----

### 题目列表 

1. 栈实现队列  & 队列实现栈

2. [有效的括号](https://leetcode-cn.com/problems/valid-parentheses/)

3. [下一个更大元素 I](https://leetcode-cn.com/problems/next-greater-element-i/)

4. [每日温度](https://leetcode-cn.com/problems/daily-temperatures/)

5. [最长有效括号 32*](http://coco66.info:88/leetcode/dynamic/LeetCode32.html)

6. [接雨水42*](http://coco66.info:88/leetcode/array/LeetCode42.html)

7. [柱状图中最大矩形面积_84*](http://coco66.info:88/leetcode/queue_stack/LeetCode84.html)

8. [最大矩形_85*](http://coco66.info:88/leetcode/queue_stack/LeetCode85.html)

   

