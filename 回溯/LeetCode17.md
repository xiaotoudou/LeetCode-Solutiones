## 电话号码的字母组合_17

> 题目：给定一个仅包含数字 `2-9` 的字符串，返回所有它能表示的字母组合。给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
>
> <img src="https://assets.leetcode-cn.com/aliyun-lc-upload/original_images/17_telephone_keypad.png" alt="img" style="zoom:40%;" />
>
> 示例 :
>
> ```txt
> 输入："23"
> 输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/)
>
> Edited by xiaotudou on 2020/3/8

----

### 动态规划

输入“2”（"abc"），“3”("def")，是一个组合问题，第一个和第二个位置分别有三种可能，一共3*3=9种可能。如果输入一个字符串，对于中间一个数字 i ，我们已经知道在此之前的所有可能，那么现在只需要将这个数字对应的字母分别添加在之前每一种可能之后即可。

Java实现，时间复杂度$O(3^n*4^m)$，计算量就是列举所有可能性，n个对应字符为3的数字，和m个对应4个字符的数字。空间复杂度也是一样$O(3^n*4^m)$。

```java
private final Map<Integer, String> phoneTable = new HashMap<>(){{
        put(2,"abc");
        put(3,"def");
        put(4,"ghi");
        put(5,"jkl");
        put(6,"mno");
        put(7,"pqrs");
        put(8,"tuv");
        put(9,"wxyz");
}};

private List<String> helper(String digits, int index, List<String> result){
  // 终止条件，遍历所有数字
  if(index == digits.length()){
    return result;
  }

  String str = phoneTable.get(digits.charAt(index)-'0');
 // 数组需要指定容量，否则数据量大会多次扩容，对性能影响较大
  List<String> newResult = new ArrayList<String>((int)(result.size()*str.length()/0.75));
  for(int i=0;i<str.length();++i){
    String tmp = str.substring(i,i+1);
    // 将每个字符追加到之前的结果后面组成新的字符串
    for(String s:result){
      newResult.add(s+tmp);
    }
  }
  // 追加下一个数字对应的字符
  return helper(digits, index+1, newResult);
}

public List<String> letterCombinations(String digits) {
  List<String> result = new ArrayList();
  if(digits == null || digits.length() == 0){
    return result;
  }
  // 注意要添加一个空字符串作为开头，否则第一个数字加入时出现空指针错误
  result.add("");
  
  return helper(digits,0,result);
}
```

### 回溯

动态规划的思想是将之前的结果存在缓存表，**每次递归都是穷举一个数对应的所有可能，往后的计算不需要考虑之前的结果，且每个字符处理后就是当前的解，这是一种联机算法**。回溯思想则是逐个试探一个数字对应的字符，如下图所示，和DFS（深度优先遍历）遍历树的过程一样，递归到叶子节点时，得到一种可能的解；直到最后“cf”这条路径走完，得出完备的解。

DFS问题模板解法三板斧：  
1. 一种可能入栈 
2.  继续试探 
3. 回溯，当前可能出栈
4. 终止条件就是到达叶子节点

![算法过程.png](https://pic.leetcode-cn.com/38567dcbb6401d88946ca974aacffb5ab27cb1ad54056f02b59016c0cc68b40f-file_1562774451350)

Java实现，复杂度同动态规划解法。

```java
private final Map<Integer, String> phoneTable = new HashMap<>(){{
        put(2,"abc");
        put(3,"def");
        put(4,"ghi");
        put(5,"jkl");
        put(6,"mno");
        put(7,"pqrs");
        put(8,"tuv");
        put(9,"wxyz");
    }};

    private void helper(String digits, int index, StringBuffer strBuf, List<String> result){
      // 递归到叶子节点，已到终点，收集到结果表
        if(index == digits.length()){
           result.add(strBuf.toString());
           return;
        }
      
        String str = phoneTable.get(digits.charAt(index)-'0');
      // 遍历一个数字对应的所有可能性
        for(int i=0;i<str.length();++i){
          // 一种可能入栈
            strBuf.append(str.charAt(i));
          // 继续试探
            helper(digits, index+1, strBuf, result);
          // 当前可能出栈
            strBuf.deleteCharAt(strBuf.length()-1);
        } 
    }

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList();
       // 采用StringBuffer 缓存中间过程
        if(digits != null && digits.length() > 0){
            helper(digits,0,new StringBuffer(digits.length()),result);
        }
        return result;
    }
```

