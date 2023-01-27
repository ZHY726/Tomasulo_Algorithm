import java.util.Queue;

public class copy {
    public static int[] pc;
    public static int clock;
    public static int[] seq;
    public static int[] rob;
    public static int[] addr;
    public static int[][] J;
    public static int[] v;

    public static data[] a;
    public static int[][] memot;
    public static int[][] ins_op;
    public static int[][] ins_ope;
    public static int[][] ins_f1;
    public static int[][] ins_f2;
    public static int[][] ins_f3;
    public static int[][] ins_f4;


    public static int[][] lsq_op;//指令，以数字代替，与input.java文件中数字相同
    public static int[][] lsq_address; //计算出的地址
    public static double[][] lsq_value;//计算出的值
    public static int[][] lsq_as;//当前行的表格完整度，地址与值每填一个+1，最大为2。用于Memory disambiguation的判断。
    public static int[][] lsq_vs;
    public static int[][] lsq_pc;
    public static int[][] mem_time;

    public static int[][] tt_issue;
    public static int[][] tt_exe;
    public static int[][] tt_exew;
    public static int[][] tt_exes;
    public static int[][] tt_complete;
    public static int[][] tt_commitf;
    public static int[][] tt_result;
    public static int[][] tt_comr;
    public static int[][] tt_commit;
    public static int[][] tt_comm;
    public static int[][] tt_res;
    public static int[][] tt_mem;
    public static String[][] tt_ins;
    public static int[][] ins_num;
    public static String[][] rob_r;
    public static double[][] rob_v;
    public static int[][] rob_finish;
    public static int[][] rob_pc;
    public static String[][] rat;
    public static String[][] rati;
    public static double[][] arf;
    public static int[][] arfi;

    public static int[][] ld_time;
    public static int[][] ld1_time;
    public static int[][] ld_address;
    public static int[][] ld_busy;
    public static int[][] ld_busy1;
    public static int[][] ld_pc;
    public static int[][] ld_q;
    public static int[][] ld_vjt;
    public static int[][] ld_qj;
    public static int[][] ld_vj;
    public static int[][] ld_op;
    public static int[][] ld_vkt;
    public static int[][] ld_vk;
    public static int[][] ld_qk;
    public static int[][] ld_v1t;
    public static double[][] ld_v1;
    public static int[][] ld_q1;
    public static int[][] ld_seq;
    public static int[][] rs_time;
    public static int[][] rs_busy;
    public static int[][] rs_busy1;
    public static int[][] rs_op;
    public static double[][] rs_vj;
    public static double[][] rs_vk;
    public static int[][] rs_vjt;
    public static int[][] rs_vkt;
    public static int[][] rs_qj;
    public static int[][] rs_qk;
    public static int[][] rs_pc;
    public static int[][] rsi_time;
    public static int[][] rsi_busy;
    public static int[][] rsi_busy1;
    public static int[][] rsi_op;
    public static int[][] rsi_vj;
    public static int[][] rsi_vk;
    public static int[][] rsi_vjt;
    public static int[][] rsi_vkt;
    public static int[][] rsi_qj;
    public static int[][] rsi_qk;
    public static int[][] rsi_pc;

    public static int[][] da_f;
    public static int[][] da_fi;
    public static double[][] da_fd;
    public static int[][] da_fdi;
    public static int[][] da_ff;
    public static int[][] da_fe;
    public static int[][] da_fei;

    public static double[][] mem;

    public static int addrsi;
    public static int addexi;
    public static int addrs;
    public static int addex;
    public static int multrs;
    public static int multex;
    public static int ldrs;
    public static int ldex;
    public static int memt;
}
