## 串联所有单词的子串_30

> 题目：
>
> 给定一个字符串 **s** 和一些长度相同的单词 **words。**找出 **s** 中恰好可以由 **words** 中所有单词串联形成的子串的起始位置。
>
> 注意子串要与 **words** 中的单词完全匹配，中间不能有其他字符，但不需要考虑 **words** 中单词串联的顺序。
>
> 示例 1 :
>
> ```txt
> 输入：
>   s = "barfoothefoobarman",
>   words = ["foo","bar"]
> 输出：[0,9]
> 解释：
> 从索引 0 和 9 开始的子串分别是 "barfoo" 和 "foobar" 。
> 输出的顺序不重要, [9,0] 也是有效答案。
> ```
>
> 示例 2 :
>
> ```txt
> 输入：
>   s = "wordgoodgoodgoodbestword",
>   words = ["word","good","best","word"]
> 输出：[]
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/substring-with-concatenation-of-all-words/)
>
> Edited by xiaotudou on 2020/3/19

----

### 暴力

s中减去words总长度后（小于words总长度的字符串不可能串联），每个字符都可以作为起点截取单词，遍历s字符串，直到words中所有单词都覆盖，记录该起点；或是中途达到失败条件，某个单词个数已大于words中所包含的，直接跳出循环。

Java实现， 最差情况时间复杂度$O(n*k)$， n为s的长度，k为words总长度，空间复杂度O(n)

```java
public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res=new ArrayList();
        if(s == null ||s.length() == 0 || words.length == 0){
            return res;
        }

        Map<String,Integer> wordCount = new HashMap();
        for(String w: words){   // 统计words 中的单词
            wordCount.put(w,wordCount.getOrDefault(w,0)+1);
        }
  
  			int len=words[0].length();
        int all_len = words.length*len;

        for(int i=0;i<s.length()-all_len+1;++i){ // 只用试探减去words总长度后的字符作为起点
            int count=0;
            Map<String, Integer> tmp = new HashMap();
            for(int j=i; j<i+all_len; j+=len){ // 最多往后遍历all_len
                String str = s.substring(j,j+len);
                tmp.put(str,tmp.getOrDefault(str,0)+1);
                ++count;
                if(tmp.getOrDefault(str,0) > wordCount.getOrDefault(str,0)){ //中间有单词不符合
                    count = 0;
                    break;
                }
            }
            if(count == words.length){
                res.add(i);
            }
        }
        return res;
    }
```

### 滑动窗口

考虑到每次右侧增加新单词时，只会影响左侧的单词，所以维护一个队列来记录已经遍历过的单词，如果队列长度等于all_len，说明此时窗口起点满足条件。如果右侧增加的单词超过wordCount中的个数，就需要窗口左侧出队，直到再次满足条件。这里面包含两种情况，一种是遇到words中没有的单词，需要窗口全部出队，另一种是超过单词个数，直到新入队的单词个数小于等于wordCount。

**窗口中包含的单词一定是满足条件的**

s中超过一个单词长度的部分被之前的遍历覆盖了，因此只用试探一个单词长度，每次都遍历完s。

Java 实现， 时间复杂度O(n)， 空间复杂度O(n/len)

```java
public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList();
        if(s == null || s.length() ==0|| words.length ==0){
            return res;
        }
        
        Map<String,Integer> wordCount = new HashMap();
        for(String w: words){
            wordCount.put(w,wordCount.getOrDefault(w,0)+1);
        }

        for(int i=0, len=words[0].length();i<len;++i){  // 只用试探一个单词的长度
            Map<String,Integer> window = new HashMap();
            int left=i;
            int right=i;
            int count=0;
            while(right+len <= s.length()){
                String w = s.substring(right, right+len);
                right+=len;
                ++count;
                window.put(w, window.getOrDefault(w,0)+1); // 右侧入队新单词
              // 新入队单词造成条件不满足，需要左边出队，直到再次满足条件
                while(window.getOrDefault(w,0) > wordCount.getOrDefault(w,0)){ 
                    String l = s.substring(left,left+len);
                    window.put(l,window.getOrDefault(l,0)-1);
                    left+=len;
                    --count;
                }
              // 窗口大小等于words长度，记录起点
                if(count == words.length){
                    res.add(left);
                }
            }
        }
        return res;
    }
```
