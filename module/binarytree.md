<p> 
<a href="http://coco66.info:88">
<img src="http://coco66.info:88/leetcode/picture/home.png" alt="小土豆" style="zoom:50%;" /></a>
</p>

## 二叉树

### 简介



### 经典问题

1. 深度优先遍历

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

   

2. 广度优先遍历

   使用队列保存下一行的对象

----

### 题目列表 

[恢复二叉搜索树_99](http://coco66.info:88/leetcode/binarytree/LeetCode99.html)

