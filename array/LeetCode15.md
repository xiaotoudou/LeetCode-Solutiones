## 三数之和_15

> 题目：
> 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
>
> 注意：答案中不可以包含重复的三元组。
>
> 示例 :
>
> ```txt
> 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
> 
> 满足要求的三元组集合为：
> [
>   [-1, 0, 1],
>   [-1, -1, 2]
> ]
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/3sum)
>
> Edited by xiaotudou on 2020/4/27

----

### hash

思路，穷举a+b，在其他元素中找有没有数等于-(a+b)。穷举O(n2)，找有没有数O(n)。用hash表存数组，常数时间可以知道有没有-c。还有一个重复组合的问题要解决，一是数组中允许相同元素，二是同一个元素只能用一次。数组排序，相同元素聚集在一起，只处理一次，相当于剪枝；因为已经遍历过的数不能再使用，穷举时只用处理和小于0的组合，大于0之后，三数肯定大于0。

时间复杂度 ，最差情况 O(n2)，空间复杂度 O(n)

```java
public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList();
        if(nums.length<3) return res;
        Arrays.sort(nums);
        Map<Integer,Integer> map = new HashMap();
        // 存下标是保证可能存在的-（a+b）的下标大于当前穷举的下标，避免重复
  			// 可以只用大于0 的数建Hash表
        for(int i=0;i<nums.length;++i){
            map.put(nums[i],i);
        }

        for(int i=0;i<nums.length-2 && nums[i]<=0;++i){ // 大于0之后就不用处理了
            for(int j=i+1;j<nums.length;++j){
                int sum = nums[i]+nums[j];
                if(map.containsKey(-sum) && map.get(-sum)>j){
                    res.add(Arrays.asList(nums[i],nums[j],-sum));
                }
                while(j+1<nums.length && nums[j+1]==nums[j]) ++j; // b的相同元素只处理一次
            }
            while(i+1<nums.length && nums[i+1]==nums[i]) ++i; // a 的相同元素只处理一次
        }
        return res;
    }
```

### 双指针

排序之后，从小到大穷举a，在剩余有序数组中找b+c等于-a，这就是一个两数之和的问题，用双指针解决。注意相同的元素只处理一次，避免重复的问题

时间复杂度 ，最差情况 O(n2)，空间复杂度 O(n)

```java
public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList();
        if(nums.length<3) return res;
        Arrays.sort(nums);
        for(int i=0;i<nums.length-2 && nums[i]<=0;++i){
            int l=i+1, r=nums.length-1;
            while(l<r){
                int sum =nums[i]+nums[l]+nums[r];
                if(sum==0){
                    while(l+1<r && nums[l+1]==nums[l]) ++l; // b的相同元素只处理一次
                    while(r-1>l && nums[r-1]==nums[r]) --r; // c的相同元素只处理一次
                    res.add(Arrays.asList(nums[i],nums[l++],nums[r--]));
                }else if(sum>0){
                    --r;
                }else{
                    ++l;
                }
            }
            while(i+1<nums.length-2 && nums[i+1]==nums[i]) ++i; // a 的相同元素只处理一次
        }
        return res;
    }
```



