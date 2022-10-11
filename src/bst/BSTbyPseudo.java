package bst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

final class INode<Integer> {
    int height;
    int key;
    INode<Integer> l, r;

    public INode(INode<Integer> l, int key, int height, INode<Integer> r){
        this.key = key;
        this.l = l;
        this.r = r;
        this.height = height;
    }

    public INode<Integer> left() {
        return l;
    }
    public INode<Integer> right() {
        return r;
    }

}

public class BSTbyPseudo {
    public static void main(String[] args) throws IOException {

        root = null;
        while(true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer st = new StringTokenizer(br.readLine());
            String order = st.nextToken();
            int key = Integer.parseInt(st.nextToken());

            if (order.equals("i")) {
                root = insertBST(root, key);
            } else {
                // deleteBST()
            }
        }

    }

    protected static INode<Integer> root;

    public void deleteBST(INode<Integer> T, int deleteKey){
        INode<Integer> p =  T; // root, rt == p in psuedo
        INode<Integer> q = null;
        Stack<INode<Integer>> stackQ = new Stack<>();


        // find position of deleteKey while storing parent node on stack
        while(p != null && deleteKey != p.key){
            stackQ.push(q);
            q = p;

            if (deleteKey<p.key){
                p = p.left();
            }else{
                p = p.right();
            }
        }

        if (p == null)  return; // deleteKey was not found

        if (p.left() != null && p.right() != null){
            stackQ.push(p);
            INode<Integer> tempNode = p;

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

    public static INode<Integer> insertBST(INode<Integer> T, int newKey) {
        INode<Integer> p =  T; // root, rt == p in psuedo
        INode<Integer> q = null;
        Stack<INode<Integer>> stackQ = new Stack<>();

        while(p != null){ //  step 1: newKey의 삽입위치q를 찾음.
            if(newKey == p.key) {
                System.out.printf("i %d : The key already exists\n", newKey);
                return T;
            }

            q = p; // 이게 먼저인 이유 : 이후 while(stack is not null) height++; 해줄거라
            stackQ.push(q);

            if(newKey < p.key ){
                p = p.left();
            } else{
                p = p.right();
            }
        }

        INode<Integer> newNode = getBSTNode();
        newNode.key = newKey;

        if(T == null){ // step 3: q의 자식으로 newkey를 삽입.
            T = newNode;
        } else if (newKey< q.key) {
            q.l = newNode;
        } else{
            q.r = newNode;
        }

        // update height while popping parent node from stack
        int size = stackQ.size();

        while(size-- != 0){
            q = stackQ.pop();
            if(q.l == null){
                q.height = 1 + q.r.height;
            } else if (q.r == null) {
                q.height = 1 + q.l.height;
            }else {
                q.height = 1 + Math.max(q.l.height, q.r.height);
            }
        }
        // TODO : (1) q.l.height 가 nullpointexception 일때 예외처리 (2) optional

        inorder(T);
        System.out.println();

        return T;
    }

    private static INode<Integer> getBSTNode() {
        return new INode<Integer>(null, 0, 0,null);
    }

    static public <K> void inorder(INode<K> rt) {
        if(rt == null) {
            // TODO: stdout 말고 스트링 빌더나 버퍼아웃 쓰면 백스페이스 구현가능
            return;
        }
        inorder(rt.left());
        System.out.print(rt.key+ " " ); //visit
        inorder(rt.right());
    }
}
