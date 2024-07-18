package cn.com.post.config.sensitiveWord;

import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.github.houbb.sensitive.word.support.allow.WordAllows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author suwenjian
 * @Date 2024/4/16
 */
@Configuration
public class SensitiveWordConfig {

    @Autowired
    private MyWordAllow myWordAllow;

    @Autowired
    private MyWordDeny myWordDeny;

    @Bean
    public SensitiveWordBs sensitiveWordBs() {
        return SensitiveWordBs.newInstance()
                // 是否启用数字检测
                .enableNumCheck(false)
                // 是否启用邮箱检测
                .enableEmailCheck(false)
                // 是否启用链接检测
                .enableUrlCheck(false)
                // 配置 默认非敏感词 + 自定义非敏感词
                .wordAllow(WordAllows.chains(WordAllows.defaults(), myWordAllow))
                // 配置 自定义敏感词
                .wordDeny(myWordDeny)
                // 各种其他配置
                .init();
    }
}
