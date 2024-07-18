package cn.com.core.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;

/**
 * @className: FileUtil
 * @author: Met.
 * @date: 2024/3/5
 **/
@Slf4j
public class FileUtil {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @param hash          文件的hash
     * @param multipartFile
     * @param suffix        文件后缀/类型
     * @return {@link String}
     */
    public static String upload(MultipartFile multipartFile, String hash, String suffix) {
        Calendar calendar = Calendar.getInstance();
        String dateFolder = calendar.get(Calendar.YEAR) + "_" + (calendar.get(Calendar.MONTH) + 1) + "_"
                + calendar.get(Calendar.DAY_OF_MONTH) + "/";
        String filePath = "res/" + dateFolder + hash + "." + suffix;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(multipartFile.getBytes());
            fileOutputStream.close();
            return filePath;
        } catch (Exception e) {
            log.debug("--", e);
        }
        return "";
    }

    /**
     * 计算文件大小
     *
     * @param filePath
     * @return {@link String}
     */
    public static String fileSize(String filePath) {
        Path path = Paths.get(filePath);
        Long fileSize = null;
        try {
            fileSize = Files.size(path);
        } catch (IOException e) {
            log.debug("error e:{}", e.getMessage());
        }
        return String.valueOf(fileSize);
    }


}
