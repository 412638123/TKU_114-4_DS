import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LoginActivityReport {

    public static class LoginLog {
        private String account;
        private String ip;

        public LoginLog(String account, String ip) {
            this.account = account;
            this.ip = ip;
        }

        public String getAccount() {
            return account;
        }

        public String getIp() {
            return ip;
        }
    }

    public static void generateReport(List<LoginLog> logs, int anomalyThreshold) {
        if (logs == null || logs.isEmpty()) {
            System.out.println("No login records available.");
            return;
        }

        Map<String, Integer> loginCounts = new HashMap<>();
        Map<String, Set<String>> userIPs = new HashMap<>();

        for (LoginLog log : logs) {
            if (log == null || log.getAccount() == null || log.getIp() == null) {
                continue;
            }
            String account = log.getAccount();
            String ip = log.getIp();

            loginCounts.put(account, loginCounts.getOrDefault(account, 0) + 1);

            userIPs.putIfAbsent(account, new HashSet<>());
            userIPs.get(account).add(ip);
        }

        System.out.println("=== Overall Login Activity Report ===");
        List<String> accounts = new ArrayList<>(loginCounts.keySet());
        Collections.sort(accounts);

        for (String acc : accounts) {
            int count = loginCounts.get(acc);
            int uniqueIpCount = userIPs.get(acc).size();
            System.out.println("Account: " + acc + " | Logins: " + count + " | Unique IPs: " + uniqueIpCount);
        }

        System.out.println("\n=== Anomaly Report (Logins >= " + anomalyThreshold + ") ===");
        boolean foundAnomaly = false;
        for (String acc : accounts) {
            int count = loginCounts.get(acc);
            if (count >= anomalyThreshold) {
                foundAnomaly = true;
                System.out.println("[ALERT] Suspicious Account: " + acc + " (Total Logins: " + count + ", Unique IPs: " + userIPs.get(acc).size() + ")");
            }
        }

        if (!foundAnomaly) {
            System.out.println("No suspicious accounts detected.");
        }
    }

    public static void main(String[] args) {
        List<LoginLog> logs = List.of(
            new LoginLog("user_alice", "192.168.1.1"),
            new LoginLog("user_bob", "192.168.1.2"),
            new LoginLog("user_alice", "192.168.1.1"),
            new LoginLog("user_charlie", "10.0.0.1"),
            new LoginLog("user_alice", "192.168.1.5"),
            new LoginLog("user_bob", "192.168.1.2"),
            new LoginLog("user_alice", "192.168.1.1"),
            new LoginLog("user_charlie", "10.0.0.2"),
            new LoginLog("user_alice", "192.168.1.1")
        );

        generateReport(logs, 4);
    }
}