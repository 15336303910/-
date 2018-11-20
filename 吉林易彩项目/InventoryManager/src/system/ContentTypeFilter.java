package system;
import java.io.*;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;
public class ContentTypeFilter extends StrutsPrepareAndExecuteFilter{
	
	@Override
    public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain)throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		int contentLength = httpServletRequest.getContentLength();
		ServletContext servletContext = httpServletRequest.getSession().getServletContext();
		System.out.println("2017 S2-045");
        String contentType = null;
        String params = servletContext.getInitParameter("content-type-param");
        if(request.getContentType()!=null){
            contentType = request.getContentType().toLowerCase(Locale.ENGLISH);
            /*请求大小小于2M，不是文件上传并且是正常请求时，放过*/
            if(params!=null && contentType!=null && params.contains(contentType) && contentLength<2097152){
            	chain.doFilter(request,response);
            }
        }
        //文件上传时过滤掉文件边界
        if(contentType!=null){
        	contentType = contentType.contains(",")?contentType.split(",")[0].trim():contentType.split(";")[0].trim();
        	//文件上传并且文件小于2g
        	if(contentType!=null && contentLength<2097152000){
        		//content-type位于白名单放过并且上传的文件名称当中不包括空字节
                if(!Contain_space(request)){
                	chain.doFilter(request,response);
                }
            }
        }else{
        	chain.doFilter(request,response);
        }
    }

    public boolean Contain_space(ServletRequest request) {
        try {
            InputStream is = request.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(is, "utf-8"));
            StringBuilder sb = new StringBuilder();
            String tmp = null;
            while ((tmp = read.readLine()) != null) {
                sb.append(tmp + "\r\n");
            }
            Pattern pattern = Pattern.compile("filename(.*?)\r\n");
            //从filename一直截取到下一个换行符位置，通过正则表达式过滤出上传的文件名称
            Matcher matcher = pattern.matcher(sb.toString().toLowerCase(Locale.ENGLISH));//将文件请求内容全部小写
            while(matcher.find()){
                String filename = matcher.group();
                if(filename.contains("\\0b") || filename.contains(" ") || filename.contains("\\u0000") || filename.contains("@ognl")){
                	//对文件名称进行过滤，筛选掉含有空字符的上传请求
                    return true;
                }
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return false;
    }
}
