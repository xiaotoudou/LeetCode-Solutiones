## 二分法

## 简介

最基本的应用，二分查找，时间复杂度O(logn)找到目标值。二分法思想不仅限于查找，它可以以每次折半的方式缩小待检查数组的大小。

----

## 基础应用

### 二分查找

```java
//  true, 返回下标
//  false, 返回-1
int binarySearch(int[] arr, int target) {
    int left = 0;
    int right = arr.length - 1;  // left 和 right表示起点和终点，都可以取到 
    while (left <= right) {  // 没找到时，left > right
      int m = (left + right) / 2;
      if (arr[m] == target) {
        return m;
      } else if (arr[m] < target) {
        left = m + 1;
      } else {
        right = m - 1;
      }
    }
    return -1;
}
```

### 找到待插入值的位置（在某个点之前插入）

返回值left，表示可在left之前插入target，**target<=arr[res]**。当res = length时，表示插入值比数组中所有值都大。

```java
int findInsertPoint(int[] arr, int target) {
    int left = 0;
    int right = arr.length; //right取值为len，
    while (left < right) { // 结束时，left = right 
      int m = (left + right) / 2;
      if (arr[m] < target) {
        left = m + 1;
      } else {
        right = m;
      }
    }
    return left;
}
```