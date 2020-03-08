## 组合总和_39

> 题目：
> 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。candidates 中的数字可以无限制重复被选取。
>
> 说明：
>
> 所有数字（包括 target）都是正整数。
> 解集不能包含重复的组合。 
>
> 示例1 :
>
> ```txt
> 输入: candidates = [2,3,6,7], target = 7,
> 所求解集为:
> [
>   [7],
>   [2,2,3]
> ]
> ```
>
> 示例2 :
>
> ```txt
> 输入: candidates = [2,3,5], target = 8,
> 所求解集为:
> [
>   [2,2,2,2],
>   [2,3,3],
>   [3,5]
> ]
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/combination-sum)
>
> Edited by xiaotudou on 2020/3/8

----

### 回溯

设想一个场景，已经组合出一个数 n，很接近但仍然小于target，我们接下来从candidates中逐个取值加上n，有的加上后仍小于target，有的大于target，中间可能有一个值恰好等于target，那么就可以保存下这个组合。从头开始想这个问题，开始时和（sum）为0，每次从数组中取一个数来求和，只要sum小于target，就可以重复从数组中取值，每次有n种选择，典型的多叉树DFS问题。

这题有一个难点，就是怎么去重，比如第一个示例中，（2，2，3）保存后；从3开头时，还会遍历出（3，2，2），这就出现了重复。解决办法就是控制后遍历的取值范围，比如说“2”打头遍历之后，与“2”相关的解都已经找到了，那么之后的遍历，就不应该再考虑“2”。

Java实现

```java
// 这些变量不需要在迭代过程中的共享变量，在参数列表里面传递，可读性不大好
private int tar;
private List<List<Integer>> result;
private int[] array;

private void backtrack(int sum, int begin, List<Integer> list){
  // 终止条件，得到解或是sum已经大于target
  if(sum == tar){
    result.add(new ArrayList<Integer>(list));
    return;
  }else if(sum > tar){
    return;
  }
// 每个数从begin开始，去除重复
// 遍历过程就是DFS三部曲
  for(int i=begin; i<array.length; ++i){
    list.add(array[i]);
    backtrack(sum + array[i], i, list);
    list.remove(list.size()-1);
  }
}

public List<List<Integer>> combinationSum(int[] candidates, int target) {
  this.tar = target;
  this.array = candidates;
  this.result = new ArrayList<List<Integer>>();
// 从头开始遍历，结果保存到共享变量result
  backtrack(0,0, new ArrayList<Integer>());
  return result;
}
```

### 剪枝

主要有两部分冗余计算可以消除。

1. 在上面的计算中，比如示例1中，取值2、3后，再去试探6时，发现大于target，向上一层返回。**如果candidates是有序的，那么“6”之后的数一定大于target，就不用遍历了。**
2. 另一个细节是找到一个正确解之后，应该往上回溯两层。比如说示例1中，已经找到了2、2、3，那么就不应该再去试探“3”之后的数，应该往上回溯，回到只有2，再往后遍历，因为可能还有5可以组成7。

```java
private int tar;
private List<List<Integer>> result;
private int[] array;

private boolean backtrack(int sum, int begin, List<Integer> list){
  if(sum == tar){
    result.add(new ArrayList<Integer>(list));
    return;
  }

  for(int i=begin; i<array.length; ++i){
    // 1.大于target就可以返回了
    if(sum + array[i] > tar){
      return false;
    }
    
    list.add(array[i]);
    // 2. 如果找到正确解，往上回溯，注意返回false，否则就一路回到根节点结束
    if(backtrack(sum + array[i], i, list)){
      list.remove(list.size()-1);
      return false;
    }
    list.remove(list.size()-1);
  }
}

public List<List<Integer>> combinationSum(int[] candidates, int target) {
  this.tar = target;
  this.array = candidates;
  this.result = new ArrayList<List<Integer>>();
// 先排序，方便后面剪枝
  Arrays.sort(candidates);
  backtrack(0,0, new ArrayList<Integer>());
  return result;
}

```