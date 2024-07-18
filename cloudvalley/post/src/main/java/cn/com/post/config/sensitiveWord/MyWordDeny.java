package cn.com.post.config.sensitiveWord;

import com.github.houbb.heaven.util.io.StreamUtil;
import com.github.houbb.sensitive.word.api.IWordDeny;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author suwenjian
 * @Date 2024/4/16
 */
@Component
@Slf4j
public class MyWordDeny implements IWordDeny {

    @Override
    public List<String> deny() {
        List<String> list = new ArrayList<String>();;
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("mySensitiveWords.txt");
            if (inputStream != null) {
                list = StreamUtil.readAllLines(inputStream);
            } else {
                log.error("无法找到资源文件 mySensitiveWords.txt");
            }
        } catch (Exception e) {
            log.error("读取敏感词文件错误！"+ e.getMessage());
        }
        return list;
    }
}
