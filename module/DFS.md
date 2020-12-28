<font face="楷体">
<font size=4>

# DFS
DFS和BFS 不同一点在于DFS使用的是栈。
[117](https://leetcode-cn.com/problems/populating-next-right-pointers-in-each-node-ii/)
```c++
 
class Solution {
public:
    void connect(TreeLinkNode *root) {
        if(root==NULL)
            return;
        TreeLinkNode* node = root->next;
        while(node)
        {
            if(node->left)
            {
                node=node->left;
                break;
            }
            if(node->right)
            {
                node=node->right;
                break;
            }
            node = node->next;
        }
        if(root->left)root->left->next = root->right?root->right:node;
        if(root->right)root->right->next = node;
        connect(root->right);
        connect(root->left);
    }
};

```
[124](https://leetcode-cn.com/problems/binary-tree-maximum-path-sum/) 二叉树的最大路径和
```python

class Solution:
    def maxPathSum(self, root: TreeNode) -> int:
        def maxs(root):
            nonlocal max_sum
            if root is None:
                return 0
            leftsum = max(maxs(root.left),0)
            rightsum = max(maxs(root.right),0)
            ps = root.val+leftsum+rightsum
            max_sum = max(max_sum,ps)
            return root.val+max(leftsum,rightsum) # 选取从当前节点出发，左边路径和右边路径中的最大值
        max_sum=float('-inf')
        maxs(root)
        return max_sum
```
[329](https://leetcode-cn.com/tag/depth-first-search/),在普通的DFS中引入记忆化
[576] 也是记忆化递归，不过事实带有约束的记忆化递归，通常可以使用DP进行求解

```python
class Solution:
    def longestIncreasingPath(self, matrix: List[List[int]]) -> int:
        direct = [(0,1),(0,-1),(1,0),(-1,0)]
        def dfs(matrix,memory,i,j,m,n):
            if memory[i][j]!=0:
                return memory[i][j]
            for d in direct:
                x = i + d[0]
                y = j + d[1]
                if 0<=x<m and 0<=y<n and matrix[x][y]>matrix[i][j]:
                    memory[i][j] = max(memory[i][j],dfs(matrix,memory,x,y,m,n))  # 记忆化搜索

            memory[i][j]+=1
            return memory[i][j]
        
        if matrix is None or len(matrix)==0:
            return 0
        m = len(matrix)
        n = len(matrix[0])
        memory = [[0 for _ in range(n)] for _ in range(m)]
        res = 0
        for i in range(m):
            for j in range(n):
                res = max(res,dfs(matrix,memory,i,j,m,n))
        return res
```

[332](https://leetcode-cn.com/problems/reconstruct-itinerary/solution/javadfsjie-fa-by-pwrliang/),求图中欧拉路径.严谨地说，一个连通有向图 G 有欧拉路径，指存在一个顶点，从它出发，沿着有向边的方向，可以不重复地遍历图中所有的边。
[753](https://leetcode-cn.com/problems/cracking-the-safe/),求解欧拉回路

```python

class Solution:
    def findItinerary(self, tickets: List[List[str]]) -> List[str]:
        from collections import defaultdict
        res = []
        graph = defaultdict(list)
        for start,end in sorted(tickets)[::-1]:
            graph[start].append(end)
        def dfs(g):
            while graph[g]:
                dfs(graph[g].pop())
            res.append(g)
        dfs('JFK')
        return res[::-1]
```

[394](https://leetcode-cn.com/problems/decode-string/),字符串解码。
```c++
class Solution
{
public:
	string decodeString(string s) {
		if (s.empty()) return "";
		int k = 0;
		return dfs(s, k);
	}
	string dfs(string s, int &k)
	{
        int cnt=0;
        string res;
        while(k<s.size())
        {
		if(isdigit(s[k])) cnt=cnt*10+(s[k++]-'0');
        else if(s[k]=='[')
        {
            string temp = dfs(s,++k);
            for(int i=0;i<cnt;++i)
                res+=temp;
            cnt=0;
        }
        else if(s[k]==']')
        {
            k++;
            return res;
        }
        
        else res+=s[k++];
        }
        return res;
	}
};

```

[473](https://leetcode-cn.com/problems/matchsticks-to-square/),火柴品正方形，使用惠苏苏算法求解
```c++
class Solution {
public:
    bool makesquare(vector<int>& nums) {
        
        if(nums.empty()||nums.size()<4) return false;
        int sum = accumulate(nums.begin(),nums.end(),0);
        if(sum%4!=0) return false;
        int target = sum/4;
        vector<int>sums(4,0);
        sort(nums.rbegin(),nums.rend());
        return helper(nums,sums,0,target);
    }
    bool helper(vector<int>&nums,vector<int>&sums,int pos,int target)
    {
        if(pos>=nums.size())
            return sums[0]==target&&sums[1]==target&&sums[2]==target;
        for(int i=0;i<4;++i)
        {
            if(sums[i]+nums[pos]>target) continue;
            sums[i]+=nums[pos];
            if(helper(nums,sums,pos+1,target)) return true;
            sums[i]-=nums[pos];
        }
        return false;
    }
};
```
[491],[473]和回溯算法中的排列组合问题是一样的，两者可以一起研究。

[546]和[664] DFS和DP结合
[743] 单源最短路径的求解

</font>
</font>