## K个一组翻转链表_25

> 题目：
> 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
>
> **说明：**
>
> - 你的算法只能使用常数的额外空间。
> - **你不能只是单纯的改变节点内部的值**，而是需要实际进行节点交换。
>
> 示例 :
>
> ```txt
> 给你这个链表：1->2->3->4->5
> 
> 当 k = 2 时，应当返回: 2->1->4->3->5
> 
> 当 k = 3 时，应当返回: 3->2->1->4->5
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/reverse-nodes-in-k-group)
>
> Edited by xiaotudou on 2020/3/17

----

### 栈

遍历链表，依次进栈，如果栈的大小达到k，就逐个弹出，实现逆序，最后剩余节点附加在尾节点之后即可。因为使用了O(k)的栈作为缓存，因此还不算符合题目要求。

Java实现，遍历一遍链表，时间复杂度O(n)，空间复杂度O(k)

```java
public ListNode reverseKGroup(ListNode head, int k) {
        ListNode res = new ListNode(0);
        ListNode tail = res;
        ListNode p = head;
        Deque<ListNode> stock = new ArrayDeque<ListNode>(k);
        while(p!=null || stock.size() == k){
            if(stock.size()<k){
                stock.addLast(p);
                p = p.next;
            }else{
                while(!stock.isEmpty()){
                    ListNode node = stock.removeLast();
                    tail.next = node;
                    tail = tail.next;
                    tail.next = null; // 一定要将尾指针悬空，否则[1,2] k=2，会成环
                }
            }
        }
        if(!stock.isEmpty()){
            tail.next = stock.removeFirst();
        } 
        return res.next;
    }
```

### 尾插法



```java
private ListNode reverse(ListNode head){
        ListNode pre=null;
        ListNode cur=head;
        while(cur!=null){
            ListNode tmp=cur.next;
            cur.next=pre;
            pre=cur;
            cur=tmp;
        }
        return pre;
    }

    public ListNode reverseKGroup(ListNode head, int k) {
       ListNode dummy = new ListNode(0);
       dummy.next=head;
       ListNode pre = dummy;
       ListNode tail=pre;
       while(tail.next!=null){
           for(int i=0;i<k && tail!=null;++i){
               tail=tail.next;
           }
           if(tail == null){
               break;
           }
           ListNode start=pre.next;
           ListNode next=tail.next;
           tail.next=null;
           pre.next=reverse(start);
           start.next=next;
           pre=start;
           tail=pre;
       }
       return dummy.next;
    }
```