## 恢复二叉搜索树_99

> 题目：
>
> 二叉搜索树中的两个节点被错误地交换。
>
> 请在不改变其结构的情况下，恢复这棵树。
>
> 尝试使用常数空间解答该题。
>
> 示例 :
>
> ```txt
> 输入: [1,3,null,null,2]
> 
>    1
>   /
>  3
>   \
>    2
> 
> 输出: [3,1,null,null,2]
> 
>    3
>   /
>  1
>   \
>    2
> 
> 示例二
> 输入: [3,1,4,null,null,2]
> 
> 3
> / \
> 1   4
>    /
>   2
> 
> 输出: [2,1,4,null,null,3]
> 
> 2
> / \
> 1   4
>    /
>  3
> 
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/recover-binary-search-tree/)
>
> Edited by xiaotudou on 2020/4/20

----

### 迭代

该问题可以转化成简单的子问题看待：

1. 二叉查找树的中序遍历就是升序数组，这里采用栈做缓存实现遍历。
2. 有序数组中有两个数位置错乱，恢复成有序数组。两个数位置错乱会导致数组从前往后会出现两次$arr[i]>arr[i+1]$,  第一次arr[i]是较大的数，第二次arr[i+1] 是较小的数，找到两个数的下标后交换即可。

时间复杂度 O(n)，空间复杂度 O(n) 

```java
public void recoverTree(TreeNode root) {
        if(root == null) return;
        TreeNode cur = root;
        TreeNode first=null;
        TreeNode second=null;
        TreeNode pre=new TreeNode(Integer.MIN_VALUE);
        Deque<TreeNode> stack = new ArrayDeque();
        while(cur!=null || !stack.isEmpty()){
            while(cur!=null){  // 当前节点不为空就入栈，找到最左节点后出循环
                stack.push(cur);
                cur=cur.left;
            }
            cur =stack.pop();
            if(first==null && pre.val>cur.val){ // 找到第一个数
                first=pre;
            }
            if(first!=null && pre.val>cur.val){ // 找到第二个数
                second=cur;
            }
            pre=cur;
            cur=cur.right;
        }
        int tmp = first.val;
        first.val=second.val;
        second.val=tmp;
    }
```

### Morri遍历

中序遍历的一个问题是当前节点的前驱节点访问后，没法回到当前节点，所以需要借助栈来缓存当前节点。二叉查找树有一个性质：

	1. 如果当前节点没有左子树，那么就不存在前驱结点
 	2. 如果当前节点存在左子树，那么它的前驱结点一定在左子树里面。进入到左子树后，找到右子树为空的节点，即为当前节点的前驱结点。证明：左子树里面的最右节点是左子树的最大值，仅小于当前节点。
 	3. 将当前节点的前驱节点指向当前节点，这样就可以去遍历左子树，不用担心回不到当前节点。
 	4. 进入左子树之后就是相同的子问题。

因此每个节点会访问两次，第一次是连接好向后的指针；第二次是进行比较。

时间复杂度 O(n)， 空间复杂度 O(1)

```java
public void recoverTree(TreeNode root) {
        if(root == null) return;
        TreeNode cur = root;
        TreeNode first=null;
        TreeNode second=null;
        TreeNode pre=new TreeNode(Integer.MIN_VALUE);
        
        while(cur!=null){
            if(cur.left != null){
                TreeNode precessor = cur.left;
              // 限定右指针不能指向当前节点，否则会成环
                while(precessor.right!=null && precessor.right!=cur){ 
                    precessor=precessor.right;
                }

                if(precessor.right==cur){ // 第二遍访问，进行比较
                    if(first==null && pre.val>cur.val) first=pre;
                    if(first!=null && pre.val>cur.val) second=cur;
                    precessor.right=null; // 恢复树的状态
                    pre=cur;  
                    cur=cur.right; // 左子树已经访问完，访问右子树
                }else{ // 设置好当前节点的前驱结点
                    precessor.right=cur;
                    cur=cur.left;
                }
            }else{ // 不存在左子树，没有前驱结点，直接判断
                if(first==null && pre.val>cur.val) first=pre;
                if(first!=null && pre.val>cur.val) second=cur;
                pre=cur;
                cur=cur.right;
            }
        }
        int tmp = first.val;
        first.val=second.val;
        second.val=tmp;
    }
```

