/***********************************************************************
 * $Id: BaseController.java,v1.0 Mar 1, 2019 9:54:13 AM $
 *
 * @author: Victor
 *
 * (c)Copyright 2011 Victor All rights reserved.
 ***********************************************************************/
package cn.com.core;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 基本控制器
 *
 * @author 13016
 * @date 2023/08/29
 */
public abstract class BaseController {
    /**
     * 日志对象, 共享给由本类派生的对象
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected <T> T updateObjectFromJson(@NonNull T data, @NonNull JSONObject json) {
        Method[] ms = data.getClass().getMethods();
        for (Method m : ms) {
            String name = m.getName();
            if (!name.startsWith("set")) {
                continue;
            }
            String key = name.substring(3, 4).toLowerCase() + name.substring(4);
            if (!json.containsKey(key)) {
                continue;
            }
            Class<?> c = m.getParameterTypes()[0];
            try {
                if (c.equals(Long.class) || c.equals(long.class)) {
                    m.invoke(data, json.getLong(key));
                } else if (c.equals(Integer.class) || c.equals(int.class)) {
                    m.invoke(data, json.getInteger(key));
                } else if (c.equals(Float.class) || c.equals(float.class)) {
                    m.invoke(data, json.getFloat(key));
                } else if (c.equals(String.class)) {
                    m.invoke(data, json.getString(key));
                } else if (c.equals(Date.class)) {
                    Date d = new Date(json.getDate(key).getTime());
                    m.invoke(data, d);
                } else if (c.equals(Timestamp.class)) {
                    try {
                        m.invoke(data, new Timestamp(json.getLongValue(key)));
                    } catch (Exception e) {
                        m.invoke(data, Timestamp.valueOf(json.getString(key)));
                    }
                } else if (c.equals(JSONObject.class)) {
                    m.invoke(data, json.getJSONObject(key));
                } else if (c.equals(JSONArray.class)) {
                    m.invoke(data, json.getJSONObject(key));
                } else if (c.isArray()) {
                    if (c.getComponentType().equals(byte.class)) {
                        m.invoke(data, json.get(key));
                    } else {
                        m.invoke(data, json.get(key));
                    }
                } else {
                    m.invoke(data, json.get(key));
                }
            } catch (ClassCastException e) {
                // 捕获JSONArray.class里面的异常
                if (c.equals(JSONArray.class)) {
                    try {
                        m.invoke(data, json.getJSONArray(key));
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } catch (Throwable e) {
                logger.warn("{}/{}", name, c.isArray(), e);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("updateObjectFromJson.json={}", json);
            logger.debug("updateObjectFromJson.obj={}", data);
        }
        return data;
    }

    protected Map<String, String> parseXml(String xml) {
        StringReader sr = null;
        Map<String, String> data = new HashMap<>();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            sr = new StringReader(xml);
            InputSource is = new InputSource(sr);
            Document document = db.parse(is);
            sr.close();
            Element root = document.getDocumentElement();
            doNode(root, data);
        } catch (Exception e) {
            logger.info("parseXml:{}", xml);
            logger.error(e.getMessage(), e);
        } finally {
            if (sr != null) {
                sr.close();
            }
        }
        return data;
    }

    private void doNode(@NonNull Node root, @NonNull Map<String, String> data) {
        NodeList list = root.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            if (node.getChildNodes().getLength() > 1) {
                doNode(node, data);
            } else {
                data.put(node.getNodeName(), node.getTextContent());
            }
        }
    }

    /**
     * 效果与 writeSuccess 一样
     *
     * @param obj 响应内容
     * @return JSON 字符串
     * @see #writeSuccess(Object)
     */
    protected String writeJavaObject(Object obj) {
        return writeSuccess(JSON.toJSON(obj));
    }

    /**
     * 效果与 writeSuccess 一样
     *
     * @param data 响应内容
     * @return JSON 字符串
     * @see #writeSuccess(Object)
     */
    protected String write(List<?> data) {
//        if (data.isEmpty()) {
//            return writeError("找不到数据!");
//        }
        return writeSuccess(data instanceof JSONObject ? data : JSON.toJSON(data));
    }


    /**
     * 当 data 为空时, 效果同等与 writeMsg, 否则效果同等于 writeSuccess
     *
     * @param data 响应数据
     * @param msg  响应消息
     * @return JSON 字符串
     */
    protected String writeIfEmpty(List<?> data, String msg) {
        if (data.isEmpty()) {
            return writeMsg(msg);
        }
        return writeSuccess(JSON.toJSON(data));
    }

    /**
     * 效果与 writeSuccess 一样
     *
     * @param data 响应内容
     * @return JSON 字符串
     * @see #writeSuccess(Object)
     */
    protected String write(Object data) {
        return writeSuccess(JSON.toJSON(data));
    }

    /**
     * 生成一个只有 code 与 msg 的 JSON 字符串
     *
     * @param code 响应代码
     * @param msg  响应信息
     * @return JSON 字符串
     */
    protected String write(int code, String msg) {
        return write(msg, code, "");
    }

    /**
     * 效果与 writeSuccess 一样
     *
     * @param data 响应内容
     * @return JSON 字符串
     * @see #writeSuccess(Object)
     */
    protected String write(Map<?, ?> data) {
        return writeSuccess(JSON.toJSON(data));
    }

    /**
     * 效果与 writeSuccess 一样
     *
     * @param data 响应内容
     * @return JSON 字符串
     * @see #writeSuccess(Object)
     */
    protected String write(JSONObject data) {
        return writeSuccess(data);
    }

    /**
     * 与 writeSuccess 意义一样
     *
     * @param msg 响应消息
     * @return JSON 字符串
     * @see #writeSuccess(Object)
     */
    protected String writeMsg(String msg) {
        return writeSuccess(msg);
    }

    /**
     * 生成一个 code 为 500 的 JSON 字符串
     *
     * @param msg 响应消息
     * @return JSON 字符串
     */
    protected String writeError(String msg) {
        return write(msg, 500, Collections.emptyList());
    }

    /**
     * 生成一个 code 为 500, msg 为 异常类名, data 为 此 throwable 的详细消息字符串 的 JSON 字符串
     *
     * @param e 异常对象
     * @return JSON 字符串
     */
    protected String writeError(Throwable e) {
        logger.error("InternalError: ", e);
        return write(e.getClass().getName(), 500, e.getMessage());
    }

    /**
     * 生成一个 code 为 500, data 为 空字符串 的 JSON 字符串
     *
     * @param msg 响应消息
     * @return JSON 字符串
     */
    protected String writeInternalError(String msg) {
        return write(msg, 500, "");
    }

    /**
     * 生成一个 code 为 0, msg 为 success 的 JSON 字符串
     *
     * @param data 响应内容
     * @return JSON 字符串
     */
    protected String writeSuccess(@Nullable Object data) {
        return writeSuccess(data, null);
    }

    protected String writeSuccess(@Nullable Object data, @Nullable Map<String, Object> others) {
        return write("success", 0, data, others);
    }

    protected String writeSuccess(List<?> data, @Nullable Map<String, Object> others) {
        return write("success", 0, data instanceof JSONObject ? data : JSON.toJSON(data), others);
    }

    protected String writeEmptyList(@NonNull String msg) {
        return write(msg, 0, Collections.emptyList());
    }

    /**
     * 使用指定数据创建一个 JSON 字符串, 用于发送响应
     *
     * @param msg  响应消息
     * @param code 响应代码
     * @param data 响应数据
     * @return JSON 字符串
     */
    protected String write(String msg, int code, Object data) {
        return write(msg, code, data, null);
    }

    protected String write(@Nullable String msg, int code, @Nullable Object data, @Nullable Map<String, Object> others) {
        JSONObject res = new JSONObject();
        res.put("code", code);
        res.put("msg", msg);
        res.put("data", data);
        if (others != null) {
            res.putAll(others);
        }
        String json = res.toJSONString();
        // 日志切片
        if (logger.isDebugEnabled()) {
            String to = json;
            if (to.length() > 256) {
                to = to.substring(0, 256);
            }
            logger.debug("write: {}", to);
        }
        return json;
    }
}
