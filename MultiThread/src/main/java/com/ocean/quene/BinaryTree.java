package com.ocean.quene;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {
    private BinaryTree leftNode;//左节点
    private BinaryTree rightNode;//右节点
    private Object value;//当前节点的数值
//    二叉树内容插入
    private void add(Object v){
        if(this.value == null){
            this.value = v;
            return;
        }
        if((Integer)this.value > (Integer)v){
//            处理左节点
            if(this.leftNode == null){
                this.leftNode = new BinaryTree();
                this.leftNode.value = v;
            }else{
                this.leftNode.add(v);
            }
        }else{
//            处理右节点
            if(this.rightNode == null){
                this.rightNode = new BinaryTree();
                this.rightNode.value = v;
            }else{
                this.rightNode.add(v);
            }
        }
    }

//    中序节点遍历
    // 中序遍历所有的节点
    public List<Object> values() {
        List<Object> values = new ArrayList<>();

        // 左节点的遍历结果
        if (null != leftNode)
            values.addAll(leftNode.values());

        // 当前节点
        values.add(value);

        // 右节点的遍历结果
        if (null != rightNode)

            values.addAll(rightNode.values());

        return values;
    }
    public static void main(String[] args) {
        int[] nums = new int[]{67, 7, 30, 73, 10, 0, 78, 81, 10, 74 };
        BinaryTree node = new BinaryTree();
        for(int i = 0;i<nums.length;i++){
            node.add(nums[i]);
        }

        System.out.println(node.values());
    }
}
