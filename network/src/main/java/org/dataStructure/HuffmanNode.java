package org.dataStructure;

import java.util.*;

/**
 * <b>哈夫曼树</b>
 * @author cy
 */
public class HuffmanNode<T>{
   private T data;
   public double weight;
   public int parent;
   public int lChild;
   public int rChild;

    public HuffmanNode() {
        data=null; weight=0.0;
        parent=0;
        lChild=-1; rChild=-1;
    }
    //创建带权重的离散节点，叶子节点
    public HuffmanNode(T t,double w){
        data=t; weight=w;
        parent=0;
        lChild=-1; rChild=-1;
    }

    public T getData(){
        return data;
    }

}

class HuffmanTree<T>{
    private final int maxSize=100;
    public HuffmanNode<T>[] nodes;
    public int length;

    public HuffmanTree() {
        nodes=new HuffmanNode[maxSize];
        length=0;
    }

    public void add(HuffmanNode<T> node){
        nodes[length++]=node;
    }

    public void list(){
        for (int i = 0; i < length; i++) {
            StringBuilder sb=new StringBuilder();
            sb.append(i).append("\t").append(nodes[i].getData()).append("\t").append(nodes[i].weight)
                    .append("\t").append(nodes[i].parent).append("\t").append(nodes[i].lChild).append("\t").append(nodes[i].rChild);
            System.out.println(sb);
        }
    }

    /**
     * 从所有离散节点中选择权重最小的节点
     * @return 权重最小的节点的位置编号，不存在 返回 -1
     */
    private int selectMin(){
        int t=-1;
        //寻找第一个离散的节点
        for (int i = 0; i < this.length; i++) {
            if (nodes[i].parent==0){
                t=i;
                break;
            }
        }
        //寻找权重最小的节点
        for (int i = 0; i < this.length; i++) {
            if (nodes[i].parent==0&&nodes[t].weight>nodes[i].weight){
                t=i;
            }
        }
        if (t!=-1) {
            //将权重最小的离散点双亲设置为-1，表示已选中该点，避免重复选择
            nodes[t].parent=-1;
        }
        return t;
    }

    public void create(){
        int first=-1;
        int second=-1;
        do{
            first=selectMin();
            second=selectMin();
            if (second!=-1){
                double weight=nodes[first].weight+nodes[second].weight;
                HuffmanNode<T> node=new HuffmanNode<>(null,weight);
                node.lChild=first;
                node.rChild=second;
                nodes[length]=node;
                nodes[first].parent=length;
                nodes[second].parent=length;
                length++;
            }
        }while (second!=-1);
    }

    public static void main(String[] args) {
        HuffmanNode<Character> n1=new HuffmanNode<>('C',2);
        HuffmanNode<Character> n2=new HuffmanNode<>('A',4);
        HuffmanNode<Character> n3=new HuffmanNode<>('S',2);
        HuffmanNode<Character> n4=new HuffmanNode<>('T',3);
        HuffmanNode<Character> n5=new HuffmanNode<>(';',3);
        HuffmanTree<Character> huffmanTree=new HuffmanTree<>();
        huffmanTree.add(n1);
        huffmanTree.add(n2);
        huffmanTree.add(n3);
        huffmanTree.add(n4);
        huffmanTree.add(n5);
        huffmanTree.create();
        huffmanTree.list();
    }
}
