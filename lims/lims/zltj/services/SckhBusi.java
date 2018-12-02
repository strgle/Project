package lims.zltj.services;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lims.zltj.vo.ChpVo;
import lims.zltj.vo.FxxmVo;
import lims.zltj.vo.ZzhiVo;
public class SckhBusi {

	public static List<FxxmVo> dealFxxmList(List<Map<String, Object>> data)
	  {
	    List<FxxmVo> rdata = new ArrayList();
	    FxxmVo vo = null;
	    for (Map<String, Object> map : data)
	    {
	      String matCode = (String)map.get("matcode");
	      String analyte = (String)map.get("analyte");
	      String status = (String)map.get("status");
	      String tasktype = (String)map.get("tasktype");
	      String specNo = map.get("specno").toString();
	      String rq = (String)map.get("rq");
	      String testcode = map.get("testcode").toString();
	      if ((vo == null) || (!vo.getMatCode().equals(matCode)) || (!vo.getName().equals(analyte)))
	      {
	        vo = new FxxmVo(matCode, analyte);
	        vo.setSpecno(specNo);
	        vo.setTestcode(testcode);
	        rdata.add(vo);
	      }
	      if (!testcode.equals(vo.getTestcode())) {
	        vo.setTestcode(testcode);
	      }
	      if (!"-1".equals(specNo)) {
	        vo.setSpecno(specNo);
	      }
	      vo.add(rq, status, tasktype);
	    }
	    return rdata;
	  }
	  
	  public static List<ChpVo> addToChp(List<FxxmVo> data, Map<String, String> ypMap, List<String> dateList)
	  {
	    List<ChpVo> rdata = new ArrayList<ChpVo>();
	    ChpVo vo = null;
	    for (FxxmVo f : data)
	    {
	      if ((vo == null) || (!f.getMatCode().equals(vo.getCode())))
	      {
	        vo = new ChpVo();
	        vo.setCode(f.getMatCode());
	        vo.setName((String)ypMap.get(f.getMatCode()));
	        rdata.add(vo);
	      }
	      for (String rq : dateList) {
	        f.jsHgl(rq);
	      }
	      vo.addData(f);
	    }
	    return rdata;
	  }
	  
	  public static void dealZZ(ZzhiVo zz)
	  {
	    int rownum = 0;
	    for (ChpVo cp : zz.getData()) {
	      rownum += cp.getRowNum();
	    }
	    zz.setRowNum(rownum);
	  }
	
}
