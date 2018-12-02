package lims.basic.vo;
import java.util.ArrayList;
import java.util.List;
public class Area {
	private String name;
	private List<Plant> plants = new ArrayList();
	public String getName()
	{
	   return this.name;
	}
	  
	public void setName(String name)
	{
	   this.name = name;
	}
	  
	public List<Plant> getPlants()
	{
	   return this.plants;
	}
	  
	public void setPlants(List<Plant> plants)
	{
	   this.plants = plants;
	} 
}
