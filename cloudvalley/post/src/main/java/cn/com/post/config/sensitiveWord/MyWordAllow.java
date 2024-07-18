package cn.com.post.config.sensitiveWord;

import com.github.houbb.heaven.util.io.StreamUtil;
import com.github.houbb.sensitive.word.api.IWordAllow;
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
public class MyWordAllow implements IWordAllow {

    @Override
    public List<String> allow() {
        List<String> list = new ArrayList<>();;
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("myAllowWords.txt");
            if (inputStream != null) {
                list = StreamUtil.readAllLines(inputStream);
            } else {
                log.error("无法找到资源文件 myAllowWords.txt");
            }
        } catch (Exception e) {
            log.error("读取非敏感词文件错误！"+ e.getMessage());
        }
        return list;
    }
}