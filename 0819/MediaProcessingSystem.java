abstract class MediaFile {
    private String fileName;
    private double fileSizeMB;

    public MediaFile(String fileName, double fileSizeMB) {
        this.fileName = fileName;
        this.fileSizeMB = fileSizeMB < 0 ? 0 : fileSizeMB;
    }

    public String getFileName() {
        return fileName;
    }

    public double getFileSizeMB() {
        return fileSizeMB;
    }

    public abstract String getFormatDetails();

    @Override
    public String toString() {
        return "檔案名稱: " + fileName + " (" + fileSizeMB + " MB) | 格式特徵: " + getFormatDetails();
    }
}

interface Playable {
    void play();
}

interface Compressible {
    void compress();
}

class ImageFile extends MediaFile implements Compressible {
    private int width;
    private int height;

    public ImageFile(String fileName, double fileSizeMB, int width, int height) {
        super(fileName, fileSizeMB);
        this.width = width < 0 ? 0 : width;
        this.height = height < 0 ? 0 : height;
    }

    @Override
    public String getFormatDetails() {
        return "圖片解析度 " + width + "x" + height;
    }

    @Override
    public void compress() {
        System.out.println("[壓縮圖片] 正在對 " + getFileName() + " 進行 JPEG/PNG 無損壓縮...");
    }
}

class AudioFile extends MediaFile implements Playable, Compressible {
    private int durationSeconds;

    public AudioFile(String fileName, double fileSizeMB, int durationSeconds) {
        super(fileName, fileSizeMB);
        this.durationSeconds = durationSeconds < 0 ? 0 : durationSeconds;
    }

    @Override
    public String getFormatDetails() {
        return "音訊長度 " + durationSeconds + " 秒";
    }

    @Override
    public void play() {
        System.out.println("[播放音訊] 正在播放音樂檔 " + getFileName() + "...");
    }

    @Override
    public void compress() {
        System.out.println("[壓縮音訊] 正在對 " + getFileName() + " 進行 MP3 碼率壓縮...");
    }
}

class VideoFile extends MediaFile implements Playable, Compressible {
    private int durationSeconds;
    private String resolution;

    public VideoFile(String fileName, double fileSizeMB, int durationSeconds, String resolution) {
        super(fileName, fileSizeMB);
        this.durationSeconds = durationSeconds < 0 ? 0 : durationSeconds;
        this.resolution = resolution;
    }

    @Override
    public String getFormatDetails() {
        return "影片長度 " + durationSeconds + " 秒, 解析度 " + resolution;
    }

    @Override
    public void play() {
        System.out.println("[播放影片] 正在解碼並播放影片 " + getFileName() + "...");
    }

    @Override
    public void compress() {
        System.out.println("[壓縮影片] 正在對 " + getFileName() + " 進行 H.264/H.265 轉碼壓縮...");
    }
}

public class MediaProcessingSystem {
    public static void main(String[] args) {
        MediaFile[] files = {
            new ImageFile("photo.png", 5.2, 1920, 1080),
            new AudioFile("song.mp3", 8.5, 210),
            new VideoFile("movie.mp4", 1250.0, 7200, "1080p")
        };

        System.out.println("=== 媒體檔案處理系統測試 ===\n");

        for (MediaFile file : files) {
            System.out.println("----------------------------------------");
            System.out.println(file);

            if (file instanceof Playable playable) {
                playable.play();
            } else {
                System.out.println("[系統訊息] 此檔案不支援播放功能。");
            }

            if (file instanceof Compressible compressible) {
                compressible.compress();
            } else {
                System.out.println("[系統訊息] 此檔案不支援壓縮功能。");
            }
        }
        System.out.println("----------------------------------------");
    }
}