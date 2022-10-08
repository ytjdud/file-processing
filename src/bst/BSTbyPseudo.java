package bst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class BSTforPseudo< K extends Comparable<K>, E > {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        String order = st.nextToken();
        int key = Integer.parseInt(st.nextToken());



        if(order == "i"){
            // insertBST()
        }else{
            // deleteBST()
        }

    }

    public void insertBST(K T, E newKey) {
        K p =  T;
        K q = null;
        Stack<K> stackQ = new Stack<>();


        while(p != null){
            if(newKey = p.key)   return;
            else{
                p = p.right();
            }
        }

        newNode = getBSTNode();
        newNode.key = newKey;

        if(T == null){
            T = newNode;
        } else if (newKey < q. key) {
            q.left = newNode;
        } else{
            q.right() = newNode;
        }

        while(stackQ != null){
            q = stackQ.pop();
            q.height = 1 + max(q.left.height, q.right.height);
        }
    }
}
