<p> 
<a href="http://coco66.info:88">
<img src="http://coco66.info:88/leetcode/picture/home.png" alt="小土豆" style="zoom:50%;" /></a>
</p>

## 二叉树

### 简介



### 基本款式

#### 二叉树

一个节点有左右两个节点；叶子节点的子树都为空。

特点：完全二叉树每一层有2^(h-1)个节点；满二叉树一共有2 ^h-1个节点；

#### 查找二叉树

查找二叉树的左子树都是小于当前节点，右子树都是大于当前节点。 

特点：中序遍历为升序排列，因为节点值是有序的，有BST条件的树利用这个特性可以大幅减少很多问题的时间复杂度；左子树的最右节点是当前节点的前驱；

### 算法题基础方法

树相关的问题都是建立在遍历树节点的基础上完成的，因此先夯实如何遍历树，理解不同遍历方式的特点，再结合具体场景使用。深度优先遍历（DFS）和广度优先遍历（BFS）是基础逻辑遍历方式，这里不再赘述定义。主要讨论两种遍历的不同实现方式，深度优先遍历共有三种实现方式 1. 递归实现 2. 迭代实现 3. Morris遍历；广度优先遍历 4. 队列实现。本文以分类来阐述内容，请按照先易后难(1->4->2->3)的路径学习。 

> 后序遍历和中序遍历的实现只需要调整访问节点的顺序，再次不重复实现。
>
> 回溯算法大多可以基于树来建模，动态规划的思路难想，但是很多问题可以根据待记忆表的回溯来推理。另外，图论相关算法是二叉树算法的推广。因此，二叉树的相关遍历方法可以说是后序学习的基石。树与回溯相关的题目放在回溯章节。

#### 深度优先遍历

深度优先遍历过程存在一个问题，如果此时去遍历左子树，怎么回到当前节点，因为右子树还没访问呢。

> 深度优先遍历的递归实现容易，但是需要做到清晰的理解不同遍历方式的区别，整棵树所有节点的遍历顺序，而且能够分别从纵向和横向来分析节点是如何被遍历的，可以写demo单步调试来实践。这样在应对算法题时，可以从容面对。
>

##### 1. 递归实现

###### a) 前序遍历

从局部来看，树的前序遍历遵循 “根->左->右” 的顺序访问二叉树的节点。代码实现由三部分构成：1. 终止条件 2. 处理当前节点 3. 递归处理子树。

当从整体上来看二叉树节点被遍历的顺序特点时，可以分为纵向和横向。纵向的角度来看，从根节点出发，沿着节点移动，依次访问根节点->左节点->左节点... 直到最左边的节点，最左节点的左孩子一定为空，因此可以访问该节点的右节点。在右节点上重复这个过程，一路向左，而后右转。这是一个自顶向下的过程，访问路径类似闪电⚡️，直到触达叶子节点。**递归过程中压栈的目的是因为右子树还没访问，直到所有左子树都访问之后，此时拿到右子树后就可以弹出该节点。**

```java
// 树节点数据结构
TreeNode {
	int val;
  TreeNode left;
  TreeNode right;
}

//纵向角度
void dfs(TreeNode root){
  if(root==null) return; //当前节点为空，立刻返回
  
  int cur = root.val; // 处理当前节点
  dfs(root.left); // 遍历左节点，递归压栈都卡在这一步，直到访问到该线路上的最左左子树
  dfs(root.right); // 遍历右节点
}

// 横向角度，可以得到每一层有哪些节点。
void dfs(TreeNode root,int level){
  if(root==null) return;
  
  int cur = root.val;
  dfs(root.left,level+1); // 树高加1
  dfs(root.right,level+1);
}
```

##### 2. 迭代实现

###### a) 前序遍历

前序遍历迭代写法，模仿递归访问节点时的顺序，右->左->根 入栈，出栈时既可得到前序。不同的遍历方式，只需要改入栈的顺序即可。使用Integer.MIN_VALUE 作为标志节点，表示该节点已经访问过。

```java
List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> ans = new ArrayList();
    if(root==null) return ans;

    Stack<TreeNode> stack = new Stack();
    TreeNode cur = root;
    stack.push(cur);
    while(!stack.isEmpty()){
      cur=stack.pop();
      if(cur != null){
        if(cur.right!=null) stack.push(cur.right);
        if(cur.left!=null) stack.push(cur.left);
        stack.push(cur);
        stack.push(null);  //标志该节点已经访问过
      }else{  // 访问过的节点直接出栈
        cur=stack.pop();
        ans.add(cur.val);
      }
    }
    return ans;
}
```

##### 3. 莫里斯(Morris)遍历

莫里斯遍历利用二叉树节点中空闲的指针保存“回溯节点”，因此不需要额外的栈来保存“回溯节点”，空间复杂度是O(1)，但是寻找回溯指针的过程会导致部分节点访问两次，这是一种时间换空间的策略。

从迭代实现中可知，对于前序遍历，是因为右子树还未访问，当左子树全部访问后，弹出当前节点，取出右子树的指针，而中序遍历是当前节点+右子树都未访问。因此需要缓存回到当前节点的指针！

二叉树的遍历过程有一个特点，无左子树节点的前驱节点要么为空，要么是父节点，因此对于前序遍历和中序遍历，无左子树的节点可以“立即访问”，不需缓存。如果存在左子树，按照中序 (前序) 遍历的顺序，**左子树的最右节点是左子树最后访问到节点，且这个节点的右指针一定为空**。莫里斯遍历就是利用这一特点，对存在左子树的节点先将最右节点的右指针指向自身，这样就利用右指针形成环路，可以取遍历左子树，最终会回到当前节点；此时已经访问完左子树。 

###### a) 前序遍历

```c
void morrisSearch(TreeNode root){
  if(root==null) return;
  
  TreeNode cur = root;
  while(cur!=null){
    if(cur.left != null){  
      TreeNode mostRight = cur.left;
      while(mostRight.right!=null && mostRight.right!=cur){
        mostRight=mostRight.right;
      }
      if(mostRight.right==null){  // 最右节点的右指针为空，说明左子树还未遍历过
        int v = cur.val; // 第一次遍历时访问当前节点
        mostRight.right=cur; // 将左子树最右节点指向自己
        cur=cur.left; // 移动到左子树
      }else{
        mostRight.right=null;  // 转了一圈，又回到当前节点，此时左子树已遍历，将二叉树恢复原状
        cur=cur.right;
      }
    }else{  // 左子树为空 
      int v = cur.val;
      cur=cur.right; // 访问右子树
    }
  }
}
```

###### b) 中序遍历

```c
void morrisSearch(TreeNode root){
  if(root==null) return;
  
  TreeNode cur = root;
  while(cur!=null){
    if(cur.left != null){  
      TreeNode mostRight = cur.left;
      while(mostRight.right!=null && mostRight.right!=cur){
        mostRight=mostRight.right;
      }
      if(mostRight.right==null){  // 最右节点的右指针为空，说明左子树还未遍历过
        mostRight.right=cur; // 将左子树最右节点指向自己
        cur=cur.left; // 移动到左子树
      }else{
        int v = cur.val; // 第二次遍历时再访问当前节点
        mostRight.right=null;  // 转了一圈，又回到当前节点，此时左子树已遍历，将二叉树恢复原状
        cur=cur.right;
      }
    }else{  // 左子树为空 
      int v = cur.val;
      cur=cur.right; // 访问右子树
    }
  }
}
```



#### 广度优先遍历

使用队列保存下一行的对象

----

### 题目列表 

基础题

1. 前序遍历 --- 三种实现
2. 中序遍历 --- 三种实现
3. 后序遍历 --- 两种实现
4. 按层访问二叉树 --- DFS 和 BFS

综合题

1. [最大二叉树](https://leetcode-cn.com/problems/maximum-binary-tree/) 
2. 

