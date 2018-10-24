# Chinese-word-tagging-system-based-on-Hmm
cnblogs：https://www.cnblogs.com/xinglichao/p/8985831.html




Hmm：
//*******项目编码GBK
//训练命令
//         训练文件
java train train.dat

//测试命令
//        测试文件   结果
java test test.dat result.dat
//列子
java test subtest/test-1.dat result/result1.dat

//评估
//              结果
java evaluation result.dat
Demo：
//*******项目编码GBK
//使用命令
java Demo

//实验用的句子

目前 这 条 高速公路 之间 的 路段 已 紧急 封闭 。

//目前/t 这/rzv 条/q 高速公路/n 之间/f 的/ude1 路段/n 已/d 紧急/a 封闭/v 。/w
FileFile：
//*******项目编码GBK
//拆分文件的命令格式
//             源文件  目标文件  拆分的个数
java splitfile fromfile tofiles subfilenumber

java splitfile test.dat dir/subtest 10

//合并文件的命令格式
//装待合并的文件的文件夹  生成的新的文件
java mergefile dir newfile

java mergefile dir newfile.dat

注意：Demo的运行依赖与训练后的模型参数labellist.dat、wordlist.dat、model.dat
训练结束后如运行Demo则将labellist.dat、wordlist.dat、model.dat拷贝到Demo

