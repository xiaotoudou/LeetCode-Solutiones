<p> 
<a href="http://coco66.info:88">
<img src="http://coco66.info:88/leetcode/picture/home.png" alt="小土豆" style="zoom:50%;" /></a>
</p>

## 链表

### 简介

> 数组需要在内存中申请一整块连续内存来存储数据，且申请之后的空间大小是不可以变化的。
>
> 有些应用场景，数组是不方便处理的，比如**事先不知道需要多大的空间来保存数据**，提前申请一个大数组无疑是浪费空间；如果分配内存太小，空间不够时就需要重新一个更大的数组，再将现有的数组逐个搬到新的数组，这个操作是非常耗时的。另外，有的场景**需要频繁在线性表中间插入**，例如数组[1,2,4,5]，现在需要插入3到2之后，那么需要将4，5 往后移动一个位置，才能为3腾出一个位子进行插入，数组的插入操作时间复杂度是O(n)。 
>
> 因此，我们需要一种新的数据结构，能够应对不确定空间需求，实现O(1) 时间插入。这个结构就是链表，链表的节点之间通过指针连接，因此在内存上不需要连续内存，只要能找到分配一个节点的空间就可以插入新的节点，只要物理内存没有耗尽，链表的长度可以无限增加。另外，链表做插入操作时，在找到插入位置后，插入操作本身是O(1) 时间复杂度。当然，链表也有自己的局限，例如在**内存占用**上，需要额外的空间来保存指针；另外，**不支持随机查找操作**，不能像数组使用下标O(1) 时间访问。链表的查找操作只能从头结点开始，逐个查找，因此也不能在链表上实现二分搜索。
>
> 2020/07/01 For Linjie

### 链表基本款式

单链表是最基础，最常用的结构，其他的类型都是衍生品。单链表的基本操作是重中之中，要非常数；双链表的操作复杂些，但是需要知道怎么实现。另外两个知道定义就行。

1. #### 单链表

   根据链表中节点的值是否有序，可以分为**无序链表**和**有序链表**。无序链表采用**头插法或者尾插法**插入，时间复杂度O(1)，查找可能需要遍历整个链表，平均时间复杂度 O(n)。链表的删除和修改都是基于查找操作，因此也是O(n)。有序链表为了维持节点值有序，插入前需要先找到待插入点，这一步查找操作消耗时间O(n)，因此有序链表的插入操作为O(n)，其他与无序链表一致。

   有序链表维持节点有序，增加了额外的计算量，但是在有序链表的基础上可以实现**跳表**，可以将增删改查操作时间复杂度都降至O(logn)。

   **无序链表**

   无序链表只需要将对象插入到链表即可，头插法和尾插法其实分别就是实现栈和队列的方法。这里用到了虚头结点的技巧，dumy解决了需要判断头结点的问题，简化编程实现。

   ```c
   // 链表节点的数据结构
     struct Listnode {
       int val;
       Listnode* next;
     }
   
   // 虚拟头结点, 避免了头结点是否为空的判断( 这里不理解可以语音说)
     Listnode dumy = malloc(sizeof(struct Listnode));  //生成一个新的哑节点，不保存数据
     Listnode* tail = dumy; // 指向最后尾节点
   
     // 头插法规定每次插入到虚头结点的后面。
     // 虚头结点相当于自行车队的 “前骑”，新的伙伴就跟在前骑后面
   
     // 比如插入 1，链表变成 dumy->1, 此时tail 指针指向“1”这个节点
   	// 再插入2，dumy->2->1, tail仍然指向 “1”，tail 永远指向自行车队的最后一个
     void insertHead(int val) {
       Listnode newNode = malloc(sizeof(struct Listnode));
       newNode->next = dumy->next;  // 新来的节点先指向此时虚节点后面的节点
       dumy->next = newNode; //  再将虚头结点指向自己
       if (tail == dumy) { // 只有在链表为空时需要移动一次
         tail = newNode;
       }
     }
   
     // 尾插法, 每次插入在尾节点之后。也就是新来的伙伴直接跟在车队最后
   	// 插入 1，链表变成 dumy->1, 此时tail 指针指向“1”这个节点
   	// 再插入2，dumy->1->2, tail指向 “2”，tail指向“2”
     void insertTail(int val) {
       tail->next = malloc(sizeof(struct Listnode));  // 插入 2 时，tail 指向1，将1的next 指向2
       tail = tail->next; // tail 移动到最后
     }
   
     // 找到第一个和target相等的节点值，否则返回null
     int get(int target) {
       Listnode* head = dumy->next;
       while (head != null) {  // head 移动到null时，说明没找到
         if (head->val == target) {  // 找到目标值就跳出循环
           break;
         }
         head = head->next; // 不是目标值就移动到下一个
       }
       
      if(head != null){
        return head->val
      }else{
        return null
      }
     }
   
     // 删除得找到待删除节点的前一个节点
     void delete(int target) {
       Listnode* cur = dumy;
       while (cur->next != null && cur->next->val != target) // 比较的是下一个节点是否满足要求
         cur = cur->next; 
       if (cur->next == null) return;  // 如果cur移动到最后一个节点，说明未找到target，直接返回
       Listnode* tmp = cur->next;  //  此时cur是指向待删除节点的前一个节点，tmp指向待删除节点
       cur->next = tmp->nexs
       free(tmp);   //  需要释放节点的内存，避免内存泄漏
     }
   ```

   **有序链表**

   有序链表需要保证插入节点的顺序，有序链表的插入方法有些区别

   ```c
     void insert(int val) {
       Listnode* cur = dumy;
       // 链表没法回退，所以需要找到待插入点的前一个节点
       while (cur->next != null && cur->next->val < val) cur = cur->next;
       // 跳出循环后，cur有三种可能
       // 1. cur->val==val  重复值
       // 2. cur->next == null 已经指向最后一个节点，说明此时val大于链表中的最大值
       // 3. cur->next->val > val cur的下一个节点值是第一个大于val的节点
       //    比如 1->2->4->5,  插入3, 此时cur指向 2 这个节点
       if(cur->val==val){  // 重复值不插入，直接返回
         return;
       }
       // 2 和 3 两种情况直接在 cur 节点之后
       Listnode* newNode = malloc(sizeof(struct Listnode));
       newNode->val=val;
       newNode->next=cur->next;
       cur->next=newNode;
     }
   ```

   > 实现了“增删改查”的方法后，要写样例来测试，比如插入1万次，修改1万次，删除5千次，最后检查链表是不是预期的状态。
   >
   > 总结：单链表的样式有两种，一种是无序链表，可以从头部插入或是尾部插入，时间复杂度 O(1)；另一种是有序链表，插入需要保证链表有序，时间复杂度 O(n)。查找和删除操作，两种链表的实现是一样的，时间复杂度都是O(n)。

   

2. #### 双向链表

   节点结构中增加了前向指针，方便逆序访问。基本操作的逻辑差不多

   

3. #### 环形链表

   尾节点的next指针指向头结点

   

4. #### 双向环形链表

   双链表的尾节点next指针指向头结点，头结点的前向指针prev指向尾节点

   

### 算法题基础方法

LeetCode的链表题目变化不多，解题思路主要是基础方法的组合使用。麻烦一点的地方是编程细节，需要注意移动节点时指针的调整，一是注意节点断开后要 悬空，否则会成环；二是时刻注意保存后面未处理部分的头结点，否则会丢失后面的节点。

**以下三种链表操作要反复练习，先理解，再按照自己舒服的样式来实现，然后背的很熟很熟，坐车无聊时都可以想象这三个操作怎么做。做题时，发现后面难的题其实就是这三种方法的组合而已~掌握这些，再稍加练习，足以应对一线互联网的链表题，链表题的考察是很火热的**

1. 反转链表

   以下是反转链表的迭代写法，采用“头插法”，原地将链表逆序，时间复杂度 O(n)，空间复杂度 O(1)。第一次看就去LeetCode上看题解，有图示说明，见下面题目列表第一题。

   ```java
   ListNode* reverseLinkedlist(ListNode* head){
     ListNode* pre = null; // pre 设为null
     while(head!=null){
       ListNode* nx=head->next;
       head->next=pre;
       pre=head;
       head=nx;
     }
     return pre;
   }
   ```

   

2. 二分链表

   二分链表，链表节点数为偶数时，返回节点序号为 len/2-1 (从0开始) ，为奇数时返回中间节点。即 1->2->3->4 返回指向2的指针，1->2->3->4->5 返回指向3的指针。 

   题目列表中“相交链表”就是用到快慢指针

   **注意这里用到的快慢指针技巧，有多个应用场景，注意提炼这个技巧能做什么！**

   > #### 小技巧
   >
   > 1. 快慢指针  --- 中分链表、检测链表相交和成环

   ```java
   ListNode* divideLinklist(ListNode* head){
     if(head==null || head->next==null) return head;
     
     ListNode* slow = head;
     ListNode* fast = head;
     while(fast->next!=null){
       if(fast->next->next!=null){
         fast=fast->next->next;
         slow=slow->next;
       }else{
         fast=fast->next;
       }
     }
     return slow;
   }
   ```

   

3. 合并两个链表 

   两个链表的合并，可以参考“合并两个有序链表”

   > #### 小技巧
   >
   > 1. 虚头结点 --- 生成新链表时避免了头结点的判断

   ```java
   ListNode* mergeLinkList(ListNode* head1, ListNode* head2){
     ListNode dm = new ListNode(0);  // 虚头结点
     ListNode* cur = dm;
     while(head1 != null && head2 != null){ 
       cur->next=head1;
       cur=cur->next;
       head1=head1->next;
       // 逻辑判断
       cur->next=head2;
       cur=cur->next;
       head2=head2->next;
     }
     cur->next = head1!=null?head1:head2;
     return dm->next;
   }
   ```

----

### 题目列表 

以下题目是热门面试题，要按照顺序来做。基本都是上面三种方法的组合，先不要看答案，想想用上面的方法怎么解。

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

    

