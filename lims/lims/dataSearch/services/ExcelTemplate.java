/**
 * 
 */
package lims.dataSearch.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * @author Administrator
 *
 */
public class ExcelTemplate {
	
	/**
     * 功能:给指定坐标单元格赋值
     */
    public static void setCell(Sheet sheet,Integer rowIndex,Integer colIndex,Object value) {
    	
    	Row row = sheet.getRow(rowIndex);
        Cell cell = row.getCell(colIndex);
        if(cell==null){
        	cell = row.createCell(colIndex);
        }
        
        if(value instanceof String){
        	 cell.setCellValue(value.toString());
        }else if(value instanceof Date){
        	cell.setCellValue((Date)value);
        }else if(value instanceof Double){
        	cell.setCellValue((Double)value);
        }else if(value instanceof Integer){
        	cell.setCellValue((Integer)value);
        }else if(value==null){
        	cell.setCellValue("");
        }else{
        	cell.setCellValue(value.toString());
        }
    }
    
    public static void setListCell(Sheet sheet,List<Map<String,Object>> data){
    	int length = data.size();
    	for(int i=0;i<length;i++){
    		if(sheet.getLastRowNum()<i+7){
    			sheet.createRow(i+7);
    		}
    		Row row = sheet.getRow(i+7);
    		Map<String,Object> result = data.get(i);
    		for(int j=0;j<=17;j++){
    			if(row.getLastCellNum()<=j){
    				row.createCell(j);
    			}
    			Cell cell = row.getCell(j);
    			switch(j){
    				case 0:
    					cell.setCellValue(result.get("testcode")==null?"":result.get("testcode").toString());
    					break;
    				case 1:
    					cell.setCellValue(result.get("testno")==null?"":result.get("testno").toString());
    					break;
    				case 2:
    					cell.setCellValue(result.get("analyte")==null?"":result.get("analyte").toString());
    					break;
    				case 3:
    					cell.setCellValue(result.get("sinonym")==null?"":result.get("sinonym").toString());
    					break;
    				case 4:
    					cell.setCellValue(result.get("analtype")==null?"":result.get("analtype").toString());
    					break;
    				case 5:
    					cell.setCellValue(result.get("servgrp")==null?"":result.get("servgrp").toString());
    					break;
    				case 6:
    					cell.setCellValue(result.get("method")==null?"":result.get("method").toString());
    					break;
    				case 7:
    					cell.setCellValue(result.get("final")==null?"":result.get("final").toString());
    					break;
    				case 8:
    					cell.setCellValue(result.get("picture")==null?"":result.get("picture").toString());
    					break;
    				case 9:
    					cell.setCellValue(result.get("s")==null?"":result.get("s").toString());
    					break;
    				case 10:
    					cell.setCellValue(result.get("units")==null?"":result.get("units").toString());
    					break;
    				case 11:
    					cell.setCellValue(result.get("lowa")==null?"":result.get("lowa").toString());
    					break;
    				case 12:
    					cell.setCellValue(result.get("lowb")==null?"":result.get("lowb").toString());
    					break;
    				case 13:
    					cell.setCellValue(result.get("higha")==null?"":result.get("higha").toString());
    					break;
    				case 14:
    					cell.setCellValue(result.get("highb")==null?"":result.get("highb").toString());
    					break;
    				case 15:
    					cell.setCellValue(result.get("countflag")==null?"":result.get("countflag").toString());
    					break;
    				case 16:
    					cell.setCellValue(result.get("printflag")==null?"":result.get("printflag").toString());
    					break;
    				case 17:
    					cell.setCellValue(result.get("firstuser")==null?"":result.get("firstuser").toString());
    					break;
    				default:
    					break;
    			}
    		}
    	}
    }
}
