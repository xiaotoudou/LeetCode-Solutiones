## 接雨水_42

> 题目：
> 给定 *n* 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
>
> 示例 :
>
> ```txt
> 输入: [0,1,0,2,1,0,1,3,2,1,2,1]
> 输出: 6
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/trapping-rain-water/)
>
> Edited by xiaotudou on 2020/3/31

----

### 暴力

遍历height数组，会出现两种情况：

1. 新加入的柱子高于左侧最高的柱子，那么左侧最高柱子到当前柱子之间的水高度为height[max_height_before]
2. 新加入的柱子低于左侧最高的柱子，那么会影响左侧第一个高于它的柱子至当前柱子的水高，更新为height[i]

java 实现，最坏情况时间复杂度$O(n^2)$ ，空间复杂度 O(n)

```java
public int trap(int[] height) {
        if(height==null || height.length<2) return 0;

        int[] waterHeight = new int[height.length];
        int max_height_index=0;
        for(int i=0;i<height.length;++i){
            if(height[i]>0){
                max_height_index=i;
                break;
            }
        }

        for(int i=max_height_index+1;i<height.length;++i){
            if(height[i] == 0) continue;
            if(height[i]>=height[max_height_index]){
                int j = max_height_index;
                while(j<i) waterHeight[j++]=height[max_height_index];
                max_height_index=i;
            }
            else{
                int j = i-1;
                while(j>=0 && height[j]<height[i]) waterHeight[j--]=height[i];
            }
        }
        int count =0;
        for(int i=0;i<height.length;++i){
            int w = waterHeight[i]-height[i];
            if(w>0) count+=w;
        }
        return count;
    }
```

### 中心扩展法

遍历柱子，柱子 i 的可能蓄水量, 每一个柱子都需要分别向左和向右遍历，找到两侧的最高柱子。需水量为 min( height[left_max], height[right_max] ) - height[i]。时间复杂度 $O(n^2)$ , 空间复杂度为 O(n)。

### 动态规划

中心扩展法中每个柱子都要向两侧遍历。可以先从两侧遍历所有柱子，记录下截止到柱子 i ，最高的柱子高度。第三次遍历柱子时，可直接得到当前柱子的需水量 min(left_max[i], right_min[i]) - height[i]。

Java实现，时间复杂度 O(n), 空间复杂度 O(n)

```java
public int trap(int[] height) {
        if(height==null || height.length<2) return 0;

        int[] left_max = new int[height.length];
        for(int i=0,max_index=0;i<height.length;++i){
            if(height[i]>=height[max_index]){
                left_max[i] = height[i];
                max_index=i;
            }
            else{
                left_max[i]=height[max_index];
            }
        }

        int[] right_max = new int[height.length];
        for(int i=height.length-1,max_index=i;i>=0;--i){
            if(height[i]>=height[max_index]){
                right_max[i] = height[i];
                max_index=i;
            }
            else{
                right_max[i]=height[max_index];
            }
        }
        int count = 0;
        for(int i=0;i<height.length;++i){
            int w = Math.min(left_max[i],right_max[i])-height[i];
            if(w>0) count+=w;
        }
        return count;
    }
```

### 单调栈

维护一个单调递减栈，后进栈矩形如果比栈顶的矩形高，那么这个矩形的积水面积被当前矩形和栈顶第二个矩形限定，可以将它出栈，并累积积水量。注意，此时这个矩形上方的积水量并没有累积完，当左侧矩形仍然小于当前矩形时，可以继续出栈，直到栈空。

左侧是一个单调递减栈，当右侧出现较高的矩形时，可以逐层计算积水量，直到达到新访问的矩形，或是当前矩形成为左侧最高矩形（到达栈底）。

每个矩形会访问两次，时间复杂度 O(n), 空间复杂度 O(n)

```java
public int trap(int[] height) {
        if(height == null) return 0;
        int res =0;
        Deque<Integer> stack = new ArrayDeque();
        for(int i=0;i<height.length;++i){
           while(!stack.isEmpty() && height[i]>height[stack.peek()]){
               int cur = stack.pop();
               if(stack.isEmpty()) break;
             // 积水量由两块矩形之间距离与较矮"限定矩形"的高度决定
               int h = Math.min(height[i], height[stack.peek()]);
               res+=(i-stack.peek()-1)*(h-height[cur]); 
           }
           stack.push(i);
        }
        return res;
    }
```

### 双指针

暴力2 中需要找当前柱子的左侧和右侧的最高柱子来计算蓄水量。用双指针保存左右两侧的最高柱子，如果当前柱子超过最高柱子就更新最高柱子，否则可以直接根据左右两侧较短的柱子计算出当前柱子最终的蓄水量。

**能使用双指针，是因为从两侧往中间逼近时，两侧的柱子不会受中间未访问柱子的影响。**

Java实现，时间复杂度O(n)，空间复杂度O(1)

```java
public int trap(int[] height) {
        if(height==null || height.length<2) return 0;

        int left_max = 0;
        int right_max = 0;
        int left = 0;
        int right = height.length-1;
        int count=0;
        while(left<right){
            if(height[left]<height[right]){
                if(height[left]>left_max){
                    left_max=height[left];
                }else{
                    count+=left_max-height[left];
                }
                ++left;
            }else{
                if(height[right]>right_max){
                    right_max=height[right];
                }else{
                    count+=right_max-height[right];
                }
                --right;
            }
        }
        return count;
    }
```

