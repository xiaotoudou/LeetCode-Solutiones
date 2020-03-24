## 缺失的第一个正数_41

> 题目：
> 给你一个未排序的整数数组，请你找出其中没有出现的最小的正整数。
>
> 你的算法的时间复杂度应为O(*n*)，并且只能使用常数级别的额外空间。
>
> 示例  1:
>
> ```txt
> 输入: [1,2,0]
> 输出: 3
> 示例 2:
> ```
>
> 示例  2:
>
> ```txt
> 输入: [3,4,-1,1]
> 输出: 2
> 示例 3:
> ```
>
> 示例  3:
>
> ```txt
> 输入: [7,8,9,11,12]
> 输出: 1
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/first-missing-positive)
>
> Edited by xiaotudou on 2020/

----

### 位集

长度为length的数组能够存储的正整数为1~length，如果数组中包含一个超出范围的数，就会挤占一个位置。如果所有数全部包含，就返回length+1。先遍历数组，位集中记录哪些数字出现过。然后从位集中找到第一个未出现的数字即可。

Java实现，时间复杂度O(n)，空间复杂度O(n)

```java
public int firstMissingPositive(int[] nums) {
        if(nums == null||nums.length == 0){
            return 1;
        }
        int len = nums.length;
        BitSet bs = new BitSet(nums.length+1);
        for(int n:nums){
            if(n>0 && n<=len){
                bs.set(n);
            }
        }
        return bs.nextClearBit(1);
    }
```

### 数组作为位集

更进一步，用数组自身来记录有多少数已经存在。交换处于“范围”中的数，使得这些数等于下标+1的位置。使用的是尾递归，编译器会自动优化，栈空间不会增加空间占用。

Java实现， 时间复杂度O(n)，空间复杂度O(1)

```java
private static void swap(int[] nums, int i, int j){
    int tmp = nums[i];
    nums[i]=nums[j];
    nums[j]=tmp;
  }

  private static void helper(int[] nums, int i){
    if(nums[i]<1 || nums[i]>nums.length || (nums[i]==i+1)) return;
    if(nums[i] == nums[nums[i]-1]) return; // 两个位置数字重复，其中一个已就位，不返回会死循环
    swap(nums,i,nums[i]-1);
    helper(nums, i); // 当前位置还可以移动就继续递归
  }

  public static int firstMissingPositive(int[] nums) {
    if(nums == null||nums.length == 0){
      return 1;
    }
    int len = nums.length;

    // 从前往后逐个检查数字，每个数字递归交换位置，直到“就位”或是不符合要求
    for(int i=0; i<len; ++i){
      helper(nums, i);
    }
	// 找到第一个数组中数与下标不能对应的数
    for(int i=0; i<len; ++i){
      if(nums[i]!=i+1){
        return i+1;
      }
    }
    return len+1; // 全部都能对应上
  }
```

