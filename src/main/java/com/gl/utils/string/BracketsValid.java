package com.gl.utils.string;

import java.util.HashMap;
import java.util.Stack;

/**
 * @Auther 葛林
 * @Date 2019/1/30/030 14
 * @Remarks 括号验证
 */
public class BracketsValid {

    //负责映射的哈希表。
    private HashMap<Character, Character> mappings;

    // 用映射初始化哈希映射。这只会使代码更容易阅读。
    public BracketsValid() {
        this.mappings = new HashMap<Character, Character>();
        this.mappings.put(')', '(');
        this.mappings.put('}', '{');
        this.mappings.put(']', '[');
    }


    public  boolean isValid(String s) {
        // 初始化要在算法中使用的堆栈。
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // 如果当前字符是右括号。
            if (this.mappings.containsKey(c)) {
                // 获取堆栈的顶部元素。如果堆栈为空，则将虚拟值设置为“”
                char topElement = stack.empty() ? '#' : stack.pop();
                // 如果此括号的映射与堆栈的top元素不匹配，则返回false。
                if (topElement != this.mappings.get(c)) {
                    return false;
                }
            } else {
                //如果它是一个开口支架，将其推到堆栈上。
                stack.push(c);
            }
        }
        // 如果堆栈仍然包含元素，则它是无效的表达式。
        return stack.isEmpty();
    }

}
