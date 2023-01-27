import java.util.LinkedList;

public class copyinit {
    copy tom;
    public void init2(){
        tom.addrsi = 2;
        tom.addexi = 1;
        tom.addrs = 2;
        tom.addex = 3;
        tom.multrs = 1;
        tom.multex = 20;
        tom.ldrs = 3;
        tom.ldex = 1;
        tom.memt = 4;

        tom.pc = new int[100];
        tom.clock = 0;
        tom.seq = new int[100];
        tom.v = new int[100];
        tom.rob = new int[100];
        tom.addr = new int[100];
        tom.J = new int[100][100];


        tom.memot = new int[1000][100];
        tom.a = new data[100];

        tom.ins_op = new int[4][100];
        tom.ins_ope = new int[1000][100];
        tom.ins_f1 = new int[1000][100];
        tom.ins_f2 = new int[1000][100];
        tom.ins_f3 = new int[1000][100];
        tom.ins_f4 = new int[1000][100];

        tom.lsq_op = new int[128][100];
        tom.lsq_address = new int[128][100];
        tom.lsq_value = new double[128][100];
        tom.lsq_as = new int[128][100];
        tom.lsq_vs = new int[128][100];
        tom.lsq_pc = new int[128][100];
        tom.mem_time = new int[128][100];

        tom.tt_issue = new int[1000][100];
        tom.tt_exe = new int[1000][100];
        tom.tt_exew = new int[1000][100];
        tom.tt_exes = new int[1000][100];
        tom.tt_complete = new int[1000][100];
        tom.tt_commitf = new int[1000][100];
        tom.tt_result = new int[1000][100];
        tom.tt_res = new int[1000][100];
        tom.tt_comr = new int[1000][100];
        tom.tt_commit = new int[1000][100];
        tom.tt_comm = new int[1000][100];
        tom.tt_mem = new int[1000][100];
        tom.tt_ins = new String[1000][100];
        tom.ins_num = new int[1000][100];
        for(int i = 0;i < 1000;i ++){
            for(int j = 0;j < 100;j ++) {
                tom.tt_comr[i][j] = 0;
            }
        }

        tom.rob_r = new String[1000][100];
        tom.rob_v = new double[1000][100];
        tom.rob_finish = new int[1000][100];
        tom.rob_pc = new int[1000][100];

        tom.rat = new String[30][100];
        tom.rati = new String[30][100];
        tom.arf = new double[30][100];
        tom.arfi = new int[30][100];

        tom.ld_time = new int[tom.ldrs][100];
        tom.ld1_time = new int[tom.ldrs][100];
        tom.ld_address = new int[tom.ldrs][100];
        tom.ld_busy = new int[tom.ldrs][100];
        tom.ld_busy1 = new int[tom.ldrs][100];
        tom.ld_pc = new int[tom.ldrs][100];
        tom.ld_q = new int[tom.ldrs][100];
        tom.ld_vjt = new int[tom.ldrs][100];
        tom.ld_qj = new int[tom.ldrs][100];
        tom.ld_vj = new int[tom.ldrs][100];
        tom.ld_op = new int[tom.ldrs][100];
        tom.ld_vkt = new int[tom.ldrs][100];
        tom.ld_vk = new int[tom.ldrs][100];
        tom.ld_qk = new int[tom.ldrs][100];
        tom.ld_v1t = new int[tom.ldrs][100];
        tom.ld_v1 = new double[tom.ldrs][100];
        tom.ld_q1 = new int[tom.ldrs][100];
        tom.ld_seq = new int[tom.ldrs][100];

        int sum = tom.addrs + tom.multrs;

        tom.rs_time = new int[sum][100];
        tom.rs_busy = new int[sum][100];
        tom.rs_busy1 = new int[sum][100];
        tom.rs_op = new int[sum][100];
        tom.rs_vj = new double[sum][100];
        tom.rs_vk = new double[sum][100];
        tom.rs_vjt = new int[sum][100];
        tom.rs_vkt = new int[sum][100];
        tom.rs_qj = new int[sum][100];
        tom.rs_qk = new int[sum][100];
        tom.rs_pc = new int[sum][100];

        tom.rsi_time = new int[tom.addrsi][100];
        tom.rsi_busy = new int[tom.addrsi][100];
        tom.rsi_busy1 = new int[tom.addrsi][100];
        tom.rsi_op = new int[tom.addrsi][100];
        tom.rsi_vj = new int[tom.addrsi][100];
        tom.rsi_vk = new int[tom.addrsi][100];
        tom.rsi_vjt = new int[tom.addrsi][100];
        tom.rsi_vkt = new int[tom.addrsi][100];
        tom.rsi_qj = new int[tom.addrsi][100];
        tom.rsi_qk = new int[tom.addrsi][100];
        tom.rsi_pc = new int[tom.addrsi][100];

        tom.da_f = new int[30][100];
        tom.da_fi = new int[30][100];
        tom.da_fd = new double[30][100];
        tom.da_fdi = new int[30][100];
        tom.da_ff = new int[30][100];
        tom.da_fe = new int[30][100];
        tom.da_fei = new int[30][100];

        for(int i = 0;i < 30;i ++){
            for(int j = 0;j < 100;j ++) {
                tom.da_f[i][j] = 0;
                tom.da_ff[i][j] = 0;
            }
        }
        tom.mem = new double[4096][100];
        for(int i = 0;i < 4096;i ++){
            for(int j = 0;j < 100;j ++) {
                tom.mem[i][j] = 0;
            }
        }
    }

    public void record(copy r,data d,int t){
        r.pc[t] = d.pc;
        r.seq[t] = d.seq;
        r.v[t] = d.v;
        r.rob[t] = d.rob;
        r.addr[t] = d.addr;

        for(int i=0;i<100;i++) {
            r.J[t] = d.J;
        }

        while(!r.a[t].cdb.isEmpty()){
            r.a[t].cdb.poll();
        }
        r.a[t].cdb.addAll(d.cdb);
        while(!r.a[t].memo.isEmpty()){
            r.a[t].memo.poll();
        }
        r.a[t].memo.addAll((d.memo));


        for(int i=0;i<4;i++) {
            r.ins_op[i][t] = d.ins_op[i];
        }

        for(int i=0;i<128;i++) {
            r.lsq_op[i][t] = d.lsq_op[i];
            r.lsq_address[i][t] = d.lsq_address[i];
            r.lsq_value[i][t] = d.lsq_value[i];
            r.lsq_as[i][t] = d.lsq_as[i];
            r.lsq_vs[i][t] = d.lsq_vs[i];
            r.lsq_pc[i][t] = d.lsq_pc[i];
            r.mem_time[i][t] = d.mem_time[i];
        }

        for(int i=0;i<1000;i++) {
            r.memot[i][t] = d.memot[i];

            r.ins_ope[i][t] = d.ins_ope[i];
            r.ins_f1[i][t] = d.ins_f1[i];
            r.ins_f2[i][t] = d.ins_f2[i];
            r.ins_f3[i][t] = d.ins_f3[i];
            r.ins_f4[i][t] = d.ins_f4[i];

            r.tt_issue[i][t] = d.tt_issue[i];
            r.tt_exe[i][t] = d.tt_exe[i];
            r.tt_exew[i][t] = d.tt_exew[i];
            r.tt_exes[i][t] = d.tt_exes[i];
            r.tt_complete[i][t] = d.tt_complete[i];
            r.tt_result[i][t] = d.tt_result[i];
            r.tt_res[i][t] = d.tt_res[i];
            r.tt_comr[i][t] = d.tt_comr[i];
            r.tt_commit[i][t] = d.tt_commit[i];
            r.tt_comm[i][t] = d.tt_comm[i];
            r.tt_mem[i][t] = d.tt_mem[i];
            r.tt_ins[i][t] = d.tt_ins[i];
            r.ins_num[i][t] = d.tt_num[i];

            r.rob_r[i][t] = d.rob_r[i];
            r.rob_v[i][t] = d.rob_v[i];
            r.rob_finish[i][t] = d.rob_finish[i];
            r.rob_pc[i][t] = d.rob_pc[i];
        }

        for(int i=0;i<30;i++) {
            r.rat[i][t] = d.rat[i];
            r.rati[i][t] = d.rati[i];
            r.arf[i][t] = d.arf[i];
            r.arfi[i][t] = d.arfi[i];
        }

        for(int i=0;i<tom.ldrs;i++) {
            r.ld_time[i][t] = d.ld_time[i];
            r.ld1_time[i][t] = d.ld1_time[i];
            r.ld_address[i][t] = d.ld_address[i];
            r.ld_busy[i][t] = d.ld_busy[i];
            r.ld_busy1[i][t] = d.ld_busy1[i];
            r.ld_pc[i][t] = d.ld_pc[i];
            r.ld_q[i][t] = d.ld_q[i];
            r.ld_vjt[i][t] = d.ld_vjt[i];
            r.ld_qj[i][t] = d.ld_qj[i];
            r.ld_vj[i][t] = d.ld_vj[i];
            r.ld_op[i][t] = d.ld_op[i];
            r.ld_vkt[i][t] = d.ld_vkt[i];
            r.ld_vk[i][t] = d.ld_vk[i];
            r.ld_qk[i][t] = d.ld_qk[i];
            r.ld_v1t[i][t] = d.ld_v1t[i];
            r.ld_v1[i][t] = d.ld_v1[i];
            r.ld_q1[i][t] = d.ld_q1[i];
            r.ld_seq[i][t] = d.ld_seq[i];
        }

        for(int i=0;i<tom.addrs+tom.multrs;i++) {
            r.rs_time[i][t] = d.rs_time[i];
            r.rs_busy[i][t] = d.rs_busy[i];
            r.rs_busy1[i][t] = d.rs_busy1[i];
            r.rs_op[i][t] = d.rs_op[i];
            r.rs_vj[i][t] = d.rs_vj[i];
            r.rs_vk[i][t] = d.rs_vk[i];
            r.rs_vjt[i][t] = d.rs_vjt[i];
            r.rs_vkt[i][t] = d.rs_vkt[i];
            r.rs_qj[i][t] = d.rs_qj[i];
            r.rs_qk[i][t] = d.rs_qk[i];
            r.rs_pc[i][t] = d.rs_pc[i];
        }

        for(int i=0;i<tom.addrsi;i++) {
            r.rsi_time[i][t] = d.rsi_time[i];
            r.rsi_busy[i][t] = d.rsi_busy[i];
            r.rsi_busy1[i][t] = d.rsi_busy1[i];
            r.rsi_op[i][t] = d.rsi_op[i];
            r.rsi_vj[i][t] = d.rsi_vj[i];
            r.rsi_vk[i][t] = d.rsi_vk[i];
            r.rsi_vjt[i][t] = d.rsi_vjt[i];
            r.rsi_vkt[i][t] = d.rsi_vkt[i];
            r.rsi_qj[i][t] = d.rsi_qj[i];
            r.rsi_qk[i][t] = d.rsi_qk[i];
            r.rsi_pc[i][t] = d.rsi_pc[i];
        }

        for(int i=0;i<30;i++) {
            r.da_f[i][t] = d.da_f[i];
            r.da_fi[i][t] = d.da_fi[i];
            r.da_fd[i][t] = d.da_fd[i];
            r.da_fdi[i][t] = d.da_fdi[i];
            r.da_ff[i][t] = d.da_ff[i];
            r.da_fe[i][t] = d.da_fe[i];
            r.da_fei[i][t] = d.da_fei[i];
        }
    }

    public void back(data d,copy r, int t){
        d.pc = r.pc[t];
        d.seq = r.seq[t];
        d.v = r.v[t];
        d.rob = r.rob[t];
        d.addr = r.addr[t];

        for(int i=0;i<100;i++) {
            d.J = r.J[t];
        }

        while(!d.cdb.isEmpty()){
            d.cdb.poll();
        }
        d.cdb.addAll(r.a[t].cdb);
        while(!d.memo.isEmpty()){
            d.memo.poll();
        }
        d.memo.addAll((r.a[t].memo));

        for(int i=0;i<4;i++) {
            d.ins_op[i] = r.ins_op[i][t];
        }

        for(int i=0;i<128;i++) {
            d.lsq_op[i] = r.lsq_op[i][t];
            d.lsq_address[i] = r.lsq_address[i][t];
            d.lsq_value[i] = r.lsq_value[i][t];
            d.lsq_as[i] = r.lsq_as[i][t];
            d.lsq_vs[i] = r.lsq_vs[i][t];
            d.lsq_pc[i] = r.lsq_pc[i][t];
            d.mem_time[i] = r.mem_time[i][t];
        }

        for(int i=0;i<1000;i++) {
            d.memot[i] = r.memot[i][t];

            d.ins_ope[i] = r.ins_ope[i][t];
            d.ins_f1[i] = r.ins_f1[i][t];
            d.ins_f2[i] = r.ins_f2[i][t];
            d.ins_f3[i] = r.ins_f3[i][t];
            d.ins_f4[i] = r.ins_f4[i][t];

            d.tt_issue[i] = r.tt_issue[i][t];
            d.tt_exe[i] = r.tt_exe[i][t];
            d.tt_exew[i] = r.tt_exew[i][t];
            d.tt_exes[i] = r.tt_exes[i][t];
            d.tt_complete[i] = r.tt_complete[i][t];
            d.tt_result[i] = r.tt_result[i][t];
            d.tt_res[i] = r.tt_res[i][t];
            d.tt_comr[i] = r.tt_comr[i][t];
            d.tt_commit[i] = r.tt_commit[i][t];
            d.tt_comm[i] = r.tt_comm[i][t];
            d.tt_mem[i] = r.tt_mem[i][t];
            d.tt_ins[i] = r.tt_ins[i][t];
            d.tt_num[i] = r.ins_num[i][t];

            d.rob_r[i] = r.rob_r[i][t];
            d.rob_v[i] = r.rob_v[i][t];
            d.rob_finish[i] = r.rob_finish[i][t];
            d.rob_pc[i] = r.rob_pc[i][t];
        }

        for(int i=0;i<30;i++) {
            d.rat[i] = r.rat[i][t];
            d.rati[i] = r.rati[i][t];
            d.arf[i] = r.arf[i][t];
            d.arfi[i] = r.arfi[i][t];
        }

        for(int i=0;i<tom.ldrs;i++) {
            d.ld_time[i] = r.ld_time[i][t];
            d.ld1_time[i] = r.ld1_time[i][t];
            d.ld_address[i] = r.ld_address[i][t];
            d.ld_busy[i] = r.ld_busy[i][t];
            d.ld_busy1[i] = r.ld_busy1[i][t];
            d.ld_pc[i] = r.ld_pc[i][t];
            d.ld_q[i] = r.ld_q[i][t];
            d.ld_vjt[i] = r.ld_vjt[i][t];
            d.ld_qj[i] = r.ld_qj[i][t];
            d.ld_vj[i] = r.ld_vj[i][t];
            d.ld_op[i] = r.ld_op[i][t];
            d.ld_vkt[i] = r.ld_vkt[i][t];
            d.ld_vk[i] = r.ld_vk[i][t];
            d.ld_qk[i] = r.ld_qk[i][t];
            d.ld_v1t[i] = r.ld_v1t[i][t];
            d.ld_v1[i] = r.ld_v1[i][t];
            d.ld_q1[i] = r.ld_q1[i][t];
            d.ld_seq[i] = r.ld_seq[i][t];
        }

        for(int i=0;i<tom.addrs+tom.multrs;i++) {
            d.rs_time[i] = r.rs_time[i][t];
            d.rs_busy[i] = r.rs_busy[i][t];
            d.rs_busy1[i] = r.rs_busy1[i][t];
            d.rs_op[i] = r.rs_op[i][t];
            d.rs_vj[i] = r.rs_vj[i][t];
            d.rs_vk[i] = r.rs_vk[i][t];
            d.rs_vjt[i] = r.rs_vjt[i][t];
            d.rs_vkt[i] = r.rs_vkt[i][t];
            d.rs_qj[i] = r.rs_qj[i][t];
            d.rs_qk[i] = r.rs_qk[i][t];
            d.rs_pc[i] = r.rs_pc[i][t];
        }

        for(int i=0;i<tom.addrsi;i++) {
            d.rsi_time[i] = r.rsi_time[i][t];
            d.rsi_busy[i] = r.rsi_busy[i][t];
            d.rsi_busy1[i] = r.rsi_busy1[i][t];
            d.rsi_op[i] = r.rsi_op[i][t];
            d.rsi_vj[i] = r.rsi_vj[i][t];
            d.rsi_vk[i] = r.rsi_vk[i][t];
            d.rsi_vjt[i] = r.rsi_vjt[i][t];
            d.rsi_vkt[i] = r.rsi_vkt[i][t];
            d.rsi_qj[i] = r.rsi_qj[i][t];
            d.rsi_qk[i] = r.rsi_qk[i][t];
            d.rsi_pc[i] = r.rsi_pc[i][t];
        }

        for(int i=0;i<30;i++) {
            d.da_f[i] = r.da_f[i][t];
            d.da_fi[i] = r.da_fi[i][t];
            d.da_fd[i] = r.da_fd[i][t];
            d.da_fdi[i] = r.da_fdi[i][t];
            d.da_ff[i] = r.da_ff[i][t];
            d.da_fe[i] = r.da_fe[i][t];
            d.da_fei[i] = r.da_fei[i][t];
        }
    }
}
