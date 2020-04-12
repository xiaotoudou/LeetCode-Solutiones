## 插入区间_57

> 题目：
>
> 给出一个*无重叠的 ，*按照区间起始端点排序的区间列表。
>
> 在列表中插入一个新的区间，你需要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）。
>
> 示例 :
>
> ```txt
> 输入: intervals = [[1,3],[6,9]], newInterval = [2,5]
> 输出: [[1,5],[6,9]]
> 
> 输入: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
> 输出: [[1,2],[3,10],[12,16]]
> 解释: 这是因为新的区间 [4,8] 与 [3,5],[6,7],[8,10] 重叠。
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/insert-interval)
>
> Edited by xiaotudou on 2020/4/10

----

### 排序

该题中，数组进过排序的，可以直接处理。新加入的节点只会影响最后一个节点的区间，但是一旦插入新结点后，在它之后所有节点需要重新判断。思路和 [合并区间](https://leetcode-cn.com/problems/merge-intervals/)很类似

1. 找到新的节点待插入的位置 
2. 合并新结点 
3. 合并剩余节点

时间复杂度 O(n)，空间复杂度 O(n)

```java
public int[][] insert(int[][] intervals, int[] newInterval) {
        if(newInterval == null ||newInterval.length == 0) return intervals;
        LinkedList<int[]> res = new LinkedList();
        int index=0;
  			// 起始比newInterval小的区间直接放到结果链表中
        while(index<intervals.length && intervals[index][0]<newInterval[0])
            res.add(intervals[index++]);
        
  			// 1. 此时index为0， 或是指向的区间起始点大于newIntercal的起始点
  			// 2. 新插入的区间可能会改变链表最后一个区间的分布
        int[] last;
        if(res.isEmpty() || res.getLast()[1] < newInterval[0]){ // 起始点没有落在链表最后区间中
            res.add(newInterval);
        }else{  // 合并newinterval 和 last节点
            last = res.removeLast();
            last[1] = Math.max(last[1],newInterval[1]); // 起始点只能是last[0], 结尾取较大的值
            res.add(last);
        }    
        // 新插入一个区间之后，可能会影响后面所有区间的分布
        while(index<intervals.length){
            last = res.getLast();
            if(intervals[index][0]>last[1]){  // 没有重叠区间就直接加入
                res.add(intervals[index]);
            }else{  // 存在重叠就修改链表最后一个节点的值
                last = res.removeLast();
                last[1]= Math.max(last[1],intervals[index][1]);
                res.add(last);
            }
            ++index;
        }
        return res.toArray(new int[res.size()][2]);
    }
```