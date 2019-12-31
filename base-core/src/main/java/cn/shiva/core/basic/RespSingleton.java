package cn.shiva.core.basic;

/**
 * @author shiva   2019/12/30 19:15
 */
class RespSingleton {

    /**
     * 成功通用返回，单例
     */
    public static final Resp RESP_COMMON_SUCCESS = Resp.success(null, null);
    /**
     * 错误通用返回，单例
     */
    public static final Resp RESP_COMMON_ERROR= Resp.error(null, null);

}
