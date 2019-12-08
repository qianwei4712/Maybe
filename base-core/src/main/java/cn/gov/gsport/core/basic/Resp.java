package cn.gov.gsport.core.basic;

/**
 * @author shiva   2019/5/18 11:47
 */
public class Resp {

    public static final int success = 0;
    public static final int error = 1;

    public static Resp success() {
        Resp response = new Resp();
        response.code = success;
        return response;
    }

    public static Resp success(String msg, Object data) {
        Resp response = new Resp();
        response.code = success;
        response.msg = msg;
        response.data = data;
        return response;
    }

    public static Resp success(String msg, long count, Object data) {
        Resp response = new Resp();
        response.code = success;
        response.count = count;
        response.msg = msg;
        response.data = data;
        return response;
    }

    public static Resp error() {
        Resp response = new Resp();
        response.code = error;
        return response;
    }

    public static Resp error(String msg, Object data) {
        Resp response = new Resp();
        response.code = error;
        response.msg = msg;
        response.data = data;
        return response;
    }

    /**
     * 状态，0-成功，1-失败
     */
    private int code;

    /**
     * 信息
     */
    private String msg;

    /**
     * 总数
     */
    private  long count;

    /**
     * 数据体
     */
    private  Object data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
