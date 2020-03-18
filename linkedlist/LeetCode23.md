## 合并K个排序链表_23

> 题目：
> 合并 *k* 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。
>
> 示例 :
>
> ```txt
> 输入:
> [
>   1->4->5,
>   1->3->4,
>   2->6
> ]
> 输出: 1->1->2->3->4->4->5->6
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/merge-k-sorted-lists/)
>
> Edited by xiaotudou on 2020/3/16

----

### 暴力法

k个有序链表，所有节点中最小的值一定在头结点中产生，每次遍历头结点就可以找到当前的最小值。

java实现， 时间复杂度O(k)*O(n) ，n为最长链表的长度，空间复杂度 O(1)。

```java
public ListNode mergeKLists(ListNode[] lists) {
        ListNode[] heads = new ListNode[lists.length];
        for(int i=0;i<lists.length;++i){
            heads[i]=lists[i];
        }
        ListNode res = new ListNode(0);
        ListNode tail = res;
        boolean isEnd = false;
        while(!isEnd){
            isEnd = true;
            int minIndex = 0;
            int minVal = Integer.MAX_VALUE;
            for(int i=0;i<heads.length;++i){
                int index = heads[i] == null? -1:i;
                if(index != -1){
                    isEnd=false;
                    if(heads[i].val < minVal){
                        minVal = heads[i].val;
                        minIndex = index;
                    }
                }
            }
            if(!isEnd){
                tail.next = new ListNode(minVal);
                tail = tail.next;
                heads[minIndex] = heads[minIndex].next;
            }
        }
        return res.next;
    }
```

### 优先级队列

暴力法中遍历头结点找最小值的过程可以优化。将头结点建一个最小堆，对数时间弹出最小值，然后对数时间插入一个新值。

Java实现， 最坏情况所有链表一样长，时间复杂度 O(logk)*O(n) ；最小堆存储头结点即可，空间复杂度O(k)。

```java
public ListNode mergeKLists(ListNode[] lists) {
        Queue<ListNode> heads = new PriorityQueue(
          Comparator.comparingInt((ListNode x) -> x.val));
        for(int i=0;i<lists.length;++i){
            if(lists[i] != null) // 处理空链表
                heads.offer(lists[i]);
        }
        ListNode res = new ListNode(0);
        ListNode tail = res;
        while(!heads.isEmpty()){
            ListNode cur = heads.poll();
            ListNode node = new ListNode(cur.val);
            tail.next = node;
            tail = tail.next;
            if(cur.next != null){
                heads.offer(cur.next);
            }
        }
        return res.next;
    }
```

### 暴力法2

数组中取两个链表，合并两个有序链表，合并之后的新链表又是同样的子问题。两两合并有一个问题，有一个链表在合并过重越来越长，如果遍历所有链表时，链表的最后一个值都是最大值，那么每一次两两合并都会遍历一遍新产生的链表。

Java实现，最坏时间复杂度 O(k)*O(n)，空间复杂度 O(1)

```java
private ListNode merge(ListNode l1,ListNode l2){
        ListNode head = new ListNode(0);
        ListNode tail = head;
        while(l1 != null && l2 != null){
            ListNode node = null;
            if(l1.val<l2.val){
                node = l1;
                l1=l1.next;
            }else {
                node = l2;
                l2=l2.next;
            }
            tail.next = new ListNode(node.val);
            tail = tail.next;
        }
        while(l2!=null){
            tail.next = new ListNode(l2.val);
            l2=l2.next;
            tail=tail.next;
        }
        if(l1!=null){ //l1 总是新的新链表，直接将剩下的连在后面即可
            tail.next = l1;
        }
        return head.next;
    }

    public ListNode mergeKLists(ListNode[] lists) {
        if(lists.length <= 0) return null;
        ListNode head = null;
      // 每次循环减少一个链表
        for(int i=0;i<lists.length;++i){
            head = merge(head,lists[i]);
        }
        return head;
    }
```

### 分治

思路和上面一样，每次合并两个链表，但不是合并旧链表和数组中的一个链表。采用分治策略，每次从链表数组中取两个出来合并，每次合并可以减少两个链表，直到最终链表数为1。

- 用队列来实现比较方便，合并之后的链表入队。

Java实现，时间复杂度O(logk)*O(n)，空间复杂度O(n)

```java
private ListNode merge(ListNode l1,ListNode l2){
    ListNode head = new ListNode(0);
    ListNode tail = head;
    while(l1 != null || l2 != null){
      int val1 = l1==null?Integer.MAX_VALUE:l1.val;
      int val2 = l2==null?Integer.MAX_VALUE:l2.val;
      int val = val1<val2?val1:val2;
      tail.next = new ListNode(val);
      tail = tail.next;
      if(val1<val2){
        l1=l1.next;
      }else {
        l2=l2.next;
      }   
    }
    return head.next;
}

public ListNode mergeKLists(ListNode[] lists) {
    if(lists.length <= 0) return null;
    Queue<ListNode> q = new ArrayDeque();
    for(int i=0;i<lists.length;++i){
      if(lists[i] != null)
        q.offer(lists[i]);
    }
    // 每次取两个链表合并，一轮循环下来，链表数量为k/2.
    while(q.size()>1){
      q.offer(merge(q.poll(),q.poll())); 
    }
    return q.poll();
}
```

