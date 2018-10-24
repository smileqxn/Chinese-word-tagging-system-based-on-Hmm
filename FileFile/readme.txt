//*******项目编码GBK
//拆分文件的命令格式
//             源文件  目标文件  拆分的个数
java splitfile fromfile tofiles subfilenumber

java splitfile test.dat dir/subtest 10

//合并文件的命令格式
//装待合并的文件的文件夹  生成的新的文件
java mergefile dir newfile

java mergefile dir newfile.dat