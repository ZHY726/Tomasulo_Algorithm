public class btbinit {
    btbdata tombtb;

    public void init() {
        tombtb.btb_id = new int[8];
        tombtb.btb_Pc = new int[8];
        tombtb.btb_target = new int[8];
        tombtb.M = new int[100];

        for (int i = 0; i < 8; i++) {
            tombtb.btb_Pc[i]=0;
        }
    }

    public void add(int addr,int tar, btbdata c) {
        for (int i = 0; i < 8; i++) {
            if (c.btb_Pc[i] == 0) {
                c.btb_id[i] = i;
                c.btb_Pc[i] = addr;
                c.btb_target[i] = tar;
                break;
            }
        }
    }
    public int get(data d, btbdata c){
        for(int i=0;i<8;i++){
            if(d.addr==c.btb_Pc[i])
                return i+1;
        }
        return 0;
    }
    public void del(int addr,btbdata c){
        for(int i=0;i<8;i++){
            if(c.btb_Pc[i]==addr){
                c.btb_id[i]=0;
                c.btb_Pc[i]=0;
                c.btb_target[i]=0;
                break;
            }
        }
    }
}
