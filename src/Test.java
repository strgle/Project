public class Test {

	public static void main(String[] args) {
		// 测试截取字符串长度？
		String pre="-";
		String batchno = "总碱度含量-总碱度含量";
		System.out.println(batchno.substring(0, batchno.indexOf("-")));
	}

}
