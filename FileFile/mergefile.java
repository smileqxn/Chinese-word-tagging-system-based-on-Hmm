public class mergefile {

	public static void main(String[] args) throws Exception{
		DoFile dofile = new DoFile();
		// 源文件  目标文件  拆分的个数
		//dofile.splitfile("test.dat", "subtest/test", 10);
		//dofile.splitfile(args[0], args[1], Integer.parseInt(args[2]));
		
		//合并文件  装小文件的文件夹   大文件
		dofile.mergefile(args[0], args[1]);
	}

}