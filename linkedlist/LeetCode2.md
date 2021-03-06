## 两数相加_2

> 题目：
> 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
>
> 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
>
> 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
>
> 示例 :
>
> ```txt
> 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
> 输出：7 -> 0 -> 8
> 原因：342 + 465 = 807
> ```
>
> 题目来源： [力扣 (LeetCode)](https://leetcode-cn.com/problems/add-two-numbers)
>
> Edited by xiaotudou on 2020/3/15

### 虚头结点+分类判断技巧

其实链表就相当于栈的一种实现，可以将求得的低位直接保存到链表中。使用虚头结点，避免了头结点的判断。用一个尾指针指向队尾，每次在最后追加新节点。

Java实现

```java
public ListNode addTwoNumbers(ListNode l1, ListNode l2){
        int carry =0;
        int n1=0;
        int n2=0;
        ListNode head = new ListNode(0);
        ListNode tail = head;
        while(l1 != null || l2 != null){
            n1 = l1 == null?0:l1.val;
            n2 = l2 == null?0:l2.val;
            int sum = n1 + n2 + carry;
            carry = sum/10;
            ListNode p = new ListNode(sum%10);
            tail.next = p;  // tail 记录尾节点
            tail = p;
            l1 = l1 == null?null:l1.next;
            l2 = l2 == null?null:l2.next;
        }
        if(carry>0){
            tail.next = new ListNode(carry);
        }
        return head.next;
}
```
