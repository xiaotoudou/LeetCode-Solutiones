<p> 
<a href="http://coco66.info:88">
<img src="http://coco66.info:88/leetcode/picture/home.png" alt="小土豆" style="zoom:50%;" /></a>
</p>

## 栈和队列

### 简介

> 数组元素 arr [1, 2, 3, 4], 顺序访问数组压栈 和 进队，栈和队列中的状态分别如下：
>
> 栈是从栈顶压栈，也是从栈顶出栈
>
> 栈：(栈顶) 4 -> 3 -> 2 -> 1 (栈底)
>
> 队列是从队尾入队，队头出队
>
> 队列：(队头) 1 -> 2 -> 3 -> 4 (队尾)
>
> 
>
> 2020/07/10 For LinJie

### 基本款式

#### 栈

栈的实现方式有两种，一是数组实现，但是数组的容量有限；二是更常见的实现方式，链表使用**头插法**实现。

栈的关键方法如下：

1. push() 压栈
2. pop() 出栈
3. peek()  返回栈顶元素，不出栈
4. size() 栈大小

#### 队列

队列的实现方式有两种，一是数组实现，**环形数组**实现队列是很经典的方法；二是链表使用**尾插法**实现。

栈的关键方法如下：

1. offer() 入队
2. poll() 出队
3. peek() 返回队头元素，不出队
4. size() 队列大小

> 栈和队列既可以用数组实现，也可以用链表实现。动手分别实现上面的四个关键API方法~ 时间复杂度都是 O(1）

### 算法题基础方法

### 栈

栈是一种先进后出的数据结构，这种特性适合用来缓存 “当前还不能决定”的状态，等待访问到后面的元素时，可以决定栈中元素时将它出栈。栈在树的深度优先遍历中出现频繁，但是栈本身的题目不多。总结就是两类：

1. 括号配对问题 

   左括号遇到右括号之前先入栈，遇到一个右括号就弹出栈中一个左括号。要满足两点条件才能保证括号有效，一是左括号与右括号的个数一致，也就是最终栈中没有剩余左括号；二是左括号出现的顺序一定在右括号之前，当出现右括号时，此时栈中一定还有左括号。

2. 单调栈 

   数组中从左向右遍历，从一个元素右侧找到第一个大于当前元素的值。暴力解法就是遇到每一个元素后往后遍历，直到找到一个大于它的元素，或是到末尾。暴力法时间复杂度 O(n2)。

   单调栈的原理是维护一个元素值单调递减的栈，此时栈顶元素最小，新访问的元素如果比栈顶对象大，那么栈顶右侧第一个大于它的值就找到了，可以出栈输出，直到“新元素”入栈时满足栈单调递减 (可能出栈至栈空) 。

   当所有元素访问完之后，栈中剩余的元素在右侧是找不到更大的值。

   ps: 这个技巧第一次遇到有点难想，先知道有这个方法，可以解决什么问题即可。可以看看题目列表第4题

   ```c
   void monotnonicStack(int[] arr){
     Stack stack;
     for(int i=0;i<arr.length;++i){
       while(!stack.isEmpty() && arr[i]>arr[stack.peek()]){
         int cur = stack.pop();
         // arr[cur] 的下一个更大值是 arr[i]
       }
       // 此时栈为空 或是 栈顶元素大于arr[i]
       stack.push(i); 
     }
   }
   ```

----

### 题目列表 

1. 栈实现队列 ~
2. 队列实现栈 ~
3. [有效的括号](https://leetcode-cn.com/problems/valid-parentheses/)
4. [下一个更大元素 I](https://leetcode-cn.com/problems/next-greater-element-i/)
5. [每日温度](https://leetcode-cn.com/problems/daily-temperatures/)
6. [最长有效括号 32*](http://coco66.info:88/leetcode/dynamic/LeetCode32.html)
7. [接雨水42*](http://coco66.info:88/leetcode/array/LeetCode42.html)
8. [柱状图中最大矩形面积_84*](http://coco66.info:88/leetcode/queue_stack/LeetCode84.html)
9. [最大矩形_85*](http://coco66.info:88/leetcode/queue_stack/LeetCode85.html)
