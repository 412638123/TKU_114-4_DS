class Member {
    int memberId;
    String name;
    String email;

    public Member(int memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "[ID:" + memberId + " | Name:" + name + " | Email:" + email + "]";
    }
}

class TreeNode {
    Member member;
    TreeNode left;
    TreeNode right;

    public TreeNode(Member member) {
        this.member = member;
        this.left = null;
        this.right = null;
    }
}

public class MemberBstIndex {

    public static boolean add(TreeNode[] rootWrapper, Member member) {
        if (member == null || member.email == null || member.email.trim().isEmpty()) {
            return false;
        }
        if (rootWrapper[0] == null) {
            rootWrapper[0] = new TreeNode(member);
            return true;
        }

        TreeNode current = rootWrapper[0];
        TreeNode parent = null;

        while (current != null) {
            parent = current;
            if (member.memberId == current.member.memberId) {
                return false;
            } else if (member.memberId < current.member.memberId) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        if (member.memberId < parent.member.memberId) {
            parent.left = new TreeNode(member);
        } else {
            parent.right = new TreeNode(member);
        }
        return true;
    }

    public static Member find(TreeNode root, int memberId) {
        TreeNode current = root;
        while (current != null) {
            if (memberId == current.member.memberId) {
                return current.member;
            } else if (memberId < current.member.memberId) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    public static boolean updateEmail(TreeNode root, int memberId, String newEmail) {
        if (newEmail == null || newEmail.trim().isEmpty()) {
            return false;
        }
        Member m = find(root, memberId);
        if (m == null) {
            return false;
        }
        m.email = newEmail.trim();
        return true;
    }

    public static boolean remove(TreeNode[] rootWrapper, int memberId) {
        if (rootWrapper[0] == null || find(rootWrapper[0], memberId) == null) {
            return false;
        }
        rootWrapper[0] = removeHelper(rootWrapper[0], memberId);
        return true;
    }

    private static TreeNode removeHelper(TreeNode root, int memberId) {
        if (root == null) {
            return null;
        }

        if (memberId < root.member.memberId) {
            root.left = removeHelper(root.left, memberId);
        } else if (memberId > root.member.memberId) {
            root.right = removeHelper(root.right, memberId);
        } else {
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else {
                TreeNode successor = findMin(root.right);
                root.member = successor.member;
                root.right = removeHelper(root.right, successor.member.memberId);
            }
        }
        return root;
    }

    private static TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public static void printInorderReport(TreeNode root) {
        System.out.println("=== Member Inorder Report ===");
        if (root == null) {
            System.out.println("(No members found)");
        } else {
            inorderHelper(root);
        }
        System.out.println("-----------------------------\n");
    }

    private static void inorderHelper(TreeNode node) {
        if (node == null) {
            return;
        }
        inorderHelper(node.left);
        System.out.println(node.member);
        inorderHelper(node.right);
    }

    public static void main(String[] args) {
        TreeNode[] rootWrapper = new TreeNode[1];

        System.out.println("=== 1. Add Member Tests ===");
        System.out.println("Add #101 (Alice): " + add(rootWrapper, new Member(101, "Alice", "alice@example.com")));
        System.out.println("Add #105 (Bob): " + add(rootWrapper, new Member(105, "Bob", "bob@example.com")));
        System.out.println("Add #102 (Charlie): " + add(rootWrapper, new Member(102, "Charlie", "charlie@example.com")));
        System.out.println("Add #101 Duplicate: " + add(rootWrapper, new Member(101, "David", "david@example.com")));
        System.out.println("Add Blank Email: " + add(rootWrapper, new Member(103, "Eve", "   ")));
        System.out.println();

        printInorderReport(rootWrapper[0]);

        System.out.println("=== 2. Find Member Tests ===");
        System.out.println("Find #102: " + find(rootWrapper[0], 102));
        System.out.println("Find #999: " + find(rootWrapper[0], 999));
        System.out.println();

        System.out.println("=== 3. Update Email Tests ===");
        System.out.println("Update #102 Email: " + updateEmail(rootWrapper[0], 102, "charlie_new@example.com"));
        System.out.println("Update #102 Email Blank: " + updateEmail(rootWrapper[0], 102, "  "));
        System.out.println("Find #102 After Update: " + find(rootWrapper[0], 102));
        System.out.println();

        System.out.println("=== 4. Remove Member Tests ===");
        System.out.println("Remove #102: " + remove(rootWrapper, 102));
        System.out.println("Remove #999: " + remove(rootWrapper, 999));
        System.out.println();

        printInorderReport(rootWrapper[0]);
    }
}