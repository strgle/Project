package pers.czf.commonUtils;
import java.net.URL;

import com.core.handler.SessionFactory;

public class ProjectUtils {
	private static String WEB_ROOT;
	private static String WEB_INF_PATH;

	public final static String getWebRoot(){
		if(WEB_ROOT!=null){
			return WEB_ROOT;
		}else{
			URL url = ProjectUtils.class.getProtectionDomain().getCodeSource().getLocation();   
	        String path = url.toString();   
	        int index = path.indexOf("WEB-INF");  
	        if(index == -1){
	        	throw new RuntimeException("WEB-INF不存在");
	        }  
	        path = path.substring(0, index);   
	        if(path.startsWith("zip")){//zip:D:/
	            path = path.substring(4);   
	        }else if(path.startsWith("file")){//file:/D:/..   
	            path = path.substring(6);   
	        }else if(path.startsWith("jar")){//jar:file:/D:/...
	            path = path.substring(10);    
	        }
	        WEB_ROOT = path;
	        return WEB_ROOT;   
		}
	}
	
	/**
	 * 获取web-inf路径
	 * @author zhfchen  2013-5-12
	 * @return WEB-INF路径
	 */	
	public final static String getWebInfPath(){
		if(WEB_INF_PATH!=null){
			return WEB_INF_PATH;
		}else{
			WEB_INF_PATH = getWebRoot()+"WEB-INF/";
	        return WEB_INF_PATH;
		}  
	}
	
	public final static String classFilePath(String fileName){
		return getWebInfPath()+"classes/"+fileName;
	}
	
	public final static String classPath(){
		return getWebInfPath()+"classes/";
	}
	/**
	 * 处理质量考核查询部门
	 * @return
	 */
	 public static String getDept()
	  {
	    String dept = SessionFactory.getDept();
	    if (dept.equals("山东益丰生化环保股份有限公司")) {
	      dept = "山东益丰生化环保股份有限公司";
	    } else if (dept.equals("京博石油化工有限公司橡胶分公司")) {
	      dept = "京博石油化工有限公司橡胶分公司";
	    } else if (dept.equals("山东安特检测有限公司")) {
	      dept = "山东安特检测有限公司";
	    } else if (dept.equals("分析分离研究所")) {
	      dept = "分析分离研究所";
	    } else {
	      dept = "石化检测中心（集团）";
	    }
	    return dept;
	  }
}
