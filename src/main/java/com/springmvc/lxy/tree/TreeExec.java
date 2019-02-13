package com.springmvc.lxy.tree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

/**
 * 描述: ${description}
 * <p>
 *
 * @author: harry
 * @date: 2019-01-01
 **/
public class TreeExec {

    private static final Logger log = LoggerFactory.getLogger(TreeExec.class);

    public static void main(String[] args) {

        TreeNode treeNode1 = new TreeNode(1);

        TreeNode treeNode2 = new TreeNode(2);
        TreeNode treeNode3 = new TreeNode(3);
        TreeNode treeNode4 = new TreeNode(4);
        TreeNode treeNode5 = new TreeNode(5);
        TreeNode treeNode6 = new TreeNode(6);
        TreeNode treeNode7 = new TreeNode(7);

        treeNode1.left = treeNode2;
        treeNode1.right = treeNode3;

        treeNode2.left = treeNode4;
        treeNode2.right = treeNode5;

        treeNode3.right = treeNode6;

        treeNode5.right = treeNode7;

//        //先序遍历-递归版
//        log.info("***** 2019-01-01...no1【开始】，先序遍历-递归版");
//        recursionPreorderTraversal(treeNode1);
//        log.info("***** 2018-01-01...no1【结束】，先序遍历-递归版：{}");
//
//        //先序遍历-非递归版
//        log.info("***** 2019-01-01...no2【开始】，先序遍历-非递归版");
//        recursionPreorder(treeNode1);
//        log.info("***** 2019-01-01...no2【结束】，先序遍历-非递归版");
//
//        //中序遍历-递归版
//        log.info("***** 2019-01-01...no3【开始】，中序遍历-递归版");
//        recursionInorderTraversal(treeNode1);
//        log.info("***** 2018-01-01...no3【结束】，中序遍历-递归版：{}");
//
//        //中序遍历-非递归版
//        log.info("***** 2019-01-01...no4【开始】，中序遍历-非递归版");
//        recursionInorder(treeNode1);
//        log.info("***** 2019-01-01...no4【结束】，中序遍历-非递归版");

        //后序遍历-递归版
        log.info("***** 2019-01-01...no5【开始】，后序遍历-递归版");
        recursionPostorderTraversal(treeNode1);
        log.info("***** 2018-01-01...no5【结束】，后序遍历-递归版：{}");

        //后序遍历-非递归版
        log.info("\n***** 2019-01-01...no6【开始】，后序遍历-非递归版");
        recursionPostorder(treeNode1);
        log.info("\n***** 2019-01-01...no6【结束】，后序遍历-非递归版");
    }

    /**
     * 先序遍历-递归版
     * @param root
     */
    public static void recursionPreorderTraversal(TreeNode root) {
        if (root != null) {
            System.out.println(root.val);
            recursionPreorderTraversal(root.left);
            recursionPreorderTraversal(root.right);
        }
    }

    /**
     * 先序遍历-非递归版
     * @param root
     */
    public static void recursionPreorder(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                System.out.println(node.val);
                stack.push(node);
                node = node.left;
            }

            //此时node已经是null了，判断一下stack是不是空的
            if (!stack.isEmpty()) {
                node = stack.pop();
                node = node.right;
            }
        }
    }

    /**
     * 中序遍历-递归版
     * @param root
     */
    public static void recursionInorderTraversal(TreeNode root) {
        if (root != null) {
            recursionInorderTraversal(root.left);
            System.out.println(root.val);
            recursionInorderTraversal(root.right);
        }
    }

    /**
     * 中序遍历-非递归版
     * @param root
     */
    public static void recursionInorder(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (!stack.isEmpty() || node != null) {

            //不管是第一次进来，还是任何时候，把node当成一棵树的根，先一路把left压入栈中
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            //此时node已经是null了，判断一下stack是不是空的
            if (!stack.isEmpty()) {

                //先弹出来最左的，然后访问
                node = stack.pop();
                System.out.println(node.val);

                //1.如果是最左的叶子，那么右肯定是空的。接下来，肯定是把最左的叶子的父亲弹出来了
                //2.如果是父亲被弹出来了，前提：左肯定访问过了，接下来就是访问右边
                node = node.right;
            }
        }
    }


    /**
     * 后序遍历-递归版
     * @param root
     */
    public static void recursionPostorderTraversal(TreeNode root) {
        if (root != null) {
            recursionPostorderTraversal(root.left);
            recursionPostorderTraversal(root.right);
            System.out.println(root.val);
        }
    }

    /**
     * 后序遍历-非递归版
     * @param root
     */
    public static void recursionPostorder(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        TreeNode lastVisit = root;

        while (!stack.isEmpty() || node != null) {

            //不管是第一次进来，还是任何时候，把node当成一棵树的根，先一路把left压入栈中
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.peek();
            if(node.right == null || node.right == lastVisit){
                System.out.println(node.val);
                stack.pop();
                lastVisit = node;
                node = null;
            }else{
                node = node.right;
            }
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
