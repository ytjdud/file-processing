package bst;

import java.io.*;
import java.util.Stack;
import java.util.StringTokenizer;


final class INode<Integer> {
    int height;
    int key;
    INode<Integer> l, r;

    public INode(INode<Integer> l, int key, int height, INode<Integer> r){
        this.l = l;
        this.key = key;
        this.height = height;
        this.r = r;
    }

    public INode<Integer> left() {
        return l;
    }
    public INode<Integer> right() {
        return r;
    }

}

public class BST {
    public static void main(String[] args) throws IOException {

        root = null;

        BufferedReader br = new BufferedReader(
                new FileReader("src/input.txt")
        );
        String str;

        while((str = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(str);

            String order = st.nextToken();
            int key = Integer.parseInt(st.nextToken());

            if (order.equals("i")) {
                root = insertBST(root, key);
                inorder(root);
                System.out.println();
            } else {
                root = deleteBST(root, key);
                inorder(root);
                System.out.println();
            }
        }
    }

    protected static INode<Integer> root;

    public static INode<Integer> deleteBST(INode<Integer> T, int deleteKey){
        INode<Integer> p =  T; // root, rt == p in psuedo
        INode<Integer> q = null;
        Stack<INode<Integer>> stackQ = new Stack<>();
        // find
        while(p != null && deleteKey != p.key){

            q = p;
            stackQ.push(q);

            if (deleteKey<p.key){
                p = p.left();
            }else{
                p = p.right();
            }
        } // delete 할 대상까지 p q 내려감

        if (p == null)  {
            System.out.printf("d %d : The key does not exist\n", deleteKey);
            return T; // (1) deleteKey was not found
        }

        // delete p from T
        if( p.l == null && p.r == null ){ // case of degree 0
            if(q == null){ // (2) 루트 노드만 있는 경우, 루트를 삭제
                T = null;
            }else if(q.l == p){ // (3)
                q.l = null;
            }else{ // (3)
                q.r = null;
            }
        }else if (p.l == null || p.r == null) { // (4) case of degree 1
            if (p.l != null ){ // p.l 에 자식이 있을때
                if(q==null){
                    T = T.l; // case of root
                }else if(q.l == p){
                    q.l = p.l;
                }else{
                    q.r = p.l;
                }
            }else{ // p.r != null
                if(q == null){
                    T = T.r; // case of root
                }else if(q.l == p){
                    q.l = p.r;
                }else{
                    q.r = p.r;
                }
            }
        } else if (p.l != null && p.r != null){ // 삭제할 노드의 차수가 2인 경우. 왼오 모두 자식을 가지고 있을 경우

            INode<Integer> r = null;
            // height() : 본인을 제외하고 서브트리의 높이
            // noNode() : 본인(파라미터)을 포함하는 노드의 개수

            if ( p.l.height > p.r.height ) {
                r = maxNode(p, q, stackQ);
                p.key = r.key;
                p.l = deleteBST(p.left(), r.key);
            }else if ( p.l.height < p.r.height ){
                r = minNode(p, q, stackQ);
                p.key = r.key;
                p.r = deleteBST(p.right(), r.key);
            }else{ // p.l.height == p.r.height
                if( noNodes(p.left()) >= noNodes(p.right()) ){
                    r = maxNode(p, q, stackQ);
                    p.key = r.key;
                    p.l = deleteBST(p.left(), r.key);
                }else{
                    r = minNode(p, q, stackQ);
                    p.key = r.key;
                    p.r = deleteBST(p.right(), r.key);
                }
            }
        }
        // 여기서 계속 내려가야함 -> p q stack

        int size = stackQ.size();

        while(size-- != 0){
            q = stackQ.pop();
            if(q.l == null && q.r == null ){
                q.height = 0;
            } else if(q.l == null){
                q.height = 1 + q.r.height;
            } else if (q.r == null) {
                q.height = 1 + q.l.height;
            }else {
                q.height = 1 + Math.max(q.l.height, q.r.height);
            }
        }

        // TODO : (1) q.l.height 가 nullpointexception 일때 예외처리 (2) optional



        return T;
    }

    private static int noNodes(INode<Integer> rt) {
        // 재귀로 풀면 되는거 아님?
        // 아 inorder 에 프린트 대신 cnt 하면 되는 거 아님?
        int cnt =0;

        if(rt == null) {
            return 0;
        }
        inorder(rt.left());
        cnt++; //visit
        inorder(rt.right());

        return 0;
    }

    private static INode<Integer> maxNode(INode<Integer> p, INode<Integer> q, Stack<INode<Integer>> stackQ) {
        q = p;
        stackQ.push(q);

        p = p.left();
        while(p.right() != null){
            stackQ.push(p);
            p = p.right();
        }
        return p;
    }

    private static INode<Integer> minNode(INode<Integer> p, INode<Integer> q, Stack<INode<Integer>> stackQ) {
        q = p;
        stackQ.push(q);

        p = p.right();
        while(p.left() != null){
            q = p;
            stackQ.push(p);
            p = p.left();
        }
        return p;
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
        // TODO : (1) q.l.height 가 nullpointexception 일때 예외처리 (2) optional (null 검사 안하기 <- 효율성 떨어짐)

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
