class Node {
    char val;
    Node left;
    Node right;

    public Node(char val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

public class ThreeTraversalPractice {

    public static void preorder(Node root) {
        if (root == null) {
            return;
        }
        System.out.print(root.val + " ");
        preorder(root.left);
        preorder(root.right);
    }

    public static void inorder(Node root) {
        if (root == null) {
            return;
        }
        inorder(root.left);
        System.out.print(root.val + " ");
        inorder(root.right);
    }

    public static void postorder(Node root) {
        if (root == null) {
            return;
        }
        postorder(root.left);
        postorder(root.right);
        System.out.print(root.val + " ");
    }

    public static void main(String[] args) {
        Node root = new Node('M');
        root.left = new Node('F');
        root.left.left = new Node('B');
        root.right = new Node('T');
        root.right.left = new Node('R');
        root.right.right = new Node('Z');

        System.out.print("Preorder: ");
        preorder(root);
        System.out.println();

        System.out.print("Inorder: ");
        inorder(root);
        System.out.println();

        System.out.print("Postorder: ");
        postorder(root);
        System.out.println();
    }
}