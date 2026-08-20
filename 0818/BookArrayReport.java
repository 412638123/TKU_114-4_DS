class Book {
    private String id;
    private String title;
    private double price;
    private int stock;

    public Book(String id, String title, double price, int stock) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    @Override
    public String toString() {
        return "書號：" + id + " | 書名：" + title + " | 單價：" + price + " | 庫存：" + stock;
    }
}

public class BookArrayReport {
    public static void main(String[] args) {
        Book[] books = {
            new Book("B001", "Java 程式設計入門", 580, 5),
            new Book("B002", "資料結構實戰指南", 650, 2),
            new Book("B003", "演算法圖解經典", 720, 8),
            new Book("B004", "物件導向架構分析", 490, 1)
        };

        System.out.println("【所有書籍清單】");
        for (Book book : books) {
            System.out.println(book);
        }

        double totalValue = 0;
        for (Book book : books) {
            totalValue += book.getPrice() * book.getStock();
        }
        System.out.println("\n【庫存總價值】\n" + totalValue + " 元");

        Book mostExpensive = books[0];
        for (int i = 1; i < books.length; i++) {
            if (books[i].getPrice() > mostExpensive.getPrice()) {
                mostExpensive = books[i];
            }
        }
        System.out.println("\n【價格最高的書籍】\n" + mostExpensive);

        System.out.println("\n【庫存 <= 3 的書籍】");
        for (Book book : books) {
            if (book.getStock() <= 3) {
                System.out.println(book);
            }
        }
    }
}