## 不同的二叉搜索树_96

> 题目：
> 给定一个整数 n，求以 1 ... n 为节点组成的二叉搜索树有多少种？
>
> 示例 :
>
> ```txt
> 输入: 3
> 输出: 5
> 解释:
> 给定 n = 3, 一共有 5 种不同结构的二叉搜索树:
> 
> 1         3     3      2      1
> \       /     /      /  \     \
> 3     2      1      1    3      2
> /     /       \                   \
> 2     1         2                    3
> ```
>
> 题目来源： [力扣 (LeetCode) ](https://leetcode-cn.com/problems/unique-binary-search-trees)
>
> Edited by xiaotudou on 2020/3/7

----

### 动态规划

*n*个节点的二叉搜索树组合问题，经典的[卡塔兰数](https://zh.wikipedia.org/wiki/%E5%8D%A1%E5%A1%94%E5%85%B0%E6%95%B0)。 卡特兰数有两种等价的表达式，一种是级数求和的形式，如下
$$
C_0=1,C_1=1,C_n=\displaystyle \sum^{i \to n}_{i \to 1} C_{i-1}\times C_{n-i}
$$

二叉搜索树问题中，$C_0$表示加入最大值、最小值作为根节点时，组合种类等于$C_{n-1}$，可以理解为树根节点没有左子树或右子树；当*n*=1时，只有一种可能；当*n*>1时，可以选取中间任一个数 *i* 作为根节点，由于二叉搜索树的性质，比根节点小的数分布在左子树，大的数在右子树，而左右子树又是相同的子问题，将它们组合起来的可能性就是一个笛卡尔积。例如序列*(1，2，3，4，5，6)*，选取4作为根节点，左子树*(1，2，3)*有5种组合，右子树(5，6)有2种，总的组合就是2*5=10种，对应公式中的 $ C_{i-1}\times C_{n-i} $项。序列中每个节点都可以作为根节点，因此总的组合是所有节点作为根节点时的和。这个公式就是状态转移方程。

Java实现，时间复杂度$O(n^2)$， 空间复杂度 $O(n)$

```java
public int numTrees(int n) {
    if(n<1) return 0;
    else if (n<2) return 1;
    int[] dp = new int[n+1];
    dp[0]=1;
    dp[1]=1;
    // 每增加一个新节点，会更新dp
    for(int k=2;k<=n;++k){
      // 从第一个节点开始，逐个作为根节点去求解
      for(int i=1;i<=k;++i){
        dp[k] += dp[i-1]*dp[k-i];
      }
    }
    return dp[n];
}
```

### 卡塔兰数公式

卡塔兰数另外一种快速递推形式，[证明见wiki](https://en.wikipedia.org/wiki/Catalan_number) 。根据递推关系式，一次递归即可。
$$
C_1=1,C_{n+1}=\frac {2(2n+1)}{n+2} C_{n}
$$
Java实现，时间复杂度$O(n)$， 空间复杂度 $O(1)$

```java
public int numTrees(int n) {
        if(n<1) return 0;
  // n比较大时，int会越界
        long result = 1;
        for(int i=1;i<n;++i){
            result = result*2*(2*i+1)/(i+2);
        }
        return (int)result;
}
```

## 不同的二叉搜索树_95

> 题目：
> 给定一个整数 n，生成所有由 1 ... n 为节点所组成的二叉搜索树。
>
> 示例:
>
> ```txt
> 输入: 3
> 输出:
> [
> [1,null,3,2],
> [3,2,null,1],
> [3,1,null,null,2],
> [2,1,3],
> [1,null,2,null,3]
> ]
> 解释:
> 以上的输出对应以下 5 种不同结构的二叉搜索树：
> 
> 1         3     3      2      1
>  \       /     /      / \      \
>   3     2     1      1   3      2
>  /     /       \                 \
> 2     1         2                 3
> ```
> 
> 题目来源：[力扣 (LeetCode)](https://leetcode-cn.com/problems/unique-binary-search-trees-ii/)
> 

----

### 动态规划

和上一题中动态规划的想法一样，只是每个节点作为根节点时，需要将所有可能组合保存起来，而不是直接做乘法。采用递归实现，List<TreeNode> helper(int start, int  end) 返回从start到end的所有组合，对于一个根节点，需要组合所有左子树和右子树，递归终止条件就是start>end。

Java实现，时间复杂度是求解卡塔兰数，它的渐进增长约等于 (3)，n趋于无穷时，左右两边商为1，时间复杂度为 $O(\frac {4^n}{n^{3/2}})$
$$
C_n\approx \frac {4^n}{n^{3/2}\sqrt \pi}
$$
空间复杂度，Cn中可能，每种可能有n个节点，因此空间复杂度为$O(\frac {4^n}{n^{1/2}})$

```java
		private List<TreeNode> helper(int start, int end){
      List<TreeNode> trees = new ArrayList();
      //  终止条件
        if(start>end){
            trees.add(null);
            return trees;
        }
			//  从 start 到end每个节点逐个做根节点 
        for(int i = start;i<=end;++i){
          // 找到所有可能的左子树
            List<TreeNode> left = helper(start,i-1);
          // 找到所有可能的右子树
            List<TreeNode> right = helper(i+1,end);
          // 组装所有左子树和右子树
            for(TreeNode l: left){
                for(TreeNode r:right){
                    TreeNode cur = new TreeNode(i);
                    cur.left = l;
                    cur.right = r;
                    trees.add(cur);
                }
            }
        }
        return trees;
    }
    public List<TreeNode> generateTrees(int n) {
        if(n<=0){
            return new ArrayList<TreeNode>();
        }
        return helper(1,n);
    }
```

