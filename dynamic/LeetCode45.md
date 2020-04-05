## 跳跃游戏ii_45

> 题目：
>
> - 给定一个非负整数数组，你最初位于数组的第一个位置。
> - 数组中的每个元素代表你在该位置可以跳跃的最大长度。
> - 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
>
> 示例 :
>
> ```txt
> 输入: [2,3,1,1,4]
> 输出: 2
> 解释: 跳到最后一个位置的最小跳跃数是 2。
>      从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
> 
> 来源：力扣（LeetCode）
> 链接：https://leetcode-cn.com/problems/jump-game-ii
> 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/jump-game-ii)
>
> Edited by xiaotudou on 2020/4/5

----

### 动态规划-自底向上

要想到达终点，从前往后找到第一个能到达终点的下标（nums[i]+i > nums.length）；这个问题就转化成一个规模更小的子问题，递归查找，直到起点。

nums全为1时，最差时间复杂度 $O(n^2)$ ，最差使用n层栈（尾递归可自动优化为迭代），空间复杂度O(n) 

```java
private int helper(int[] nums, int end){
        if(end == 0) return 0;

        int start=end;
        for(int i=0;i<end;++i){
            if(nums[i]+i >= end){
                start=i;
                break;
            }
        }
        return helper(nums,start)+1;
    }
    public int jump(int[] nums) {
        if(nums == null || nums.length<1) return 0;
        return helper(nums,nums.length-1);
    }
```

### 动态规划-自顶向下

时间复杂度为nums数组（小于nums.length的数和length）之和。nums数组值都大于nums.length时，最差时间复杂度 $O(n^2)$ ，空间复杂度 O(n)

```java
public int jump(int[] nums) {
        if(nums == null || nums.length<1) return 0;
        int[] dp = new int[nums.length];
        for(int i=1;i<dp.length;++i){ // 除0外都置位 为最大值，表示当前不可达。
            dp[i]=Integer.MAX_VALUE;
        }
  
        for(int i=0;i<nums.length;++i){
            for(int j = 1;j+i<nums.length && j<=nums[i];++j){
                if(dp[i]+1<dp[j+i]){  // 每次遍历一个点，更新之后可到达点的step
                    dp[j+i]=dp[i]+1;
                }
            }
        }
        return dp[dp.length-1];
    }
```

### 贪心

自顶向下的过程中，其实没必要更新在此之后每个点的状态，只需要记录最远能够达到的点即可。比如说起点位置为3， 那么最远可以达到4，我们只需要记录遍历到4过程中，下一步的位置（nums[i] + i）的最大值，即为下一个跳跃点。每次到达一个跳跃点时，步数+1.

时间复杂度 O(n)，空阿金复杂度 O(1)

```java
public int jump(int[] nums) {
        if(nums == null || nums.length<1) return 0;
        int maxPos=0;
        int step=0;
 				// i=0时，会自增一次；到终点这一跳不用处理，此时maxPos >= nums.length-1
        for(int i=0,end=0;i<nums.length-1;++i){ 
            maxPos = Math.max(maxPos,nums[i]+i);
            if(i == end){
                end = maxPos;
                ++step;
            }
        }
        return step;
    }
```