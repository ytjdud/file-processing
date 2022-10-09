package bst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class BSTbyPseudo< K extends Comparable<K>> {
    public void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        String order = st.nextToken();
        String key = st.nextToken();

        if(order == "i"){
            insertBST(null, (K) key);
        }else{
            // deleteBST()
        }

    }

    protected INode<K> root;

    public void deleteBST(INode<K> T, K deleteKey){
        INode<K> p =  T; // root, rt == p in psuedo
        INode<K> q = null;
        Stack<INode<K>> stackQ = new Stack<>();


        // find position of deleteKey while storing parent node on stack
        while(p != null && deleteKey != p.key){
            stackQ.push(q);
            q = p;

            if (deleteKey.compareTo(p.key) < 0 ){
                p = p.left();
            }else{
                p = p.right();
            }
        }

        if (p == null)  return; // deleteKey was not found

        if (p.left() != null && p.right() != null){
            stackQ.push(p);
            INode<K> tempNode = p;

            if(p.left().height <= p.right().height){
                p = p.right();
                while(p.left() != null){
                    stackQ.push(p);
                    p = p.left();
                }
            }else{
                p = p.left();
                while(p.right() != null){
                    stackQ.push(p);
                    p = p.right();
                }
            }

            tempNode.key = p.key;
            q = stackQ.peek();
        }

        // now degree of p is 0 or 1
        // delete p from T
        if(p.left() == null && p.right()==null ){ // case of degree 0
            if(q ==null){
                root=null; // case of root ?
            }else if(q.left() == p){
                q.l = null;
            }else{
                q.r = null;
            }
        }else{ // case of degree 1
            if (p.l != null ){
                if(q==null){
                    root = root.left(); // case of root
                }else if(q.l == p){
                    q.l = p.l;
                }else{
                    q.r = p.l;
                }
            }else{
                if(q == null){
                    root = root.right(); // case of root
                }else if(q.l == p){
                    q.l = p.r;
                }else{
                    q.r = p.r;
                }
            }
        }

        // delete p; ? 이거 뭐임?

        // update height while popping parent node from stack
        while(stackQ != null){
            q = stackQ.pop();
            q.height = 1 + Math.max(q.left().height, q.right().height);
        }
    }

    public void insertBST(INode<K> T, K newKey) {
        INode<K> p =  T; // root, rt == p in psuedo
        INode<K> q = null;
        Stack<INode<K>> stackQ = new Stack<>();

        while(p != null){ //  step 1: newKey의 삽입위치q를 찾음.
            if(newKey == p.key)   return;

            stackQ.push(q);
            q = p;

            if(newKey.compareTo(p.key) < 0){
                p = p.left();
            } else{
                p = p.right();
            }
        }

        INode<K> newNode = new INode<>(null, newKey, 0, null);

        if(p == null){ // step 3: q의 자식으로 newkey를 삽입.
            p = newNode;
        } else if (newKey.compareTo(q.key) < 0) {
            q.l = newNode;
        } else{
            q.r = newNode;
        }

        while(stackQ != null){
            q.key = stackQ.pop().key;
            q.height = 1 + Math.max(q.left().height, q.right().height);
        }

    }

    public static class INode<K> {
        private int height;
        private K key;
        private INode<K> l, r;

        public INode(INode<K> l, K key, int height, INode<K> r){
            this.key = key;
            this.l = l;
            this.r = r;
            this.height = height;
        }

        public INode<K> left() {
            return l;
        }
        public INode<K> right() {
            return r;
        }
    }
}
