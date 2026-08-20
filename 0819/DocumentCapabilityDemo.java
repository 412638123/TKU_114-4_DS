interface Exportable {
    void exportFile();
}

interface Compressible {
    void compressFile();
}

class BackupDocument implements Exportable, Compressible {
    private String documentName;

    public BackupDocument(String documentName) {
        this.documentName = documentName;
    }

    @Override
    public void exportFile() {
        System.out.println("匯出文件：" + documentName + ".pdf");
    }

    @Override
    public void compressFile() {
        System.out.println("壓縮文件：" + documentName + ".zip");
    }

    public void showStatus() {
        System.out.println("文件狀態：已就緒 (" + documentName + ")");
    }
}

public class DocumentCapabilityDemo {
    public static void main(String[] args) {
        BackupDocument doc = new BackupDocument("系統備份資料");

        Exportable exporter = doc;
        Compressible compressor = doc;

        System.out.println("=== 1. 使用 Exportable 介面參考 ===");
        exporter.exportFile();

        System.out.println("\n=== 2. 使用 Compressible 介面參考 ===");
        compressor.compressFile();

        System.out.println("\n=== 3. 驗證記憶體位置（是否指向同一物件）===");
        System.out.println("exporter == compressor ? " + (exporter == compressor));
        System.out.println("exporter == doc ? " + (exporter == doc));

        System.out.println("\n=== 4. 介面可見方法限制說明 ===");
        System.out.println("1. exporter 與 compressor 這兩個參考型態，實際上都指向記憶體中的同一物件 (doc)。");
        System.out.println("2. 透過 exporter 只能呼叫 exportFile()，看不到 compressFile() 與 showStatus()。");
        System.out.println("3. 透過 compressor 只能呼叫 compressFile()，看不到 exportFile() 與 showStatus()。");
    }
}
