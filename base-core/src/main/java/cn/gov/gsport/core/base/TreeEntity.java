package cn.gov.gsport.core.base;

/**
 * @author shiva   2019/8/11 14:41
 */
public class TreeEntity<T> extends BaseEntity<T> {

    private T parent;

    /**
     * 上级id
     */
    private Long pid;

    /**
     * 上级id字符串，","分割
     */
    private String pids;


    public T getParent() {
        return parent;
    }

    public void setParent(T parent) {
        this.parent = parent;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPids() {
        return pids;
    }

    public void setPids(String pids) {
        this.pids = pids;
    }
}
