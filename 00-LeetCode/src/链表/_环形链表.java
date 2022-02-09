package 链表;

/**
 * @author 默烦
 * @date 2020/6/25
 */
public class _环形链表 {
    // 判断链表是否有环
    /* 思路(使用快慢指针)
    *   1. 令慢指针等于head
    *   2. 令快指针等于head.next（快指针领先慢指针一步）
    *   3. 慢指针每次走一步，快指针每次走两步
    *   4. 如果，快慢指针相等则有环
    *   5. 如果，快指针或快指针的next等于空，则无环
    * */
    public boolean hasCycle(ListNode head){
        if (head == null || head.next == null) return false;
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }
}
