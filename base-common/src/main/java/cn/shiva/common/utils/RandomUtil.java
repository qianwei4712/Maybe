package cn.shiva.common.utils;

/**
 * 随机数生成器
 * @author shiva   2020/7/9 14:26
 */
public class RandomUtil {

    /**
     * @return 默认生成6位数字验证码
     */
    public static String generate(){
        return generateDigit(6);
    }

    /**
     * @return 生成指定位数的数字验证码
     */
    public static String generateDigit(int number){
        try {
            if (number < 1 || number > 100){
                return "000000";
            }
            return String.format("%06d",new Double(Math.random() * Math.pow(10, number)).intValue());
        } catch (Exception e) {
            e.printStackTrace();
            return "000000";
        }
    }

}
