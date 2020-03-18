## 反转链表_206

> 题目：
> 反转一个单链表。
>
> 示例 :
>
> ```txt
> 输入: 1->2->3->4->5->NULL
> 输出: 5->4->3->2->1->NULL
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/reverse-linked-list/)
>
> Edited by xiaotudou on 2020/3/18

----

### 迭代

1. cur指向待移动的节点，pre指向cur之前的节点，tmp记录cur的下一个节点
2. 调整cur的next指针，指向pre
3. pre指针和cur指针分别向前移动一位
4. 终止条件就是cur指向null，此时pre指向之前的尾节点，也就是反转之后的头结点

```java
public ListNode reverseList(ListNode head) {
   ListNode pre=null; // 头结点的next 需要指向null
   ListNode cur=head;
   while(cur!=null){
     ListNode tmp=cur.next;
     cur.next=pre;
     pre=cur;
     cur=tmp;
   }
   return pre;
}
```

### 递归

从中间一个节点来看，假设reverseList能够返回下一个节点反转之后的结果，即newHead。此时head右边的链表已经反转完成，头节点为newHead（旧链表的尾节点）；注意此时指针的状态，head指针指向当前节点，head.next指向的下一个节点其实是新链表的尾节点，此时调整新链表的尾节点指向head既可。

```java
public ListNode reverseList(ListNode head) {
  if(head==null || head.next == null){
    return head; //找到最后一个节点，旧链表的尾节点直接作为头节点向上返回
  }
  ListNode newHead = reverseList(head.next);
  head.next.next=head; // head.next 指向新链表的tail，调整tail.next指向当前节点
  head.next=null; // 当前节点就是尾节点，悬空，否则两个元素的情况会成环
  return newHead;  // 将头结点向上返回
} 
```