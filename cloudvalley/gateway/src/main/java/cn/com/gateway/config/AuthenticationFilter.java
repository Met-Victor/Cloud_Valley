package cn.com.gateway.config;

import cn.com.core.util.JwtUser;
import cn.com.gateway.redis.RedisUtil;
import com.alibaba.fastjson.JSONObject;
import com.nimbusds.jose.JWSObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;

/**
 * @className: AuthenticationFilter
 * @author: Met.
 * @date: 2024/3/2
 **/

@Component // 不要少了
public class AuthenticationFilter implements GlobalFilter, Ordered {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * removeInput：是退出后，移除问题回复的编辑信息
     */
    private static final String[] white = {
            "/api/", "/oauth/token", "/oauth/login", "/oauth/register", "/post/article/page", "/post/article/selectById",
            "/post/section/selectAll","/post/category/selectAll"
    };

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 定义验证请求头是否带有 Authorization
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 请求对象
        ServerHttpRequest request = exchange.getRequest();
        // 响应对象
        ServerHttpResponse response = exchange.getResponse();
        //
        String path = request.getPath().pathWithinApplication().value();
        logger.debug("path:{}", path);
        net.minidev.json.JSONObject object = new net.minidev.json.JSONObject();
        // 公开api接口进行放行，无需认证
        if ("/post/article/selectById".equals(path) || "/post/article/page".equals(path)) {
            String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            String token = StringUtils.substringAfter(authorization, "Bearer ");
            if (!StringUtils.isNotEmpty(token)) {
                return chain.filter(exchange);
            }
            JWSObject jwsObject = null;
            try {
                jwsObject = JWSObject.parse(token);
            } catch (ParseException e) {
                logger.error("解析令牌失败 {}", e.getMessage());
            }
            net.minidev.json.JSONObject jsonObject = jwsObject.getPayload().toJSONObject();
            object = jsonObject;
            // 如果令牌存在，则通过
            String userInfo = object.get("userInfo").toString();
            exchange.mutate()
                    .request(b -> b.header("userInfo", userInfo)
                    ).build();
            return chain.filter(exchange);
        }
        if (StringUtils.indexOfAny(path, white) != -1) {
            logger.debug("白名单path:{}", path);
            // 直接放行
            return chain.filter(exchange);
        }
        // 请求头信息
        String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = StringUtils.substringAfter(authorization, "Bearer ");
        JwtUser user = null;
        // net.minidev.json.JSONObject object = new net.minidev.json.JSONObject();
        // 响应错误信息
        String message = null;
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            net.minidev.json.JSONObject jsonObject = jwsObject.getPayload().toJSONObject();
            // 校验redis中是否存在对应jti的token
            String jti = null;
            if (jsonObject != null) {
                object = jsonObject;
                jti = jsonObject.get("jti").toString();
            }
            logger.info("jti:" + jti);
            logger.info("json:" + jsonObject);
            // 查询是否存在
            Object value = redisUtil.get(jti);
            if (value == null) {
                logger.info("令牌已过期 {}", token);
                message = "您的身份已过期, 请重新认证!";
            }
        } catch (Exception e) {
            logger.error("解析令牌失败 {}", token);
            message = "无效令牌";
        }

        if (message == null) {
            // 如果令牌存在，则通过
            String userInfo = object.get("userInfo").toString();
            exchange.mutate()
                    .request(b -> b.header("userInfo", userInfo)
                    ).build();
            return chain.filter(exchange);
        }
        // 响应错误提示信息
        net.minidev.json.JSONObject result = new net.minidev.json.JSONObject();
        result.put("code", 401);
        result.put("message", message);

        // 转换响应消息内容对象为字节
        byte[] bits = result.toJSONString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        // 设置响应对象状态码 401
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        // 设置响应对象内容并且指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }


    @Override
    public int getOrder() {
        //过滤器执行顺序，越小越优先执行
        return 0;
    }
}

