import java.util.Objects;

class LibraryMember {
    private String memberId;
    private String name;
    private String email;

    public LibraryMember(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "會員編號：" + memberId + " | 姓名：" + name + " | Email：" + email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LibraryMember other = (LibraryMember) obj;
        return Objects.equals(memberId, other.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }
}

public class MemberEqualityPractice {
    public static void main(String[] args) {
        LibraryMember member1 = new LibraryMember("M001", "王小明", "ming@email.com");
        LibraryMember member2 = new LibraryMember("M001", "王小明", "ming_new@email.com");

        System.out.println("【會員一】" + member1);
        System.out.println("【會員二】" + member2);

        System.out.println("\n== 比較結果：" + (member1 == member2));
        System.out.println("equals() 比較結果：" + member1.equals(member2));

        System.out.println("\n與 null 比較結果：" + member1.equals(null));
    }
}