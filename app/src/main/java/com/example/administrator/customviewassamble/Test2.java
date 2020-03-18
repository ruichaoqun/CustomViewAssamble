package com.example.administrator.customviewassamble;

import java.util.Stack;

public class Test2 {
    public static void main(String[] args){
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.push(6);
        System.out.println("原始栈：" + stack);    // [1, 2, 3, 4, 5]
        reverseStackRecursively(stack);
        System.out.println("逆序后的栈：" + stack); // [5, 4, 3, 2, 1]
    }

    /**
     * 栈中元素逆序
     * @param stack
     */
    public static void reverseStackRecursively(Stack<Integer> stack){
        Stack<Integer> stack1 = new Stack<>();
        while (!stack.isEmpty()){
            stack1.push(stack.pop());
        }
        while (!stack1.isEmpty()){
            stack.push(getBottomElement(stack1));
        }
//        if(stack.isEmpty()){
//            return;
//        }
//        int i = getBottomElement(stack);
//        reverseStackRecursively(stack);
//        stack.push(i);
    }

    /**
     * 获取栈低元素并pop，并且其他元素都还在栈中
     * @param stack
     * @return
     */
    private static int getBottomElement(Stack<Integer> stack) {
       int result = stack.pop();
       if(stack.isEmpty()){
           return result;
       }
       int last = getBottomElement(stack);
       stack.push(result);
       return last;
    }
}
