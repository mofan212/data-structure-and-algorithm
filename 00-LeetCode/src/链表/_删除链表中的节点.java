package 链表;

/**
 * @author 默烦
 * @date 2020/6/25
 */
public class _删除链表中的节点 {

    // 删除传入的节点，链表长度不小于2
    /* 思路分析：
       以前的做法：需要删除节点的前一个节点，将前一个节点的next指向删除节点的后一个节点
       现在的做法：将删除节点下一个节点的值赋值给删除节点，让删除节点指向其下一个节点的下一个节点
    * */
    public void deleteNode(ListNode node){
        node.val = node.next.val;
        node.next = node.next.next;
    }
}
