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
        while(p!=null || stock.size() == k){  // 注意k恰好等于链表长度的情况，需要再进一次循环倒序
            if(stock.size()<k){  // 栈小于k就进栈
                stock.addLast(p);
                p = p.next;
            }else{  
                while(!stock.isEmpty()){   //从栈里面逐个倒出
                    ListNode node = stock.removeLast();
                    tail.next = node;
                    tail = tail.next;
                    tail.next = null; // 一定要将尾指针悬空，否则[1,2] k=2，会成环
                }
            }
        }
        if(!stock.isEmpty()){  // 剩余节点不用倒序，从栈底取出节点
            tail.next = stock.removeFirst();
        } 
        return res.next;
    }
```

### 尾插法

先思考简单的子问题，[翻转链表](http://coco66.info:88/leetcode/linkedlist/LeetCode206.html)，实现了原地反转链表，并返回头结点。k个节点的原地反转就是每次计数k个节点，截出子链表，反转之后接回之前的链表。这里也可以写成递归的形式。

java实现，时间复杂度O(n)，空间复杂度 O(1)

```java
private ListNode reverseList(ListNode head){ // 反转链表
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
       ListNode dummy = new ListNode(0);  // 虚头结点
       dummy.next=head;
       ListNode pre = dummy; // pre指向已完成反转部分的尾节点
       ListNode tail=pre; // tail 用来计数
       while(tail.next!=null){
           for(int i=0;i<k && tail!=null;++i){  // tail移动到第k个节点
               tail=tail.next;
           }
           if(tail == null){ //tail为null， 说明剩余节点少于k个
               break;
           }
         // 截出子链表
           ListNode subHead=pre.next; // 子链表头结点
           ListNode next=tail.next; // 记录未处理链表的头结点
           tail.next=null; // 子链表尾节点悬空
         
           pre.next=reverseList(subHead); // 反转之后的头结点接回已经处理部分的尾部
        
           start.next=next;   //翻转之后，start成为尾节点，接上未处理的部分
           pre=start;  // pre 和 tail 归位到尾节点，准备下一次循环
           tail=start;
       }
       return dummy.next;
    }
```