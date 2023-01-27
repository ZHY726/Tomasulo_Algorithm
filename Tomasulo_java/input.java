import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class input {
    public static datainit mine;
    public static copyinit recorder;
    public static  btbinit mine2;

    public static void main(String[] args) {
        mine = new datainit();
        mine.init();
        recorder = new copyinit();
        recorder.init2();
        mine2 = new btbinit();
        mine2.init();

        String cpuInitFile = "./init.txt";
        String instructionFile = "./instructions.txt";
        int num_ins = 0;

        readCPUinit(cpuInitFile);
        num_ins = readInstructions(instructionFile);
        System.out.println("num_ins=" + num_ins);

        for (int i = 0; i < num_ins; i++) {//show the instruction buffer;
            System.out.println(mine.tomdata.ins_ope[i] + " " + mine.tomdata.ins_f1[i] + " " + mine.tomdata.ins_f2[i] + " " + mine.tomdata.ins_f3[i] + " " + mine.tomdata.ins_f4[i]);
        }

        for (int i = 0; i < 99; i++) {
            mine.tomdata.clock++;
            mine.tomdata.ins_op[0] = mine.tomdata.ins_ope[mine.tomdata.addr];
            mine.tomdata.ins_op[1] = mine.tomdata.ins_f1[mine.tomdata.addr];
            mine.tomdata.ins_op[2] = mine.tomdata.ins_f2[mine.tomdata.addr];
            mine.tomdata.ins_op[3] = mine.tomdata.ins_f3[mine.tomdata.addr];
            mine.tomdata.ins_op[4] = mine.tomdata.ins_f4[mine.tomdata.addr];
            tomasulo.go(mine.tomdata, recorder.tom,  mine2.tombtb);
        }

//        Result Outputs
        writeResult2file();

        System.out.println("Results:");
        for (int i = 0; i < mine.tomdata.arfi.length; i++) {
            if (mine.tomdata.arfi[i] != 0) {
                System.out.println("R" + i + "= " + mine.tomdata.arfi[i]);
            }
        }
        for (int i = 0; i < mine.tomdata.arf.length; i++) {
            if (mine.tomdata.arf[i] != 0) {
                System.out.println("F" + i + "= " + mine.tomdata.arf[i]);
            }
        }
        for (int i = 0; i < mine.tomdata.mem.length; i++) {
            if (mine.tomdata.mem[i] != 0) {
                System.out.println("MEM[" + i + "]= " + mine.tomdata.mem[i]);
            }
        }
        System.out.println();
        System.out.println("Time Table:");
        System.out.println("               IS EX M WB C");
        for (int i = 0; i<mine.tomdata.tt_issue.length; i++) {
            if (mine.tomdata.tt_issue[i] != 0) {
                System.out.println(mine.tomdata.tt_ins[i] + " " + mine.tomdata.tt_issue[i] + " " + mine.tomdata.tt_exew[i] + " " + mine.tomdata.tt_mem[i] + " " + mine.tomdata.tt_result[i] + " " + mine.tomdata.tt_commit[i]);
            }
        }
    }

    public static void readCPUinit(String filePath){
        File file = new File(filePath);
        String s = null;
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            while((s = br.readLine())!=null) {//使用readLine方法，一次读一行
                if(s.equals("")){
                    continue;
                }
                if (s.contains("Integer Adder")) {
                    String[] INT_arr = s.split(":");
                    INT_arr = INT_arr[1].split(" ");
                    mine.tomdata.addrsi = Integer.parseInt(INT_arr[0]);
                    mine.tomdata.addexi = Integer.parseInt(INT_arr[1]);
                    mine.tomdata.addrsi *= Integer.parseInt(INT_arr[3]);//# of FUs
//                    System.out.println(Arrays.toString(INT_arr));
                }
                else if(s.contains("FP Adder")){
                    String[] FPAdd_arr = s.split(":");
                    FPAdd_arr = FPAdd_arr[1].split(" ");
                    mine.tomdata.addrs = Integer.parseInt(FPAdd_arr[0]);
                    mine.tomdata.addex = Integer.parseInt(FPAdd_arr[1]);
                    mine.tomdata.addrs *= Integer.parseInt(FPAdd_arr[3]);//# of FUs
//                    System.out.println(Arrays.toString(FPAdd_arr));
                }
                else if (s.contains("FP Multiplier")){
                    String[] FPMult_arr = s.split(":");
                    FPMult_arr = FPMult_arr[1].split(" ");
                    mine.tomdata.multrs = Integer.parseInt(FPMult_arr[0]);
                    mine.tomdata.multex = Integer.parseInt(FPMult_arr[1]);
                    mine.tomdata.multrs *= Integer.parseInt(FPMult_arr[3]);//# of FUs
//                    System.out.println(Arrays.toString(FPMult_arr));
                }
                else if (s.contains("Load/Store Unit:")){
                    String[] LdSd_arr = s.split(":");
                    LdSd_arr = LdSd_arr[1].split(" ");
                    mine.tomdata.ldrs = Integer.parseInt(LdSd_arr[0]);
                    mine.tomdata.ldex = Integer.parseInt(LdSd_arr[1]);
                    mine.tomdata.memt = Integer.parseInt(LdSd_arr[2]);
                    mine.tomdata.ldrs *= Integer.parseInt(LdSd_arr[3]);//# of FUs
//                    System.out.println(Arrays.toString(LdSd_arr));
                }
                else if (s.contains("ROB Entries=")){
                    String[] ROB_arr = s.split("=");
                    int ROB_entries = Integer.parseInt(ROB_arr[1]);
//                    mine.tomdata.rob = ROB_entries;
//                    System.out.println(ROB_entries);
                }
                else if (s.contains("CDB buffer entries=")){
                    String[] CDB_buffer_arr = s.split("=");
                    int CDB_buffer_entries = Integer.parseInt(CDB_buffer_arr[1]);
//                    System.out.println(CDB_buffer_entries);
                }
                else if (s.contains("R0=0")){
                    String[] arf_arr = s.split(",");
                    for(int i= 0; i<arf_arr.length;i++){
                        String[] arf_temp_arr = arf_arr[i].split("=");
                        String regEx = "[^0123456789-]";
                        Pattern p_order = Pattern.compile(regEx);
                        Matcher m_order = p_order.matcher(arf_temp_arr[0]);
                        int arf_order = Integer.parseInt(m_order.replaceAll("").trim());
                        if(arf_temp_arr[0].contains("R")){
                            int arf_value = Integer.parseInt(arf_temp_arr[1]);
                            mine.tomdata.arfi[arf_order] = arf_value;
                        }
                        else if (arf_temp_arr[0].contains("F")){
                            double arf_value = Double.parseDouble(arf_temp_arr[1]);
                            mine.tomdata.arf[arf_order] = arf_value;
                        }
                    }
                    System.out.println("ARFi:"+Arrays.toString(mine.tomdata.arfi));
                    System.out.println("ARFf:"+Arrays.toString(mine.tomdata.arf));
                }
                else if(s.contains("MEM")){
                    String[] mem_arr = s.split(",");
                    for(int i= 0; i<mem_arr.length;i++) {
                        String[] mem_temp_arr = mem_arr[i].split("=");
                        String regEx = "[^0123456789-]";
                        Pattern p_order = Pattern.compile(regEx);
                        Matcher m_order = p_order.matcher(mem_temp_arr[0]);
                        int mem_order = Integer.parseInt(m_order.replaceAll("").trim());
                        double mem_value = Double.parseDouble(mem_temp_arr[1]);
                        mine.tomdata.mem[mem_order] = mem_value;
                    }
                    System.out.println("MEM:"+Arrays.toString(mine.tomdata.mem));
                }
            }
            br.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        for(int i = 0; i < 30; i++){
            mine.tomdata.rat[i] = "ARF" + Integer.toString(i);
        }
        for(int i = 0; i < 30; i++){
            mine.tomdata.rati[i] = "ARF" + Integer.toString(i);
        }
        for(int i = 0; i < 30; i++) {
            mine.tomdata.da_fd[i] =  mine.tomdata.arf[i];
        }
        for(int i = 0; i < 30; i++) {
            mine.tomdata.da_fdi[i] =  mine.tomdata.arfi[i];
        }

        mine.tomdata.ld_time = new int[mine.tomdata.ldrs];
        mine.tomdata.ld1_time = new int[mine.tomdata.ldrs];
        mine.tomdata.ld_address = new int[mine.tomdata.ldrs];
        mine.tomdata.ld_busy = new int[mine.tomdata.ldrs];
        mine.tomdata.ld_busy1 = new int[mine.tomdata.ldrs];
        mine.tomdata.ld_pc = new int[mine.tomdata.ldrs];
        mine.tomdata.ld_q = new int[mine.tomdata.ldrs];
        mine.tomdata.ld_vjt = new int[mine.tomdata.ldrs];
        mine.tomdata.ld_qj = new int[mine.tomdata.ldrs];
        mine.tomdata.ld_vj = new int[mine.tomdata.ldrs];
        mine.tomdata.ld_op = new int[mine.tomdata.ldrs];
        mine.tomdata.ld_vkt = new int[mine.tomdata.ldrs];
        mine.tomdata.ld_vk = new int[mine.tomdata.ldrs];
        mine.tomdata.ld_qk = new int[mine.tomdata.ldrs];
        mine.tomdata.ld_v1t = new int[mine.tomdata.ldrs];
        mine.tomdata.ld_v1 = new double[mine.tomdata.ldrs];
        mine.tomdata.ld_q1 = new int[mine.tomdata.ldrs];
        mine.tomdata.ld_seq = new int[mine.tomdata.ldrs];

        int sum = mine.tomdata.addrs + mine.tomdata.multrs;

        mine.tomdata.rs_time = new int[sum];
        mine.tomdata.rs_busy = new int[sum];
        mine.tomdata.rs_busy1 = new int[sum];
        mine.tomdata.rs_op = new int[sum];
        mine.tomdata.rs_vj = new double[sum];
        mine.tomdata.rs_vk = new double[sum];
        mine.tomdata.rs_vjt = new int[sum];
        mine.tomdata.rs_vkt = new int[sum];
        mine.tomdata.rs_qj = new int[sum];
        mine.tomdata.rs_qk = new int[sum];
        mine.tomdata.rs_pc = new int[sum];

        mine.tomdata.rsi_time = new int[mine.tomdata.addrsi];
        mine.tomdata.rsi_busy = new int[mine.tomdata.addrsi];
        mine.tomdata.rsi_busy1 = new int[mine.tomdata.addrsi];
        mine.tomdata.rsi_op = new int[mine.tomdata.addrsi];
        mine.tomdata.rsi_vj = new int[mine.tomdata.addrsi];
        mine.tomdata.rsi_vk = new int[mine.tomdata.addrsi];
        mine.tomdata.rsi_vjt = new int[mine.tomdata.addrsi];
        mine.tomdata.rsi_vkt = new int[mine.tomdata.addrsi];
        mine.tomdata.rsi_qj = new int[mine.tomdata.addrsi];
        mine.tomdata.rsi_qk = new int[mine.tomdata.addrsi];
        mine.tomdata.rsi_pc = new int[mine.tomdata.addrsi];

    }
    public static int readInstructions(String filePath){
        File file = new File(filePath);
        String s = null;
        int num_ins = 0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            int i = 0;
            while((s = br.readLine())!=null) {//使用readLine方法，一次读一行
                if(s.equals("")){
                    continue;
                }
                String[] instruction_arr= s.split(" ");
                String[] ins_value_temp_arr = instruction_arr[1].split(",");
                String regEx = "[^0123456789-]";
                Pattern p_ins_value = Pattern.compile(regEx);
                Matcher m_f1 = p_ins_value.matcher(ins_value_temp_arr[0]);
                Matcher m_f2;
                Matcher m_f3;
                if(ins_value_temp_arr[1].contains("(")){
                    String[] ldsd_temp_arr = ins_value_temp_arr[1].split("R");
                    m_f2 = p_ins_value.matcher(ldsd_temp_arr[0]);
                    m_f3 = p_ins_value.matcher(ldsd_temp_arr[1]);
                }
                else {
                    m_f2 = p_ins_value.matcher(ins_value_temp_arr[1]);
                    m_f3 = p_ins_value.matcher(ins_value_temp_arr[2]);
                }
                int f1 = Integer.parseInt(m_f1.replaceAll("").trim());
                int f2 = Integer.parseInt(m_f2.replaceAll("").trim());
                int f3 = Integer.parseInt(m_f3.replaceAll("").trim());
                mine.tomdata.ins_f1[i] = f1;
                mine.tomdata.ins_f2[i] = f2;
                mine.tomdata.ins_f3[i] = f3;
                switch (instruction_arr[0]){
                    case("Ld"):
                        mine.tomdata.ins_ope[i] = 1;
                        mine.tomdata.ins_f2[i] = f3;
                        mine.tomdata.ins_f3[i] = f2;
                        break;
                    case("Sd"):
                        mine.tomdata.ins_ope[i] = 2;
                        mine.tomdata.ins_f2[i] = f3;
                        mine.tomdata.ins_f3[i] = f2;
                        break;
                    case("Add.d"):
                        mine.tomdata.ins_ope[i] = 3;
                        break;
                    case("Sub.d"):
                        mine.tomdata.ins_ope[i] = 4;
                        break;
                    case("Mult.d"):
                        mine.tomdata.ins_ope[i] = 5;
                        break;
                    case("Add"):
                        mine.tomdata.ins_ope[i] = 6;
                        break;
                    case("Sub"):
                        mine.tomdata.ins_ope[i] = 7;
                        break;
                    case("Addi"):
                        mine.tomdata.ins_ope[i] = 8;
                        break;
                    case("Beq"):
                        mine.tomdata.ins_ope[i] = 9;
                        mine.tomdata.ins_f1[i] = 0;
                        mine.tomdata.ins_f2[i] = f1;
                        mine.tomdata.ins_f3[i] = f2;
                        mine.tomdata.ins_f4[i] = f3;
                        break;
                    case("Bne"):
                        mine.tomdata.ins_ope[i] = 10;
                        mine.tomdata.ins_f1[i] = 0;
                        mine.tomdata.ins_f2[i] = f1;
                        mine.tomdata.ins_f3[i] = f2;
                        mine.tomdata.ins_f4[i] = f3;
                        break;
                }
                i++;
            }
            num_ins = i;
            br.close();
//            System.out.println(Arrays.toString(mine.tomdata.ins_ope));
//            System.out.println(Arrays.toString(mine.tomdata.ins_f1));
//            System.out.println(Arrays.toString(mine.tomdata.ins_f2));
//            System.out.println(Arrays.toString(mine.tomdata.ins_f3));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return num_ins;
    }

    public static void writeResult2file(){
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("MM_dd_yyyy_HH_mm_ss");
        Date date = new Date();// 获取当前时间
        String nowTime= sdf.format(date);
        System.out.println(nowTime); // 输出已经格式化的现在时间（24小时制）
        File writeName = new File("./output_"+nowTime+".txt");
        try{
            writeName.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(writeName));


            out.write("Time Table:\n");
            out.write("               IS EX M WB C\n");
            for (int i = 0; i<mine.tomdata.tt_issue.length; i++) {
                if (mine.tomdata.tt_issue[i] != 0) {
                    out.write(mine.tomdata.tt_ins[i] + " " + mine.tomdata.tt_issue[i] + " " + mine.tomdata.tt_exew[i] + " " + mine.tomdata.tt_mem[i] + " " + mine.tomdata.tt_result[i] + " " + mine.tomdata.tt_commit[i]+"\n");
                }
            }
            out.write("\n");
            out.write("ARF & Memory Results:\n");
            for (int i = 0; i < mine.tomdata.arfi.length; i++) {
                if (mine.tomdata.arfi[i] != 0) {
                    out.write("R" + i + "= " + mine.tomdata.arfi[i]+"; ");
                }
            }
            out.write("\n");
            for (int i = 0; i < mine.tomdata.arf.length; i++) {
                if (mine.tomdata.arf[i] != 0) {
                    out.write("F" + i + "= " + mine.tomdata.arf[i]+"; ");
                }
            }
            out.write("\n");
            for (int i = 0; i < mine.tomdata.mem.length; i++) {
                if (mine.tomdata.mem[i] != 0) {
                    out.write("MEM[" + i + "]= " + mine.tomdata.mem[i]+"; ");
                }
            }
            out.write("\n");
            out.flush();
            out.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
