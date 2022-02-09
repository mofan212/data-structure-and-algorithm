package 栈;

/**
 * @author 默烦
 * @date 2020/7/1
 */

import java.util.HashMap;
import java.util.Stack;

/**
 * 示例： "()" : true
 * "(){}[]" : true
 * "(]" : false
 * "([)]" : false
 * "{[]}" : true
 */
public class _有效的括号 {

    private HashMap<Character, Character> map = new HashMap<>();

    public _有效的括号() {
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');
    }

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {   // 左括号
                stack.push(c);
            } else { // 右括号
                if (stack.empty()) return false;
                if (c != map.get(stack.pop())) return false;
            }
        }
        return stack.empty();
    }

    public boolean isValid_1(String s) {
        Stack<Character> stack = new Stack<>();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '{' || c == '[') { // 左括号
                stack.push(c);
            } else { // 右括号
                if (stack.empty()) return false;
                char left = stack.pop();
                if (left == '(' && c != ')') return false;
                if (left == '{' && c != '}') return false;
                if (left == '[' && c != ']') return false;
            }
        }
        return stack.empty();
    }

    /**
     * 括号的情况：[({})]
     * 效率低下
     */
    public boolean isValid_2(String s) {
        while (s.contains("{}")
                || s.contains("()")
                || s.contains("[]")) {
            s = s.replace("{}", "");
            s = s.replace("()", "");
            s = s.replace("[]", "");
        }
        return s.isEmpty();
    }
}
