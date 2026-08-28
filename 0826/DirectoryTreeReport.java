import java.util.ArrayList;
import java.util.List;

class FileNode {
    String name;
    boolean isDirectory;
    long size;
    List<FileNode> children;

    public FileNode(String name, boolean isDirectory, long size) {
        this.name = name;
        this.isDirectory = isDirectory;
        this.size = size;
        this.children = isDirectory ? new ArrayList<>() : null;
    }

    public void addChild(FileNode child) {
        if (this.isDirectory && child != null) {
            this.children.add(child);
        }
    }
}

public class DirectoryTreeReport {

    private static int totalNodeCount = 0;
    private static int fileCount = 0;
    private static int directoryCount = 0;
    private static FileNode maxFile = null;

    public static long calculateSizesAndCollectStats(FileNode node) {
        if (node == null) {
            return 0;
        }

        totalNodeCount++;

        if (!node.isDirectory) {
            fileCount++;
            if (maxFile == null || node.size > maxFile.size) {
                maxFile = node;
            }
            return node.size;
        }

        directoryCount++;
        long totalDirSize = 0;

        for (FileNode child : node.children) {
            totalDirSize += calculateSizesAndCollectStats(child);
        }

        node.size = totalDirSize;
        System.out.println("[Postorder Directory Size] " + node.name + "/ -> " + node.size + " bytes");
        return node.size;
    }

    public static int getHeight(FileNode node) {
        if (node == null) {
            return -1;
        }
        if (!node.isDirectory || node.children.isEmpty()) {
            return 0;
        }

        int maxChildHeight = 0;
        for (FileNode child : node.children) {
            maxChildHeight = Math.max(maxChildHeight, getHeight(child));
        }
        return 1 + maxChildHeight;
    }

    public static void printReport(FileNode root) {
        totalNodeCount = 0;
        fileCount = 0;
        directoryCount = 0;
        maxFile = null;

        System.out.println("=== Postorder Directory Size Calculation ===");
        long totalSize = calculateSizesAndCollectStats(root);
        int height = getHeight(root);

        System.out.println("\n=== Directory Tree Statistics Report ===");
        System.out.println("Root Directory      : " + (root != null ? root.name : "null"));
        System.out.println("Total Storage Size  : " + totalSize + " bytes");
        System.out.println("Total Nodes         : " + totalNodeCount);
        System.out.println("Directory Count     : " + directoryCount);
        System.out.println("File Count          : " + fileCount);
        System.out.println("Tree Height         : " + height);
        System.out.println("Largest File        : " + (maxFile != null ? maxFile.name + " (" + maxFile.size + " bytes)" : "None"));
        System.out.println("----------------------------------------\n");
    }

    public static void main(String[] args) {
        FileNode root = new FileNode("root", true, 0);

        FileNode docs = new FileNode("documents", true, 0);
        FileNode media = new FileNode("media", true, 0);
        FileNode readme = new FileNode("README.md", false, 1500);

        root.addChild(docs);
        root.addChild(media);
        root.addChild(readme);

        FileNode pdf1 = new FileNode("report.pdf", false, 450000);
        FileNode txt1 = new FileNode("notes.txt", false, 2300);
        docs.addChild(pdf1);
        docs.addChild(txt1);

        FileNode images = new FileNode("images", true, 0);
        FileNode video = new FileNode("intro.mp4", false, 12500000);
        media.addChild(images);
        media.addChild(video);

        FileNode img1 = new FileNode("logo.png", false, 85000);
        FileNode img2 = new FileNode("banner.jpg", false, 340000);
        images.addChild(img1);
        images.addChild(img2);

        printReport(root);
    }
}