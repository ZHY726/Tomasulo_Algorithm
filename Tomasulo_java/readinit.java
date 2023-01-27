import java.io.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class readinit {
    public static datainit mine;
    public static void main(String[] args) {
        String cpuInitFile = "./init.txt";
        String instructionFile = "./instructions.txt";
        mine = new datainit();
        mine.init();
//        readCPUinit(cpuInitFile);
//        readInstructions(instructionFile);
//        instructionsInit instructionsInitData = readInstructions(instructionFile);
//        System.out.println(cpuInitData.test_instruction_f1[0]);
    }

    public static void readCPUinit(String filePath){
        File file = new File(filePath);
        String s = null;
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            while((s = br.readLine())!=null) {//使用readLine方法，一次读一行
                if (s.contains("Integer Adder")) {
                    String[] INT_arr = s.split(":");
                    INT_arr = INT_arr[1].split(" ");
                    mine.tomdata.addrsi = Integer.parseInt(INT_arr[0]);
                    mine.tomdata.addexi = Integer.parseInt(INT_arr[1]);
//                    mine.tomdata. = Integer.parseInt(INT_arr[3]);//# of FUs
//                    System.out.println(Arrays.toString(INT_arr));
                }
                else if(s.contains("FP Adder")){
                    String[] FPAdd_arr = s.split(":");
                    FPAdd_arr = FPAdd_arr[1].split(" ");
                    mine.tomdata.addrs = Integer.parseInt(FPAdd_arr[0]);
                    mine.tomdata.addex = Integer.parseInt(FPAdd_arr[1]);
//                    mine.tomdata. = Integer.parseInt(FPAdd_arr[3]);//# of FUs
//                    System.out.println(Arrays.toString(FPAdd_arr));
                }
                else if (s.contains("FP Multiplier")){
                    String[] FPMult_arr = s.split(":");
                    FPMult_arr = FPMult_arr[1].split(" ");
                    mine.tomdata.multrs = Integer.parseInt(FPMult_arr[0]);
                    mine.tomdata.multex = Integer.parseInt(FPMult_arr[1]);
//                    mine.tomdata. = Integer.parseInt(FPMult_arr[3]);//# of FUs
//                    System.out.println(Arrays.toString(FPMult_arr));
                }
                else if (s.contains("Load/Store Unit:")){
                    String[] LdSd_arr = s.split(":");
                    LdSd_arr = LdSd_arr[1].split(" ");
                    mine.tomdata.ldrs = Integer.parseInt(LdSd_arr[0]);
                    mine.tomdata.ldex = Integer.parseInt(LdSd_arr[1]);
                    mine.tomdata.memt = Integer.parseInt(LdSd_arr[2]);
//                    mine.tomdata. = Integer.parseInt(LdSd_arr[3]);//# of FUs
//                    System.out.println(Arrays.toString(LdSd_arr));
                }
                else if (s.contains("ROB Entries=")){
                    String[] ROB_arr = s.split("=");
                    int ROB_entries = Integer.parseInt(ROB_arr[1]);
                    mine.tomdata.rob = ROB_entries;
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
                        String regEx = "[^0-9]";
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
                    System.out.println(Arrays.toString(mine.tomdata.arfi));
                    System.out.println(Arrays.toString(mine.tomdata.arf));
                }
                else if(s.contains("MEM")){
                    String[] mem_arr = s.split(",");
                    for(int i= 0; i<mem_arr.length;i++) {
                        String[] mem_temp_arr = mem_arr[i].split("=");
                        String regEx = "[^0-9]";
                        Pattern p_order = Pattern.compile(regEx);
                        Matcher m_order = p_order.matcher(mem_temp_arr[0]);
                        int mem_order = Integer.parseInt(m_order.replaceAll("").trim());
                        double mem_value = Double.parseDouble(mem_temp_arr[1]);
                        mine.tomdata.mem[mem_order] = mem_value;
                    }
//                    System.out.println(Arrays.toString(mine.tomdata.mem));
                }
            }
            br.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void readInstructions(String filePath){
        File file = new File(filePath);
        String s = null;
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            int i = 0;
            while((s = br.readLine())!=null) {//使用readLine方法，一次读一行
                String[] instruction_arr= s.split(" ");
                String[] ins_value_temp_arr = instruction_arr[1].split(",");
                String regEx = "[^0-9]";
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
                        break;
                    case("Sd"):
                        mine.tomdata.ins_ope[i] = 2;
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
                        break;
                    case("Bne"):
                        mine.tomdata.ins_ope[i] = 10;
                        break;
                }
                i++;
            }
            br.close();
            System.out.println(Arrays.toString(mine.tomdata.ins_ope));
            System.out.println(Arrays.toString(mine.tomdata.ins_f1));
            System.out.println(Arrays.toString(mine.tomdata.ins_f2));
            System.out.println(Arrays.toString(mine.tomdata.ins_f3));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

