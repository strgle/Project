package lims.common.mvc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pers.czf.commonUtils.DateUtils;
import pers.czf.dbManager.Dao;
import pers.czf.export.TableToExcel;
import pers.czf.ftpUtils.FtpClientUtils;

@Controller
@RequestMapping("lims/common/")
public class CommonAction {
	
	@Autowired
	private Dao dao;
	
	private String dcuFtpIp = "10.254.2.13";
	private String dcuFtpUser = "lims";
	private String dcuFtpPassword = "lims";
	@RequestMapping("downLoad")
	public ResponseEntity<byte[]> downLoad(HttpServletRequest request) throws UnsupportedEncodingException{
		byte[] osbyte = new byte[1024];
		String fileName = null;
		//
		String ordNo = request.getParameter("ordNo");
		String testCode = request.getParameter("testCode");
		String analyte = request.getParameter("analyte");
		String sql = "select stardoc_id from RES_ATTACHMENTS where ordno=? and testcode=? and analyte=? order by origrec desc";
		List<String> list = dao.queryListValue(sql, String.class, ordNo, testCode,analyte);
		String fileId = "-1";
		if(list.size()>0){
			fileId = list.get(0);
		}
		String sql2 = "select file_name,file_image from db_file_storage where file_image_id=?";
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
		try {
			conn = dao.getConnection();
			ps = conn.prepareStatement(sql2);
			ps.setObject(1, fileId);
			rs = ps.executeQuery();
			if(rs.next()){
				fileName = rs.getString(1);
				Blob b = rs.getBlob(2);
				InputStream in = b.getBinaryStream();
				ByteArrayOutputStream bos = new  ByteArrayOutputStream(); 
				
				byte[] bt = new byte[1024];
	            int len=-1;
	            while((len = in.read(bt)) != -1){
	                bos.write(bt,0, len);
	                bos.flush();
	            }
	            
		        osbyte = bos.toByteArray();
		        in.close();
		        bos.close();
			}
			
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				rs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				ps.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("GBK"),"ISO-8859-1"));
		
		return new ResponseEntity<byte[]>(osbyte,headers, HttpStatus.OK);
	}
	
	@RequestMapping("ftpdownLoad")
	public ResponseEntity<byte[]> ftpdownLoad(HttpServletRequest request) throws UnsupportedEncodingException{
		String fileurl = request.getParameter("fileurl");
		String fileName =fileurl.substring(fileurl.lastIndexOf("/")+1);
		byte[] osbyte = FtpClientUtils.downLoad(dcuFtpIp, 21, dcuFtpUser, dcuFtpPassword, fileurl);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("GBK"),"ISO-8859-1"));
		return new ResponseEntity<byte[]>(osbyte,headers, HttpStatus.OK);
	}
	@RequestMapping("upLoad")
	public String upLoad(){
		return "lims/dataLedger/area/testIndex";
	}
	
	@RequestMapping("expExcel")
	public void expExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String html = request.getParameter("expContent");
		//将数据转换为excel
		String dateName = DateUtils.DateFormat(new Date(), "yyyyMMddHHmmss");
		String filename = new String(dateName.getBytes(), "ISO-8859-1"); 
		response.setHeader("Content-disposition", "attachment; filename=" + filename + ".xlsx");// 组装附件名称和格式  
		ServletOutputStream out = response.getOutputStream();
		TableToExcel.tableToExcel(html, out);
	}
	
	
}
