<p> 
<a href="http://coco66.info:88">
<img src="http://coco66.info:88/leetcode/picture/home.png" alt="小土豆" style="zoom:50%;" /></a>
</p>

## 链表

### 简介

链表题目变化不多，解题思路主要是常规方法的组合使用。麻烦一点的地方是编程细节，需要注意移动节点时，指针的切换。做题时尽量用简单样例走读代码，多加练习~

### 常规方法

1. 反转链表

   ```java
   class Listnode{
     int val;
     ListNode next;
     ListNode(int v){val=v;}
   }
   
   ListNode reverseLinkedlist(ListNode head){
     ListNode pre = null;
     while(head!=null){
       ListNode nx=head.next;
       head.next=pre;
       pre=head;
       head=nx;
     }
     return pre;
   }
   ```

   

2. 二分链表

   二分链表，链表节点数为偶数时，返回节点序号为 len/2-1 (从0开始) ，为奇数时返回中间节点。即 1->2->3->4 返回指向2的指针，1->2->3->4->5 返回指向3的指针。

   

   ```java
   ListNode divideLinklist(ListNode head){
     if(head==null || head.next==null) return head;
     
     ListNode slow = head;
     ListNode fast = head;
     while(fast.next!=null){
       if(fast.next.next!=null){
         fast=fast.next.next;
         slow=slow.next;
       }else{
         fast=fast.next;
       }
     }
     return slow;
   }
   ```

   

3. 合并两个链表 

   ```java
   ListNode mergeLinkList(ListNode head1, ListNode head2){
     ListNode dm = new ListNode(0);  // 虚头结点
     ListNode cur = dm;
     while(head1 != null && head2 != null){ 
       cur.next=head1;
       cur=cur.next;
       head1=head1.next;
       // 逻辑判断
       cur.next=head2;
       cur=cur.next;
       head2=head2.next;
     }
     cur.next = head1!=null?head1:head2;
     return dm.next;
   }
   ```

### ***小技巧

1. 虚头结点 --- 生成新链表时避免了头结点的判断
2. 快慢指针  --- 中分链表、检测相交和成环

----

### 题目列表 

1. [反转链表](https://leetcode-cn.com/problems/reverse-linked-list) 

2. [相交链表](https://leetcode-cn.com/problems/intersection-of-two-linked-lists/)

3. [环形链表](https://leetcode-cn.com/problems/linked-list-cycle/)

4. [两数相加](http://coco66.info:88/leetcode/linkedlist/LeetCode2.html)   

5. [分隔链表](https://leetcode-cn.com/problems/partition-list/)

6. [合并两个有序链表](https://leetcode-cn.com/problems/merge-two-sorted-lists/)

7. [排序链表](https://leetcode-cn.com/problems/sort-list/)

8. [重排链表](https://leetcode-cn.com/problems/reorder-list/)

9. [两两交换链表中的节点](https://leetcode-cn.com/problems/swap-nodes-in-pairs/)

10. [删除链表的倒数第N个节点](https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/)

11. [合并K个排序链表*](http://coco66.info:88/leetcode/linkedlist/LeetCode23.html)  

12. [K个一组翻转链表*](http://coco66.info:88/leetcode/linkedlist/LeetCode25.html)  

    

