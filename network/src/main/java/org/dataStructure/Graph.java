package org.dataStructure;

/**
 * @author cy
 */
public class Graph<T> {
    private final int MAXSIZE=10;
    private final int NAX=999;
    private T[] node;
    private int[][] arcs;
    private int e;
    private int n;
    public Graph(){
        node= (T[]) new Object[MAXSIZE];
        arcs=new int[MAXSIZE][MAXSIZE];
    }

    public void createAdj(){

    }

    public int locateVex(T v){
        for (int i = 0; i < n; i++) {
            if (node[i]==v){
                return i;
            }
        }
        return -1;
    }
    public void displayAdjMatrix(){

    }
}
