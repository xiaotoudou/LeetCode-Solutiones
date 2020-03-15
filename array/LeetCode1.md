## 两数之和_1

> 题目：给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
>
> 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
>
> 示例 :
>
> ```txt
> 给定 nums = [2, 7, 11, 15], target = 9
> 
> 因为 nums[0] + nums[1] = 2 + 7 = 9
> 所以返回 [0, 1]
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/two-sum)
>
> Edited by xiaotudou on 2020/

----

### 暴力法

遍历每一个数，然后查找在它之后，数组中是否存在target - nums[i]。因为是从前往后遍历的，如果之前的元素的能匹配上，在遍历之前的元素时就已经找到结果了，所以第二次查找不用从开头开始。

Java实现，时间复杂度$O(n^2)$，空间复杂度 O(1)

```java
public int[] twoSum(int[] nums, int target) {   
    for(int i=0; i<nums.length; ++i){
      int a = nums[i];
      for(int j = i+1;j<nums.length;++j){
        if(target == nums[i]+nums[j]){
          return new int[]{i,j};
        }
      }
    }
    return null;
}
```

### HashMap

用HashMap缓存已经遍历过的数，那么去匹配数组中是否有数可以匹配这个过程的复杂度降到O(1)。

java实现, 时间复杂度O(n)，空间复杂度O(n).

```java
public int[] twoSum(int[] nums, int target) {
    Map<Integer,Integer> map = new HashMap((int)(nums.length/0.7));
    for(int i=0; i<nums.length; ++i){
      Integer index = map.get(target - nums[i]);
      if(index != null){
        return new int[]{index, i};
      }
      map.put(nums[i], i);
    }
    return null;
}
```

### 扩展 -- 双指针

如果数组是有序的，可以采用双指针解法。两个指针指向数组前后，如果求和大于target，--right; 和小于target，++left。但不适用于本题，因为排序会改变元素的位置。

java实现, O(n), O(1)

```java
public int[] twoSum(int[] nums, int target) {
  int l = 0;
  int r = nums.length-1;
  while(l<r){
    int sum = nums[l] + nums[r];
    if(sum == target){
      return new int[]{l,r};
    }
    else if(sum<target){
      ++l;
    }
    else{
      --r;
    }
  }
  return null;
}
```

