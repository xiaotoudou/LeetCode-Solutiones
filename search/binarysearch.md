## 二分查找

## 简介

在一个数组中查找目标数，可以从前往后遍历，时间复杂度$O(n)$。

一个饭桌游戏可以很好的阐述二分查找的原理，先准备一个数1~100，然后轮流猜，由准备数的人来提示猜的数是大了还是小了。怎么可以最快猜到目标数呢？当然是每次都猜目标范围中间的数，经过一次判断就可以省略判断另一半的计算量。正是每次都是折半，所以称为二分搜索，它的时间复杂度就是$O(logn)$。

如果是一个有序数组，该如何找到一个目标数。借助二分法的思想，取中间的数判断，如果目标数小于中间的值，中间值右边部分的数自然都是大于目标值，不需要判断。同理，如果目标值大于中间值，说明左侧的可以不必判断。

二分法思想容易理解，编程实现的细节还是需要十分注意才能不出错。**注意二分法的两个要点**：

1. 数组，因为每次都需要直接访问数组中间位置的元素，所以数据结构必须支持随机访问。有序链表就没法使用二分法
2. 有序，二分法的基础是通过中间值的判断来减少计算量；如果是无序的，没法判断数组另一半中是否包含目标值

----

### 二分查找基础款

通常查找函数的实现，是找到目标值就返回下标，否则返回-1。有几个细节点要注意

1. 起始左侧和右侧下标的取值
2. 为什么终止条件是left<=right， 而不是left<right
3. 为什么left 和 right 要 +1 和 -1
4. 如果目标值不在数组中，left 和 right  是什么状态
5. 单步调试，查看每循环一次，left 和 right是如何变化的

```java
//  true, 返回下标
//  false, 返回-1
int binarySearch(int[] arr, int target) {
    int left = 0;
    int right = arr.length - 1;  // left 和 right分别表示起点和终点 
    while (left <= right) {     // 没找到时，left > right
      int mid = (left + right) / 2;  // 取中间的位置
      if (arr[mid] > target) {   // 中间值大于目标值
        right = mid - 1;
      } else if (arr[mid] < target) {   // 中间值小于目标值
        left = mid + 1;
      } else {   // 找到目标值
        return mid;
      }
    }
    return -1;   // 未找到返回-1
}
```

### 找到待插入值的位置---找到多个重复值的起点

二分查找的基本方法是找到目标值，或是返回-1。当目标值不存在时，最终状态 left>right跳出循环。

现在要求在有序数组中找到目标值，如果不存在，就返回待插入的位置。比如 {11，22，44，54} ，插入33时，应该返回 2， 表示待插入点的下标为2，也可以理解为在4之前插入。当目标值大于数组最大值时，返回arr.length，小于最小值时，返回0。这种二分法实现需要判断返回下标位置是否等于目标值，它可能是目标值的下标，或是待插入位置。LeetCode35

数组中存在重复数时，基本二分法只能确保指向目标值的下标，但结果是不确定的。在有重复数的数组中，如果目标值存在多个，该二分法实现总是返回首个数的下标。

实现细节，如果已经找到目标值，仍然移动右侧指针，直到等于左侧指针。

```java
int findInsertPoint(int[] arr, int target) {
    int left = 0;
    int right = arr.length; //right取值为length
    while (left < right) { // 结束时，left = right 
      int mid = (left + right) / 2;
      if (arr[mid] < target) {
        left = mid + 1;
      } else {   // arr[mid]==target时，移动右侧指针
        right = mid;
      }
    }
    return left;
}
```

### 找到多个重复值的终点

Leetcode34

```java
int findLast(int[] arr, int target) {
    int left = 0;
    int right = arr.length; //right取值为length
    while (left < right) { // 结束时，left = right 
      int mid = (left + right) / 2;
      if (arr[mid] <= target) {  // arr[mid]==target时，移动左侧指针
        left = mid + 1;
      } else {   
        right = mid;
      }
    }
    return left;
}
```

