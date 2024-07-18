package cn.com.post.util.wordutil;

import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author suwenjian
 * @Date 2024/4/16
 */
@Component
public class SensitiveWordUtil {

    @Autowired
    private SensitiveWordBs sensitiveWordBs;

    // 当词库的信息发生变化之后，需要先调用此方法更新词库
    public void refresh(){
        sensitiveWordBs.init();
    }

    // 判断是否含有敏感词
    public boolean contains(String text){
        return sensitiveWordBs.contains(text);
    }

    // 使用默认替换符 * 进行替换敏感词
    public String replace(String text){
        return sensitiveWordBs.replace(text);
    }

    // 返回所有敏感词
    public List<String> findAll(String text){
        return sensitiveWordBs.findAll(text);
    }

}
