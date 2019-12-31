package cn.shiva.common.others;

import cn.shiva.common.lang.StringUtils;
import cn.shiva.core.constant.BaseConstant;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 项目中涉及到的公用方法
 * @author shiva   2019/12/07 13:56
 */
public class CommonUtils {

    /**
     * MD5密码盐的后缀
     */
    private static final String SALT_FIX = ".salt";

    /**
     * 明文账号密码加密加盐<br/>
     * 盐为 username.salt
     * @return 32位hash密码
     */
    public static String md5HashWithusername(String username, String password){
        try {
            //MD5的盐默认为username.salt
            String salt= username + SALT_FIX;
            //加密（md5+盐），返回一个32位的字符串小写
            return new Md5Hash(password, salt).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 去除拼接字符串最后的‘,’
     */
    public static String subEndComma(String str){
        if (StringUtils.isBlank(str)){
            return str;
        }
        while (str.endsWith(BaseConstant.SPLIT_SYMBOL)){
            str = str.substring(0, str.length()-1);
        }
        return str;
    }


    private CommonUtils(){}
}
