package cn.shiva.core.basic;

import java.util.List;

/**
 * @author shiva   2019/6/4 19:55
 */
public class Page<T> extends com.github.pagehelper.PageInfo<T>{

    public Page() {
        super();
    }

    public Page(List<T> list) {
        super(list);
    }
}
