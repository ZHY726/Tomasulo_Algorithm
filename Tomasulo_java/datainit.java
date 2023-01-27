import java.util.*;

public class datainit{
	data tomdata;
	public void init(){
		tomdata.addrsi = 2;
		tomdata.addexi = 1;
		tomdata.addrs = 3;
		tomdata.addex = 3;
		tomdata.multrs = 3;
		tomdata.multex = 20;
		tomdata.ldrs = 4;
		tomdata.ldex = 1;
		tomdata.memt = 4;

		tomdata.pc = 0;
		tomdata.clock = 0;
		tomdata.seq = 0;
		tomdata.v = 0;
		tomdata.rob = 0;
		tomdata.addr = 0;
		tomdata.J = new int[100];

		tomdata.cdb = new LinkedList<>();
		tomdata.memo = new LinkedList<>();
		tomdata.memot = new int[1000];

		tomdata.ins_op = new int[5];
		tomdata.ins_ope = new int[1000];
		tomdata.ins_f1 = new int[1000];
		tomdata.ins_f2 = new int[1000];
		tomdata.ins_f3 = new int[1000];
		tomdata.ins_f4 = new int[1000];

		tomdata.lsq_op = new int[128];
		tomdata.lsq_address = new int[128];
		tomdata.lsq_value = new double[128];
		tomdata.lsq_as = new int[128];
		tomdata.lsq_vs = new int[128];
		tomdata.lsq_pc = new int[128];
		tomdata.mem_time = new int[128];
		
		tomdata.tt_issue = new int[1000];
		tomdata.tt_exe = new int[1000];
		tomdata.tt_exew = new int[1000];
		tomdata.tt_exes = new int[1000];
		tomdata.tt_complete = new int[1000];
		tomdata.tt_commitf = new int[1000];
		tomdata.tt_result = new int[1000];
		tomdata.tt_res = new int[1000];
		tomdata.tt_comr = new int[1000];
		tomdata.tt_commit = new int[1000];
		tomdata.tt_comm = new int[1000];
		tomdata.tt_mem = new int[1000];
		tomdata.tt_ins = new String[1000];
		tomdata.tt_num = new int[1000];
		for(int i = 0;i < 1000;i ++){
			tomdata.tt_comr[i] = 0;
		}

		tomdata.rob_r = new String[1000];
		tomdata.rob_v = new double[1000];
		tomdata.rob_finish = new int[1000];
		tomdata.rob_pc = new int[1000];

		tomdata.rat = new String[30];
		tomdata.rati = new String[30];
		tomdata.arf = new double[30];
		tomdata.arfi = new int[30];
		
		tomdata.ld_time = new int[tomdata.ldrs];
		tomdata.ld1_time = new int[tomdata.ldrs];
		tomdata.ld_address = new int[tomdata.ldrs];
		tomdata.ld_busy = new int[tomdata.ldrs];
		tomdata.ld_busy1 = new int[tomdata.ldrs];
		tomdata.ld_pc = new int[tomdata.ldrs];
		tomdata.ld_q = new int[tomdata.ldrs];
		tomdata.ld_vjt = new int[tomdata.ldrs];
		tomdata.ld_qj = new int[tomdata.ldrs];
		tomdata.ld_vj = new int[tomdata.ldrs];
		tomdata.ld_op = new int[tomdata.ldrs];
		tomdata.ld_vkt = new int[tomdata.ldrs];
		tomdata.ld_vk = new int[tomdata.ldrs];
		tomdata.ld_qk = new int[tomdata.ldrs];
		tomdata.ld_v1t = new int[tomdata.ldrs];
		tomdata.ld_v1 = new double[tomdata.ldrs];
		tomdata.ld_q1 = new int[tomdata.ldrs];
		tomdata.ld_seq = new int[tomdata.ldrs];

		int sum = tomdata.addrs + tomdata.multrs;
		
		tomdata.rs_time = new int[sum];
		tomdata.rs_busy = new int[sum];
		tomdata.rs_busy1 = new int[sum];
		tomdata.rs_op = new int[sum];
		tomdata.rs_vj = new double[sum];
		tomdata.rs_vk = new double[sum];
		tomdata.rs_vjt = new int[sum];
		tomdata.rs_vkt = new int[sum];
		tomdata.rs_qj = new int[sum];
		tomdata.rs_qk = new int[sum];
		tomdata.rs_pc = new int[sum];

		tomdata.rsi_time = new int[tomdata.addrsi];
		tomdata.rsi_busy = new int[tomdata.addrsi];
		tomdata.rsi_busy1 = new int[tomdata.addrsi];
		tomdata.rsi_op = new int[tomdata.addrsi];
		tomdata.rsi_vj = new int[tomdata.addrsi];
		tomdata.rsi_vk = new int[tomdata.addrsi];
		tomdata.rsi_vjt = new int[tomdata.addrsi];
		tomdata.rsi_vkt = new int[tomdata.addrsi];
		tomdata.rsi_qj = new int[tomdata.addrsi];
		tomdata.rsi_qk = new int[tomdata.addrsi];
		tomdata.rsi_pc = new int[tomdata.addrsi];
		
		tomdata.da_f = new int[30];
		tomdata.da_fi = new int[30];
		tomdata.da_fd = new double[30];
		tomdata.da_fdi = new int[30];
		tomdata.da_ff = new int[30];
		tomdata.da_fe = new int[30];
		tomdata.da_fei = new int[30];

		tomdata.mem = new double[4096];
		for(int i = 0;i < 4096;i ++){
			tomdata.mem[i] = 0;
		}
	}
}