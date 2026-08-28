import java.util.ArrayList;
import java.util.List;

class Book {
    String isbn;
    String title;
    String author;
    boolean available;

    public Book(String isbn, String title, String author, boolean available) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = available;
    }

    @Override
    public String toString() {
        return "[ISBN:" + isbn + " | " + title + " | " + author + " | " + (available ? "在館內" : "已借出") + "]";
    }
}

class TreeNode {
    Book book;
    TreeNode left;
    TreeNode right;

    public TreeNode(Book book) {
        this.book = book;
        this.left = null;
        this.right = null;
    }
}

public class LibraryBookBst {

    public static boolean add(TreeNode[] rootWrapper, Book book) {
        if (book == null || book.isbn == null || book.isbn.trim().isEmpty()) {
            return false;
        }

        if (rootWrapper[0] == null) {
            rootWrapper[0] = new TreeNode(book);
            return true;
        }

        TreeNode current = rootWrapper[0];
        TreeNode parent = null;

        while (current != null) {
            parent = current;
            int cmp = book.isbn.compareTo(current.book.isbn);
            if (cmp == 0) {
                return false;
            } else if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        if (book.isbn.compareTo(parent.book.isbn) < 0) {
            parent.left = new TreeNode(book);
        } else {
            parent.right = new TreeNode(book);
        }
        return true;
    }

    public static Book find(TreeNode root, String isbn) {
        if (isbn == null) {
            return null;
        }
        TreeNode current = root;
        while (current != null) {
            int cmp = isbn.compareTo(current.book.isbn);
            if (cmp == 0) {
                return current.book;
            } else if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    public static boolean borrow(TreeNode root, String isbn) {
        Book book = find(root, isbn);
        if (book == null || !book.available) {
            return false;
        }
        book.available = false;
        return true;
    }

    public static boolean returnBook(TreeNode root, String isbn) {
        Book book = find(root, isbn);
        if (book == null || book.available) {
            return false;
        }
        book.available = true;
        return true;
    }

    public static boolean remove(TreeNode[] rootWrapper, String isbn) {
        Book book = find(rootWrapper[0], isbn);
        if (book == null || !book.available) {
            return false;
        }
        rootWrapper[0] = removeHelper(rootWrapper[0], isbn);
        return true;
    }

    private static TreeNode removeHelper(TreeNode root, String isbn) {
        if (root == null) {
            return null;
        }

        int cmp = isbn.compareTo(root.book.isbn);
        if (cmp < 0) {
            root.left = removeHelper(root.left, isbn);
        } else if (cmp > 0) {
            root.right = removeHelper(root.right, isbn);
        } else {
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else {
                TreeNode successor = findMin(root.right);
                root.book = successor.book;
                root.right = removeHelper(root.right, successor.book.isbn);
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

    public static List<Book> rangeQuery(TreeNode root, String minIsbn, String maxIsbn) {
        List<Book> result = new ArrayList<>();
        if (minIsbn == null || maxIsbn == null || minIsbn.compareTo(maxIsbn) > 0) {
            return result;
        }
        rangeHelper(root, minIsbn, maxIsbn, result);
        return result;
    }

    private static void rangeHelper(TreeNode node, String minIsbn, String maxIsbn, List<Book> result) {
        if (node == null) {
            return;
        }

        if (node.book.isbn.compareTo(minIsbn) > 0) {
            rangeHelper(node.left, minIsbn, maxIsbn, result);
        }

        if (node.book.isbn.compareTo(minIsbn) >= 0 && node.book.isbn.compareTo(maxIsbn) <= 0) {
            result.add(node.book);
        }

        if (node.book.isbn.compareTo(maxIsbn) < 0) {
            rangeHelper(node.right, minIsbn, maxIsbn, result);
        }
    }

    public static void printInorderReport(TreeNode root) {
        System.out.println("=== Library Book Inorder Report ===");
        if (root == null) {
            System.out.println("(No books in library)");
        } else {
            inorderHelper(root);
        }
        System.out.println("-----------------------------------\n");
    }

    private static void inorderHelper(TreeNode node) {
        if (node == null) {
            return;
        }
        inorderHelper(node.left);
        System.out.println(node.book);
        inorderHelper(node.right);
    }

    public static void main(String[] args) {
        TreeNode[] rootWrapper = new TreeNode[1];

        System.out.println("=== 1. Add Book Tests ===");
        System.out.println("Add 978-01: " + add(rootWrapper, new Book("978-01", "Java Programming", "Alice", true)));
        System.out.println("Add 978-05: " + add(rootWrapper, new Book("978-05", "Data Structures", "Bob", true)));
        System.out.println("Add 978-03: " + add(rootWrapper, new Book("978-03", "Algorithms", "Charlie", true)));
        System.out.println("Add 978-01 (Duplicate): " + add(rootWrapper, new Book("978-01", "Duplicate Book", "David", true)));
        System.out.println();

        printInorderReport(rootWrapper[0]);

        System.out.println("=== 2. Borrow & Return Tests ===");
        System.out.println("Borrow 978-03: " + borrow(rootWrapper[0], "978-03"));
        System.out.println("Borrow 978-03 (Already borrowed): " + borrow(rootWrapper[0], "978-03"));
        System.out.println("Return 978-03: " + returnBook(rootWrapper[0], "978-03"));
        System.out.println("Return 978-03 (Already returned): " + returnBook(rootWrapper[0], "978-03"));
        System.out.println();

        System.out.println("=== 3. Remove Tests ===");
        borrow(rootWrapper[0], "978-05");
        System.out.println("Remove 978-05 (Borrowed, should fail): " + remove(rootWrapper, "978-05"));
        returnBook(rootWrapper[0], "978-05");
        System.out.println("Remove 978-05 (Returned, should pass): " + remove(rootWrapper, "978-05"));
        System.out.println();

        System.out.println("=== 4. Range Query [978-01 to 978-04] ===");
        List<Book> books = rangeQuery(rootWrapper[0], "978-01", "978-04");
        for (Book b : books) {
            System.out.println(b);
        }
        System.out.println();

        printInorderReport(rootWrapper[0]);
    }
}