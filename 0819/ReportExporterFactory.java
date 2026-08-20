interface ReportExporter {
    void export(String title, int[] values);
}

class CsvExporter implements ReportExporter {
    @Override
    public void export(String title, int[] values) {
        System.out.println("=== [CSV 格式報表] ===");
        System.out.println("標題: " + (title == null ? "無標題" : title));
        System.out.print("數據: ");
        if (values == null || values.length == 0) {
            System.out.println("(無數據)");
        } else {
            for (int i = 0; i < values.length; i++) {
                System.out.print(values[i] + (i < values.length - 1 ? "," : ""));
            }
            System.out.println();
        }
    }
}

class JsonExporter implements ReportExporter {
    @Override
    public void export(String title, int[] values) {
        System.out.println("=== [JSON 格式報表] ===");
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"title\": \"").append(title == null ? "無標題" : title).append("\",\n");
        sb.append("  \"values\": ");
        if (values == null) {
            sb.append("null");
        } else {
            sb.append("[");
            for (int i = 0; i < values.length; i++) {
                sb.append(values[i]).append(i < values.length - 1 ? ", " : "");
            }
            sb.append("]");
        }
        sb.append("\n}");
        System.out.println(sb.toString());
    }
}

class TextExporter implements ReportExporter {
    @Override
    public void export(String title, int[] values) {
        System.out.println("=== [TEXT 純文字報表] ===");
        System.out.println("報表名稱：" + (title == null ? "無標題" : title));
        System.out.println("-------------------------");
        if (values == null || values.length == 0) {
            System.out.println("內容：(無資料)");
        } else {
            for (int i = 0; i < values.length; i++) {
                System.out.println("項目 " + (i + 1) + " : " + values[i]);
            }
        }
    }
}

public class ReportExporterFactory {
    public static ReportExporter createExporter(String format) {
        if (format == null) {
            return new TextExporter();
        }
        
        switch (format.trim().toUpperCase()) {
            case "CSV":
                return new CsvExporter();
            case "JSON":
                return new JsonExporter();
            case "TEXT":
            default:
                return new TextExporter();
        }
    }

    public static void exportReport(ReportExporter exporter, String title, int[] values) {
        if (exporter == null) {
            return;
        }
        exporter.export(title, values);
    }

    public static void main(String[] args) {
        int[] sampleData = {100, 250, 300, 450};

        System.out.println("【測試一：CSV 匯出】");
        ReportExporter csvExporter = createExporter("CSV");
        exportReport(csvExporter, "第一季銷售報表", sampleData);

        System.out.println("\n----------------------------------------\n");

        System.out.println("【測試二：JSON 匯出】");
        ReportExporter jsonExporter = createExporter("json");
        exportReport(jsonExporter, "使用者點擊統計", sampleData);

        System.out.println("\n----------------------------------------\n");

        System.out.println("【測試三：不支援的 format (預設回傳 TextExporter)】");
        ReportExporter unknownExporter = createExporter("XML");
        exportReport(unknownExporter, "未知的格式測試", sampleData);

        System.out.println("\n----------------------------------------\n");

        System.out.println("【測試四：邊界條件 (values 為 null)】");
        ReportExporter textExporter = createExporter("TEXT");
        exportReport(textExporter, "空資料報表", null);
    }
}