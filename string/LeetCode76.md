## 最小覆盖子串_76

> 题目：
> 给你一个字符串 S、一个字符串 T，请在字符串 S 里面找出：包含 T 所有字母的最小子串。
>
> - 如果 S 中不存这样的子串，则返回空字符串 `""`。
> - 如果 S 中存在这样的子串，我们保证它是唯一的答案。
>
> 示例 :
>
> ```txt
> 输入: S = "ADOBECODEBANC", T = "ABC"
> 输出: "BANC"
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/minimum-window-substring/)
>
> Edited by xiaotudou on 2020/4/11

----

### 滑动窗口

子串是一种连续的状态，可以使用滑动窗口穷举模型去遍历所有子串，使用HashMap计数来表示当前窗口状态。下面是剪枝策略：

1. 右边增加一个字符会改变窗口的状态，如果此时正好能够包含子串t所有字母，窗口状态记做A
2. 能够从左边窗口减少字符，减去的字符可能是不包含在字符串t，或是重复的字符，直至窗口状态不满足A
3. 回到第一步，直至右边界遍历完字符串s

时间复杂度 O(n)， 空间复杂度 O(1)

```java
public String minWindow(String s, String t) {
        if(s==null||t==null) return "";

        HashMap<Character,Integer> ts=new HashMap((int)(t.length()/0.8));
        for(char c :t.toCharArray()){
            ts.put(c,ts.getOrDefault(c,0)+1);
        }
        
        int left =0,right=0;
        int start=-1,minLen=Integer.MAX_VALUE;
        Map<Character,Integer> state = new HashMap();
        int count=0;
        while(right<s.length()){
            if(ts.containsKey(s.charAt(right))){
                if(state.getOrDefault(s.charAt(right),0)<ts.get(s.charAt(right))){
                    ++count;
                }
                state.put(s.charAt(right),state.getOrDefault(s.charAt(right),0)+1);
                if(count == t.length()){
                    while(left<=right && count==t.length()){
                        if(state.containsKey(s.charAt(left))){
                            int num=state.get(s.charAt(left))-1;
                            state.put(s.charAt(left), num);
                            if(num<ts.get(s.charAt(left))){
                                --count;
                            }
                        }
                        ++left;
                    }
                    int newLen=right-(left-1)+1;
                    if(newLen<minLen){
                        start=left-1;
                        minLen=newLen;
                    }
                }
            }
            ++right;
        }
        return start==-1?"":s.substring(start,start+minLen);
    }
```