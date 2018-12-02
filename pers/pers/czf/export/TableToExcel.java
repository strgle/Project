package pers.czf.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TableToExcel {
	
	public static void tableToExcel(String html,ServletOutputStream out) throws Exception{
		Document doc = Jsoup.parse(html);
		List<List<Map<String,Object>>> execlThead = TableToExcel.parse(doc,"thead");
		List<List<Map<String,Object>>> execlTbody = TableToExcel.parse(doc,"tbody");
		if(execlThead.size()==0&&execlTbody.size()==0){
			execlTbody = TableToExcel.parse(doc,"table");
		}
		TableToExcel.dataToExcel(execlThead, execlTbody, out);
		
	}
	
	/**
	 * 解析数据
	 * @return
	 */
	private static List<List<Map<String,Object>>> parse(Document doc,String type){
		//设置二维数组表头
		List<List<Map<String,Object>>> excelData = new ArrayList<List<Map<String, Object>>>();
		
		Elements theads = doc.getElementsByTag(type);
		if(theads.size()>0){
			Element thead = theads.first();
			//获取行
			Elements trs = thead.getElementsByTag("tr");
			for(int row=0,len=trs.size();row<len;row++){
				Elements ths = trs.get(row).children();
				if(row==excelData.size()){
					excelData.add(new ArrayList<Map<String,Object>>());
				}
				List<Map<String,Object>> rowData = excelData.get(row);
				
				int colIndex = 0;
				//处理数据
				for(int col=0,cl=ths.size();col<cl;col++){
					int cols=1;
					int rows=1;
					Element th = ths.get(col);
					Map<String,Object> cell = new HashMap<String,Object>();
					String text = th.attr("data-data");
					if(text==null||text.equals("")){
						text = th.text().trim();
					}
					
					cell.put("text", text);
					
					String colspan = th.attr("colspan");
					if(colspan!=null&&!colspan.equals("")){
						try {
							cols = Integer.parseInt(colspan);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
						}
					}
					cell.put("colspan", cols);
							
					String rowspan = th.attr("rowspan");
					
					if(rowspan!=null&&!rowspan.equals("")){
						try {
							rows = Integer.parseInt(rowspan);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
						}
					}
					cell.put("rowspan", rows);
					
					//获取起始位置
					for(int index=colIndex,rindex=rowData.size();index<rindex;index++){
						Map<String,Object> rcell = rowData.get(index);
						if(rcell==null){
							colIndex=index;
							break;
						}else{
							colIndex+=1;
						}
					}
					
					if(rowData.size()>colIndex){
						rowData.set(colIndex, cell);
					}else{
						rowData.add(colIndex, cell);
					}
					
					//列转移
					for(int mc=1;mc<cols;mc++){
						rowData.add(colIndex+mc,new HashMap<String,Object>());
					}
							
					//行转移
					for(int rc=1;rc<rows;rc++){
						int curRow = row+rc;
						if(excelData.size()==curRow){
							excelData.add(new ArrayList<Map<String,Object>>());	
						}
						
						List<Map<String,Object>> nextRow = excelData.get(curRow);
						while(nextRow.size()<colIndex){
							nextRow.add(null);
						}
						
						for(int index=0;index<cols;index++){
							nextRow.add(colIndex+index,new HashMap<String,Object>());
						}						
					}
					colIndex+=cols;
				}
			}
		};	
		return excelData;
	}
	
	/**
	 * Excel文档的构成
	 * 在工作簿(WorkBook)里面包含了工作表(Sheet) 在工作表里面包含了行(Row) 行里面包含了单元格(Cell)
	 * 创建一个工作簿的基本步骤
	 * 第一步 创建一个 工作簿 第二步 创建一个 工作表 第三步 创建一行 第四步 创建单元格 第五步 写数据 第六步
	 * 将内存中生成的workbook写到文件中 然后释放资源
	 * 
	 */
	private static void dataToExcel(List<List<Map<String,Object>>> execlThead,List<List<Map<String,Object>>> execlTbody,ServletOutputStream out) throws Exception{
		Workbook wb = new XSSFWorkbook();
		//创建工作表
		Sheet sheet = wb.createSheet("sheet1");	
		
		//合并标题单元格
		for(int row=0,len=execlThead.size();row<len;row++){
			List<Map<String,Object>> tds = execlThead.get(row);
			for(int col=0,collen=tds.size();col<collen;col++){
				Map<String,Object> td = tds.get(col);
				if(td.get("colspan")!=null){
					int rowSpan = (Integer) td.get("rowspan");
					int colSpan = (Integer) td.get("colspan");
					if(rowSpan>1||colSpan>1){
						CellRangeAddress cell = new CellRangeAddress(row, row+rowSpan-1, col, col+colSpan-1);
						sheet.addMergedRegion(cell);
						RegionUtil.setBorderBottom(BorderStyle.THIN, cell, sheet); // 下边框
						RegionUtil.setBorderLeft(BorderStyle.THIN, cell, sheet); // 左边框
						RegionUtil.setBorderRight(BorderStyle.THIN, cell, sheet); // 有边框
						RegionUtil.setBorderTop(BorderStyle.THIN, cell, sheet); // 上边框
						
					}
				}
			}
		}
		
		//合并内容单元格	
		int moveNum = execlThead.size();
		for(int row=0,len=execlTbody.size();row<len;row++){
			List<Map<String,Object>> tds = execlTbody.get(row);
			for(int col=0,collen=tds.size();col<collen;col++){
				Map<String,Object> td = tds.get(col);
				if(td.get("colspan")!=null){
					int rowSpan = (Integer) td.get("rowspan");
					int colSpan = (Integer) td.get("colspan");
					if(rowSpan>1||colSpan>1){
						CellRangeAddress cell = new CellRangeAddress(row+moveNum, row+rowSpan+moveNum-1, col, col+colSpan-1);
						sheet.addMergedRegion(cell);
						RegionUtil.setBorderBottom(BorderStyle.THIN, cell, sheet); // 下边框
						RegionUtil.setBorderLeft(BorderStyle.THIN, cell, sheet); // 左边框
						RegionUtil.setBorderRight(BorderStyle.THIN, cell, sheet); // 有边框
						RegionUtil.setBorderTop(BorderStyle.THIN, cell, sheet); // 上边框						
					}
				}
			}
		}
		
		//添加标题内容
		CellStyle headStyle = wb.createCellStyle();  
		headStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headStyle.setWrapText(true);
		headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		headStyle.setBorderTop(BorderStyle.THIN);
		headStyle.setBorderRight(BorderStyle.THIN);
		headStyle.setBorderBottom(BorderStyle.THIN);
		headStyle.setBorderLeft(BorderStyle.THIN);
		
		for(int row=0,len=execlThead.size();row<len;row++){
			Row title_row = sheet.createRow(row);
			List<Map<String,Object>> tds = execlThead.get(row);
			for(int col=0,collen=tds.size();col<collen;col++){
				Map<String,Object> td = tds.get(col);
				if(td.get("colspan")!=null){
					String text = td.get("text").toString();
					Cell cell = title_row.createCell(col);
					cell.setCellValue(text);
					cell.setCellStyle(headStyle);
				}
			}
		}
				
		//添加数据内容
		CellStyle dataStyle = wb.createCellStyle();  
		dataStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		dataStyle.setWrapText(true);
		dataStyle.setBorderTop(BorderStyle.THIN);
		dataStyle.setBorderRight(BorderStyle.THIN);
		dataStyle.setBorderBottom(BorderStyle.THIN);
		dataStyle.setBorderLeft(BorderStyle.THIN);
		for(int row=0,len=execlTbody.size();row<len;row++){
			Row title_row = sheet.createRow(row+moveNum);
			List<Map<String,Object>> tds = execlTbody.get(row);
			for(int col=0,collen=tds.size();col<collen;col++){
				Map<String,Object> td = tds.get(col);
				if(td.get("colspan")!=null){
					String text = td.get("text").toString();
					Cell cell = title_row.createCell(col);
					cell.setCellValue(text);
					cell.setCellStyle(dataStyle);
				}
			}
		}
		wb.write(out);
		wb.close();
		out.flush();
		out.close();
	}

}
