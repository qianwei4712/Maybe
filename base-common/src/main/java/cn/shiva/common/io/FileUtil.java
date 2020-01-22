package cn.shiva.common.io;

import jdk.nashorn.internal.objects.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;
import java.util.UUID;

/**
 * @author shiva   2020/1/22 22:17
 */
public class FileUtil extends org.apache.commons.io.FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 上传单个文件
     * @param file 上传文件
     * @param filePath 文件路径
     * @return 文件全路径
     */
    public static String uploadFile(HttpServletRequest request, MultipartFile file, String filePath){
        if(file.isEmpty()){
            logger.debug("没有上传文件");
        }else{
            //获取东八区时间
            Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
            //获取年
            int year = c.get(Calendar.YEAR);
            //获取月份，0表示1月份
            int month = c.get(Calendar.MONTH) + 1;
            //获取当前天数
            int day = c.get(Calendar.DAY_OF_MONTH);
            //文件地址
            filePath = filePath + year + "/" + month + "/" + day + "/" + "temp";
            //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
            if (createDirectory(filePath)){
                logger.debug("创建文件夹失败");
                return null;
            }
            try {
                String suffix = "";
                String[] strArray = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
                if (strArray.length > 0){
                    suffix = strArray[strArray.length -1];
                }
                //中文名路径报错
                String fileName = UUID.randomUUID().toString();
                fileName = fileName + "." + suffix;
                FileUtil.copyInputStreamToFile(file.getInputStream(), new File(filePath, fileName));
                logger.debug("文件上传成功:" + filePath + "/" + fileName);
                return filePath + "/" + fileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    /**
     * 创建目录
     * @param descDirName 目录名,包含路径
     * @return 如果创建成功，则返回true，否则返回false
     */
    public static boolean createDirectory(String descDirName) {
        String descDirNames = descDirName;
        if (!descDirNames.endsWith(File.separator)) {
            descDirNames = descDirNames + File.separator;
        }
        File descDir = new File(descDirNames);
        if (descDir.exists()) {
            logger.debug("目录 " + descDirNames + " 已存在!");
            return false;
        }
        // 创建目录
        if (descDir.mkdirs()) {
            logger.debug("目录 " + descDirNames + " 创建成功!");
            return true;
        } else {
            logger.debug("目录 " + descDirNames + " 创建失败!");
            return false;
        }

    }

}
