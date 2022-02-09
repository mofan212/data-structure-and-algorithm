package 链表;

/**
 * @author 默烦
 * @date 2020/6/25
 */
public class _反转链表 {
    // 返回的是反转后的链表首节点
    /* 递归
      思路：
       1. reverseList()方法的作用是反转链表，返回反转链表的首节点
       2. 如果我传递head.next进方法，那么就是将除head以外的节点都反转
       3. 将head.next节点的next指向head
       4. 反转后，head的next应该为null
    * */
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode newHead = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    /*非递归方式 迭代
    * 思路：
    *  1. 引入第三个变量tmp，为了防止链表断了，使tmp指向head.next
    *  2. 让head.next指向newhead
    *  3. newhead指向head
    *  4. head指向tmp
    *  5. 重复以上操作，直到head等于null
    * */
    public ListNode reverseList_2(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode newHead = null;
        while (head != null) {
            ListNode tmp = head.next;
            head.next = newHead;
            newHead = head;
            head = tmp;
        }
        return newHead;
    }
}
