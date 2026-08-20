class Device {
    private String name;

    public Device(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void runDiagnostic() {
        System.out.println("對設備 " + name + " 執行通用診斷...");
    }
}

class Laptop extends Device {
    public Laptop(String name) {
        super(name);
    }

    @Override
    public void runDiagnostic() {
        System.out.println("[筆記型電腦 " + getName() + "] 檢查電池健康度與 CPU 狀態... 正常。");
    }
}

class Printer extends Device {
    public Printer(String name) {
        super(name);
    }

    @Override
    public void runDiagnostic() {
        System.out.println("[印表機 " + getName() + "] 檢查墨水存量與進紙軌道... 正常。");
    }

    public void cleanPrintHead() {
        System.out.println("[印表機 " + getName() + "] 正在執行印表頭噴嘴清潔...");
    }
}

class Router extends Device {
    public Router(String name) {
        super(name);
    }

    @Override
    public void runDiagnostic() {
        System.out.println("[路由器 " + getName() + "] 檢查網路封包與 Wi-Fi 訊號... 正常。");
    }
}

public class DeviceInspectionSystem {
    public static void main(String[] args) {
        Device[] devices = {
            new Laptop("MacBook Pro"),
            new Printer("Epson L3250"),
            new Router("ASUS RT-AX86U"),
            new Printer("HP LaserJet")
        };

        System.out.println("=== 設備檢測與自動維護流程 ===\n");

        for (Device device : devices) {
            device.runDiagnostic();

            if (device instanceof Printer printer) {
                printer.cleanPrintHead();
            }

            System.out.println("----------------------------------------");
        }
    }
}