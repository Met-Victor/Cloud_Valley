package cn.com.core.util;

/**
 * @className: AuthUtil
 * @author: Met.
 * @date: 2024/3/3
 **/
public class AuthUtil {
    private static final ThreadLocal<JwtUser> userHolder = new ThreadLocal<>();

    public static JwtUser getUserInfo() {
        return userHolder.get();
    }

    public static void setUser(JwtUser user) {
        userHolder.set(user);
    }

    public static void removeUser() {
        userHolder.remove();
    }

}
