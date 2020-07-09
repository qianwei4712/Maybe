package cn.shiva.core.basic;

/**
 * @author shiva   2019/5/18 11:47
 */
public class Resp {

    public static final int SUCCESS = 0;
    public static final int ERROR = 1;

    public static Resp success() {
        return RespSingleton.RESP_COMMON_SUCCESS;
    }

    public static Resp success(String msg, Object data) {
        Resp response = new Resp();
        response.code = SUCCESS;
        response.msg = msg;
        response.data = data;
        return response;
    }

    public static Resp success(String msg, long count, Object data) {
        Resp response = new Resp();
        response.code = SUCCESS;
        response.count = count;
        response.msg = msg;
        response.data = data;
        return response;
    }

    public static Resp error() {
        return RespSingleton.RESP_COMMON_ERROR;
    }

    public static Resp error(String msg, Object data) {
        Resp response = new Resp();
        response.code = ERROR;
        response.msg = msg;
        response.data = data;
        return response;
    }

    public static Resp error(String msg) {
        Resp response = new Resp();
        response.code = ERROR;
        response.msg = msg;
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
