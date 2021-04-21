package org.rosychao.springframework.web.servlet;

import org.rosychao.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义模板解析引擎
 * render()核心方法进行渲染，通过response输出
 */
public class View {

    public final String DEFAULT_CONTENT_TYPE = "text/html;charset=utf-8";

    private String contentType = DEFAULT_CONTENT_TYPE;

    private File viewFile;

    public View(File viewFile) {
        this.viewFile = viewFile;
    }

    public void render(Map<String, ?> model,
                       HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuffer sb = new StringBuffer();
        try {
            RandomAccessFile ra = new RandomAccessFile(this.viewFile,"r");
            String line  = null;
            while (null != (line = ra.readLine())){
                line = new String(line.getBytes("ISO-8859-1"),"utf-8");
                Pattern pattern = Pattern.compile("\\$\\{[^\\}]+\\}",Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()){
                    String paramName = matcher.group();
                    paramName = paramName.replaceAll("\\$\\{|\\}","");
                    Object paramValue = model.get(paramName);
                    if(null == paramValue){ continue;}
                    line = matcher.replaceFirst(StringUtils.makeStringForRegExp(paramValue.toString()));
                    matcher = pattern.matcher(line);
                }
                sb.append(line);
            }
        } catch (FileNotFoundException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 设置编码
        response.setCharacterEncoding("utf-8");
        // 设置Content-type
        response.setContentType(contentType);
        // resp输出内容
        response.getWriter().write(sb.toString());
    }
}

