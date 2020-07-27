package cn.shiva.common.utils;

import java.util.regex.Pattern;

/**
 * @author shiva   2020/7/9 16:12
 */
public class RegexVerify {

    /**
     * 邮箱正则; 格式：zhangsan@xxx.com.cn
     */
    public static final String EMAIL = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";

    /**
     * 手机号正则
     *<p>移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）</p>
     *<p>联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）</p>
     *<p>电信的号段：133、153、180（未启用）、189</p>
     */
    public static final String MOBILE = "(\\+\\d+)?1[34578]\\d{9}$";

    /**
     * 居民身份证号码15位或18位，最后一位可能是数字或字母
     */
    public static final String IDCARD = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";


    /**
     * @param regex RegexVerify 预设的正则表达式
     * @param str 待验证的字符串
     * @return 如果匹配成功返回ture
     */
    public static boolean checkRegex(String regex, String str) {
        return Pattern.matches(regex, str);
    }


}
