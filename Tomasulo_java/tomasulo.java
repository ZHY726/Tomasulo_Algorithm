import java.math.BigDecimal;

public class tomasulo{

	static btbinit B = new btbinit();
	static copyinit C = new copyinit();
	public static void go(data d, copy r, btbdata c) {

		for(int i = 0; i < 30; i++){
			d.da_fe[i] = 0;
			d.da_fei[i] = 0;
		}

		double[] fud = new double[30];
		int[] fu = new int[30];
		int[] fui = new int[30];
		int[] fudi = new int[30];
		for (int i = 0; i < 30; i++) {
			fu[i] = d.da_f[i];
			fui[i] = d.da_fi[i];
			fud[i] = d.da_fd[i];
			fudi[i] = d.da_fdi[i];
		}
		int[] tt_com = new int[1000];
		for (int i = 0; i < d.pc; i++) {
			tt_com[i] = d.tt_comr[i];
			if(d.tt_num[i] != 2) {
				d.tt_comr[i] = 0;
			}
		}
		int com = 0;

		int[][] run_order = new int[d.addrs + d.multrs + d.addrsi + d.ldrs][4];
		for (int i = 0; i < d.addrs + d.multrs; i++) {
			run_order[i][0] = i;
			if (d.rs_busy[i] == 1) {
				run_order[i][1] = d.rs_pc[i] + 1;
			} else {
				run_order[i][1] = 0;
			}
		}
		for (int i = 0; i < d.addrsi; i++) {
			int g = d.addrs + d.multrs + d.ldrs;
			run_order[g + i][0] = g + i;
			if (d.rsi_busy[i] == 1) {
				run_order[g + i][1] = d.rsi_pc[i] + 1;
			} else {
				run_order[g + i][1] = 0;
			}
		}
		for (int i = 0; i < d.ldrs; i++) {
			int g = d.addrs + d.multrs;
			run_order[g + i][0] = g + i;
			if (d.ld_busy[i] == 1) {
				run_order[g + i][1] = d.ld_pc[i] + 1;
				run_order[g + i][3] = d.ld_seq[i];
			} else {
				run_order[g + i][1] = 0;
			}
		}

		for (int i = 0; i < d.addrs + d.multrs + d.addrsi + d.ldrs; i++) {
			for (int j = 0; j < d.addrs + d.multrs + d.addrsi + d.ldrs - 1; j++) {
				if (run_order[j][1] > run_order[j + 1][1]) {
					int temp;
					temp = run_order[j][1];
					run_order[j][1] = run_order[j + 1][1];
					run_order[j + 1][1] = temp;
					temp = run_order[j][0];
					run_order[j][0] = run_order[j + 1][0];
					run_order[j + 1][0] = temp;
					temp = run_order[j][3];
					run_order[j][3] = run_order[j + 1][3];
					run_order[j + 1][3] = temp;
				}
			}
		}

		for (int i = 0; i < d.addrs + d.multrs + d.addrsi + d.ldrs; i++) {
			if (run_order[i][0] < d.addrs + d.multrs) {//rs
				if (d.rs_busy[run_order[i][0]] == 1) {
					if (d.rs_time[run_order[i][0]] != 0) {
						if (d.tt_exe[run_order[i][1] - 1] == 0) {
							d.tt_exe[run_order[i][1] - 1] = d.clock;
						}
						d.rs_time[run_order[i][0]]--;
						if (d.rs_time[run_order[i][0]] == 0) {
							d.tt_comr[run_order[i][1] - 1] = 1;
							d.tt_complete[run_order[i][1] - 1] = d.clock;
							for (int k = 0; k < 30; k++) {
								if (fu[k] / 10 > 2) {
									if (fu[k] % 10 == run_order[i][0]) {
										d.da_f[k] = 0;
										d.da_fe[k] = 1;
										if (fu[k] / 10 == 3) {
											BigDecimal numberA = new BigDecimal(Double.toString(d.rs_vj[run_order[i][0]]));
											BigDecimal numberB = new BigDecimal(Double.toString(d.rs_vk[run_order[i][0]]));
											d.da_fd[k] = numberA.add(numberB).doubleValue();
										} else if (fu[k] / 10 == 4) {
											BigDecimal numberA = new BigDecimal(Double.toString(d.rs_vj[run_order[i][0]]));
											BigDecimal numberB = new BigDecimal(Double.toString(d.rs_vk[run_order[i][0]]));
											d.da_fd[k] = numberA.subtract(numberB).doubleValue();
										} else if (fu[k] / 10 == 5) {
											BigDecimal numberA = new BigDecimal(Double.toString(d.rs_vj[run_order[i][0]]));
											BigDecimal numberB = new BigDecimal(Double.toString(d.rs_vk[run_order[i][0]]));
											d.da_fd[k] = numberA.multiply(numberB).doubleValue();
										}
									}
								}
							}
						}
					} else {
						if (d.rs_vjt[run_order[i][0]] == 1 || d.rs_vkt[run_order[i][0]] == 1) {
							if (fu[d.rs_qj[run_order[i][0]]] == 0 && d.rs_vjt[run_order[i][0]] == 1) {
								d.rs_vjt[run_order[i][0]] = 0;
								d.rs_vj[run_order[i][0]] = fud[d.rs_qj[run_order[i][0]]];
							}
							if (fu[d.rs_qk[run_order[i][0]]] == 0 && d.rs_vkt[run_order[i][0]] == 1) {
								d.rs_vkt[run_order[i][0]] = 0;
								d.rs_vk[run_order[i][0]] = fud[d.rs_qk[run_order[i][0]]];
							}
							if (d.rs_vjt[run_order[i][0]] == 0 && d.rs_vkt[run_order[i][0]] == 0) {
								if (d.rs_op[run_order[i][0]] == 3) {
									d.rs_time[run_order[i][0]] = d.addex;
								} else if (d.rs_op[run_order[i][0]] == 4) {
									d.rs_time[run_order[i][0]] = d.addex;
								} else if (d.rs_op[run_order[i][0]] == 5) {
									d.rs_time[run_order[i][0]] = d.multex;
								}
							}
						} else {
							d.rs_busy1[run_order[i][0]] = 1;
						}
					}
				}
			} else if (run_order[i][0] > d.addrs + d.multrs + d.ldrs - 1) {
				int g = run_order[i][0] - (d.addrs + d.multrs +  d.ldrs);
				if (d.rsi_busy[g] == 1) {
					if (d.rsi_time[g] != 0) {
						if (d.tt_exe[run_order[i][1] - 1] == 0) {
							d.tt_exe[run_order[i][1] - 1] = d.clock;
						}
						d.rsi_time[g]--;
						if (d.rsi_time[g] == 0) {
							d.tt_comr[run_order[i][1] - 1] = 1;
							d.tt_complete[run_order[i][1] - 1] = d.clock;
							for (int k = 0; k < 30; k++) {
								if (fui[k] / 10 > 5) {
									if (fui[k] % 10 == g) {
										d.da_fi[k] = 0;
										d.da_fei[k] = 1;
										if (fui[k] / 10 == 6) {
											d.da_fdi[k] = d.rsi_vj[g] + d.rsi_vk[g];
										} else if (fui[k] / 10 == 7) {
											d.da_fdi[k] = d.rsi_vj[g] - d.rsi_vk[g];
										} else if (fui[k] / 10 == 8) {
											d.da_fdi[k] = d.rsi_vj[g] + d.rsi_vk[g];
										} else if (fui[k] / 10 == 9) {
											if(d.rsi_vj[g] == d.rsi_vk[g]){
												d.J[run_order[i][1] - 1] = 1;
											}else{
												d.J[run_order[i][1] - 1] = 2;
											}
											d.tt_res[run_order[i][1] - 1] = 1;
										} else if (fui[k] / 10 == 10) {
											if(d.rsi_vj[g] != d.rsi_vk[g]){
												d.J[run_order[i][1] - 1] = 1;
											}else{
												d.J[run_order[i][1] - 1] = 2;
											}
											d.tt_res[run_order[i][1] - 1] = 1;
										}
									}
								}
							}
						}
					} else {
						if (d.rsi_vjt[g] == 1 || d.rsi_vkt[g] == 1) {
							if (fui[d.rsi_qj[g]] == 0 && d.rsi_vjt[g] == 1) {
								d.rsi_vjt[g] = 0;
								d.rsi_vj[g] = fudi[d.rsi_qj[g]];
							}
							if (fui[d.rsi_qk[g]] == 0 && d.rsi_vkt[g] == 1) {
								d.rsi_vkt[g] = 0;
								d.rsi_vk[g] = fudi[d.rsi_qk[g]];
							}
							if (d.rsi_vjt[g] == 0 && d.rsi_vkt[g] == 0) {
								if (d.rsi_op[g] == 6) {
									d.rsi_time[g] = d.addexi;
								} else if (d.rsi_op[g] == 7) {
									d.rsi_time[g] = d.addexi;
								} else if (d.rsi_op[g] == 8) {
									d.rsi_time[g] = d.addexi;
								} else if (d.rsi_op[g] == 9) {
									d.rsi_time[g] = d.addexi;
								} else if (d.rsi_op[g] == 10) {
									d.rsi_time[g] = d.addexi;
								}
							}
						} else {
							d.rsi_busy1[g] = 1;
						}
					}
				}
			} else if (run_order[i][0] < d.addrs + d.multrs + d.ldrs) {//ld
				int g = run_order[i][0] - (d.addrs + d.multrs);
				if (d.ld_busy[g] == 1) {
					if (d.ld_time[g] != 0) {
						if (d.tt_exe[run_order[i][1] - 1] == 0) {
							d.tt_exe[run_order[i][1] - 1] = d.clock;
							d.tt_exes[run_order[i][1] - 1] = 1;
						}
						d.ld_time[g]--;
						if (d.ld_time[g] == 0) {
							for (int k = 0; k < 30; k++) {
								if (fu[k] / 10 > 0) {
									if (fu[k] % 10 == g) {
										d.da_f[k] = 0;
										d.da_fe[k] = 1;
										if (fu[k] / 10 == 1) {
											d.lsq_address[run_order[i][3]] = d.ld_vj[g] + d.ld_vk[g];
											d.lsq_as[run_order[i][3]] = 1;
										} else if (fu[k] / 10 == 2) {
											d.lsq_address[run_order[i][3]] = d.ld_vj[g] + d.ld_vk[g];
											d.lsq_value[run_order[i][3]] = d.da_fd[k];
											d.tt_comr[run_order[i][1] - 1] = 1;
										}
									}
								}
							}
						}
					} else {
						if (d.ld_vjt[g] == 1 || d.ld_v1t[g] == 1) {
							if (fui[d.ld_qj[g]] == 0 && d.ld_vjt[g] == 1) {
								d.ld_vjt[g] = 0;
								d.ld_vj[g] = fudi[d.ld_qj[g]];
							}
							if (fu[d.ld_q1[g]] == 0 && d.ld_v1t[g] == 1) {
								d.ld_v1t[g] = 0;
								d.ld_v1[g] = fud[d.ld_q1[g]];
							}
							if (d.ld_vjt[g] == 0 && d.ld_v1t[g] == 0) {
								if (d.ld_op[g] == 1) {
									d.ld_time[g] = d.memt + d.ldex;
								} else if (d.ld_op[g] == 2) {
									d.ld_time[g] = d.memt;
								}
							}
						} else {
							d.ld_busy1[g] = 1;
						}
					}
				}
			}
		}

		int addr=0;
		boolean R=false;
		int x[]=new int[100];

		for (int i = 0; i < d.addrs + d.multrs + d.addrsi + d.ldrs; i++) {
			if(run_order[i][1] - 1 >= 0) {
				if (c.M[run_order[i][1] - 1] == 0) {
					x[run_order[i][1] - 1] = 1;
				} else if (c.M[run_order[i][1] - 1] == 1) {
					if (d.J[run_order[i][1] - 1] == 0)
						x[run_order[i][1] - 1] = 2;
					else if (d.J[run_order[i][1] - 1] == 1)
						x[run_order[i][1] - 1] = 3;
					else if (d.J[run_order[i][1] - 1] == 2)
						x[run_order[i][1] - 1] = 4;
				} else if (c.M[run_order[i][1] - 1] == 2) {
					if (d.J[run_order[i][1] - 1] == 0)
						x[run_order[i][1] - 1] = 5;
					else if (d.J[run_order[i][1] - 1] == 1)
						x[run_order[i][1] - 1] = 6;
					else if (d.J[run_order[i][1] - 1] == 2)
						x[run_order[i][1] - 1] = 7;
				}
			}
		}

		switch (d.ins_op[0]) {
			case 1://ld
				for (int i = 0; i < d.ldrs; i++) {
					if (d.ld_busy[i] == 0) {
						d.ld_busy[i] = 1;
						d.ld_op[i] = 1;
						d.da_f[d.ins_op[1]] = 1 * 10 + i;
						d.ld_seq[i] = d.seq;
						d.lsq_op[d.seq] = 1;
						d.lsq_pc[d.seq] = d.pc;

						if (d.da_fi[d.ins_op[2]] != 0) {
							d.ld_vjt[i] = 1;
							d.ld_qj[i] = d.ins_op[2];
						} else {
							d.ld_vjt[i] = 0;
							d.ld_vj[i] = d.da_fdi[d.ins_op[2]];
						}
						d.ld_v1t[i] = 0;
						d.ld_v1[i] = d.ins_op[1];

						d.ld_vkt[i] = 0;
						d.ld_vk[i] = d.ins_op[3];

						if (d.ld_vjt[i] == 0 && d.ld_vkt[i] == 0 && d.ld_v1t[i] == 0) {
							if(d.da_fei[d.ins_op[2]] == 1) {
								d.ld_time[i] = d.memt + 2;
								d.tt_exe[d.pc] = d.clock + 2;
							}else{
								d.ld_time[i] = d.memt + 1;
							}
						} else {
							d.ld_time[i] = 0;
						}
						d.ld_pc[i] = d.pc;
						d.tt_num[d.pc] = 1;
						d.tt_issue[d.pc] = d.clock;
						d.tt_ins[d.pc] = "Ld " + "F" + Integer.toString(d.ins_op[1]) + " " + Integer.toString(d.ins_op[3]) + " " + "(" + Integer.toString(d.ins_op[2]) + ")";
						int pc4 = d.pc + 1;
						String temp = Integer.toString(pc4);
						d.rob_r[d.rob] = "F" + Integer.toString(d.ins_op[1]);
						d.rob_pc[d.rob] = d.pc;
						d.rat[d.ins_op[1]] = "ROB" + temp;
						d.pc++;
						d.seq++;
						d.rob++;
						d.addr++;
						break;
					}
				}
				break;
			case 2://sd
				for (int i = 0; i < d.ldrs; i++) {
					if (d.ld_busy[i] == 0) {
						d.ld_busy[i] = 1;
						d.ld_op[i] = 2;
						d.ld_seq[i] = d.seq;
						d.lsq_op[d.seq] = 2;
						d.lsq_pc[d.seq] = d.pc;

						if (d.da_f[d.ins_op[1]] != 0) {
							d.ld_v1t[i] = 1;
							d.ld_q1[i] = d.ins_op[1];
						} else {
							d.ld_v1t[i] = 0;
							d.ld_v1[i] = d.da_fd[d.ins_op[1]];
						}

						if (d.da_fi[d.ins_op[2]] != 0) {
							d.ld_vjt[i] = 1;
							d.ld_qj[i] = d.ins_op[2];
						} else {
							d.ld_vjt[i] = 0;
							d.ld_vj[i] = d.da_fdi[d.ins_op[2]];
						}

						d.ld_vkt[i] = 0;
						d.ld_vk[i] = d.ins_op[3];

						if (d.ld_vjt[i] == 0 && d.ld_vkt[i] == 0 && d.ld_v1t[i] == 0) {
							if(d.da_fei[d.ins_op[2]] == 1) {
								d.ld_time[i] = 2 + 1;
								d.tt_exe[d.pc] = d.clock + 2;
							}else{
								d.ld_time[i] = 2;
							}
						} else {
							d.ld_time[i] = 0;
						}
						d.da_f[d.ins_op[1]] = 2 * 10 + i;
						d.ld_pc[i] = d.pc;
						d.tt_num[d.pc] = 2;
						d.tt_issue[d.pc] = d.clock;
						d.tt_ins[d.pc] = "Sd " + "F" + Integer.toString(d.ins_op[1]) + " " + Integer.toString(d.ins_op[3]) + " " + "(" + Integer.toString(d.ins_op[2]) + ")";
						d.pc++;
						d.seq++;
						d.addr++;
						break;
					}
				}
				break;
			case 3://add.d
			case 4://sub.d
			case 5://mult.d
				int m, n, r_time, r_op;
				if (d.ins_op[0] == 3) {
					m = 0;
					n = d.addrs;
					r_time = d.addex;
					r_op = 3;
				} else if (d.ins_op[0] == 4) {
					m = 0;
					n = d.addrs;
					r_time = d.addex;
					r_op = 4;
				} else {
					m = d.addrs;
					n = d.addrs + d.multrs;
					r_time = d.multex;
					r_op = 5;
				}
				for (int i = m; i < n; i++) {
					if (d.rs_busy[i] == 0) {
						d.rs_busy[i] = 1;
						d.rs_op[i] = r_op;

						if (d.da_f[d.ins_op[2]] != 0) {
							d.rs_vjt[i] = 1;
							d.rs_qj[i] = d.ins_op[2];
						} else {
							d.rs_vjt[i] = 0;
							d.rs_vj[i] = d.da_fd[d.ins_op[2]];
						}

						if (d.da_f[d.ins_op[3]] != 0) {
							d.rs_vkt[i] = 1;
							d.rs_qk[i] = d.ins_op[3];
						} else {
							d.rs_vkt[i] = 0;
							d.rs_vk[i] = d.da_fd[d.ins_op[3]];
						}

						if (d.rs_vjt[i] == 0 && d.rs_vkt[i] == 0) {
							if(d.da_fe[d.ins_op[2]] == 1 || d.da_fe[d.ins_op[3]] == 1) {
								d.rs_time[i] = r_time + 1;
								d.tt_exe[d.pc] = d.clock + 2;
							}else{
								d.rs_time[i] = r_time;
							}
						} else {
							d.rs_time[i] = 0;
						}
						d.da_f[d.ins_op[1]] = r_op * 10 + i;
						d.rs_pc[i] = d.pc;
						d.tt_issue[d.pc] = d.clock;
						d.tt_num[d.pc] = r_op;
						if (r_op == 3) {
							d.tt_ins[d.pc] = "Add.d " + "F" + Integer.toString(d.ins_op[1]) + ",F" + Integer.toString(d.ins_op[2]) + ",F" + Integer.toString(d.ins_op[3]) + " ";
						}
						if (r_op == 4) {
							d.tt_ins[d.pc] = "Sub.d " + "F" + Integer.toString(d.ins_op[1]) + ",F" + Integer.toString(d.ins_op[2]) + ",F" + Integer.toString(d.ins_op[3]) + " ";
						}
						if (r_op == 5) {
							d.tt_ins[d.pc] = "Mult.d " + "F" + Integer.toString(d.ins_op[1]) + ",F" + Integer.toString(d.ins_op[2]) + ",F" + Integer.toString(d.ins_op[3]);
						}
						int pc1 = d.pc + 1;
						String temp = Integer.toString(pc1);
						d.rob_r[d.rob] = "F" + Integer.toString(d.ins_op[1]);
						d.rob_pc[d.rob] = d.pc;
						d.rat[d.ins_op[1]] = "ROB" + temp;
						d.pc++;
						d.rob++;
						d.addr++;
						break;
					}
				}
				break;
			case 6://Add
			case 7://Sub
				if (d.ins_op[0] == 6) {
					r_time = d.addexi;
					r_op = 6;
				} else {
					r_time = d.addexi;
					r_op = 7;
				}
				for (int i = 0; i < d.addrsi; i++) {
					if (d.rsi_busy[i] == 0) {
						d.rsi_busy[i] = 1;
						d.rsi_op[i] = r_op;

						if (d.da_fi[d.ins_op[2]] != 0) {
							d.rsi_vjt[i] = 1;
							d.rsi_qj[i] = d.ins_op[2];
						} else {
							d.rsi_vjt[i] = 0;
							d.rsi_vj[i] = d.da_fdi[d.ins_op[2]];
						}

						if (d.da_fi[d.ins_op[3]] != 0) {
							d.rsi_vkt[i] = 1;
							d.rsi_qk[i] = d.ins_op[3];
						} else {
							d.rsi_vkt[i] = 0;
							d.rsi_vk[i] = d.da_fdi[d.ins_op[3]];
						}

						if (d.rsi_vjt[i] == 0 && d.rsi_vkt[i] == 0) {
							if(d.da_fei[d.ins_op[2]] == 1 || d.da_fei[d.ins_op[3]] == 1) {
								d.rsi_time[i] = r_time + 1;
								d.tt_exe[d.pc] = d.clock + 2;
							}else{
								d.rsi_time[i] = r_time;
							}
						} else {
							d.rsi_time[i] = 0;
						}
						d.da_fi[d.ins_op[1]] = r_op * 10 + i;
						d.rsi_pc[i] = d.pc;
						d.tt_issue[d.pc] = d.clock;
						if (r_op == 6) {
							d.tt_ins[d.pc] = "Add " + "R" + Integer.toString(d.ins_op[1]) + ",R" + Integer.toString(d.ins_op[2]) + ",R" + Integer.toString(d.ins_op[3]) + "   ";
						}
						if (r_op == 7) {
							d.tt_ins[d.pc] = "Sub " + "R" + Integer.toString(d.ins_op[1]) + ",R" + Integer.toString(d.ins_op[2]) + ",R" + Integer.toString(d.ins_op[3]) + "   ";
						}
						d.rob_r[d.rob] = "R" + Integer.toString(d.ins_op[1]);
						d.rob_pc[d.rob] = d.pc;
						int pc2 = d.pc + 1;
						String temp = Integer.toString(pc2);
						d.rati[d.ins_op[1]] = "ROB" + temp;
						d.pc++;
						d.rob++;
						d.addr++;
						break;
					}
				}
				break;
			case 8://addi
				for (int i = 0; i < d.addrsi; i++) {
					if (d.rsi_busy[i] == 0) {
						d.rsi_busy[i] = 1;
						d.rsi_op[i] = 8;
//						d.da_fi[d.ins_op[1]] = 8 * 10 + i;

						if (d.da_fi[d.ins_op[2]] != 0) {
							d.rsi_vjt[i] = 1;
							d.rsi_qj[i] = d.ins_op[2];
						} else {
							d.rsi_vjt[i] = 0;
							d.rsi_vj[i] = d.da_fdi[d.ins_op[2]];
						}

						d.rsi_vkt[i] = 0;
						d.rsi_vk[i] = d.ins_op[3];

						if (d.rsi_vjt[i] == 0 && d.rsi_vkt[i] == 0) {
//							if(d.da_fei[d.ins_op[2]] == 1 || d.da_fei[d.ins_op[3]] == 1) {
							if(d.da_fei[d.ins_op[2]] == 1) {
								d.rsi_time[i] = d.addexi + 1;
								d.tt_exe[d.pc] = d.clock + 2;
							}else{
								d.rsi_time[i] = d.addexi;
							}
						} else {
							d.rsi_time[i] = 0;
						}


						d.da_fi[d.ins_op[1]] = 8 * 10 + i;
						d.rsi_pc[i] = d.pc;
						d.tt_issue[d.pc] = d.clock;
						d.tt_ins[d.pc] = "Addi " + "R" + Integer.toString(d.ins_op[1]) + ",R" + Integer.toString(d.ins_op[2]) + " " + Integer.toString(d.ins_op[3]) + "   ";
						d.rob_r[d.rob] = "R" + Integer.toString(d.ins_op[1]);
						d.rob_pc[d.rob] = d.pc;
						int pc3 = d.pc + 1;
						String temp = Integer.toString(pc3);
						d.rati[d.ins_op[1]] = "ROB" + temp;
						d.pc++;
						d.rob++;
						d.addr++;
						break;
					}
				}
				break;
			case 9://BEQ
				for (int i = 0; i < d.addrsi; i++) {
					if (d.rsi_busy[i] == 0) {
						d.rsi_busy[i] = 1;
						d.rsi_op[i] = 9;
						d.tt_num[d.pc] = 9;
						addr = d.addr;
						R = true;

						if (d.da_fi[d.ins_op[2]] != 0) {
							d.rsi_vjt[i] = 1;
							d.rsi_qj[i] = d.ins_op[2];
						} else {
							d.rsi_vjt[i] = 0;
							d.rsi_vj[i] = d.da_fdi[d.ins_op[2]];
						}

						if (d.da_fi[d.ins_op[3]] != 0) {
							d.rsi_vkt[i] = 1;
							d.rsi_qk[i] = d.ins_op[3];
						} else {
							d.rsi_vkt[i] = 0;
							d.rsi_vk[i] = d.da_fdi[d.ins_op[3]];
						}
						if (d.rsi_vjt[i] == 0 && d.rsi_vkt[i] == 0) {
							if(d.da_fei[d.ins_op[2]] == 1 || d.da_fei[d.ins_op[3]] == 1 ) {
								d.rsi_time[i] = d.addexi + 1;
								d.tt_exe[d.pc] = d.clock + 2;
							}else{
								d.rsi_time[i] = d.addexi;
							}
						} else {
							d.rsi_time[i] = 0;
						}
						if (B.get(d, c) == 0) {
							d.addr++;
							c.M[d.pc] = 1;
						} else {
							d.addr = d.addr + 1 + c.btb_target[B.get(d, c) - 1];
							c.M[d.pc] = 2;
						}
						d.da_fi[d.ins_op[1]] = 9 * 10 + i;
						d.rsi_pc[i] = d.pc;
						d.tt_issue[d.pc] = d.clock;
						d.tt_ins[d.pc] = "Beq " + "R" + Integer.toString(d.ins_op[2]) + ",R" + Integer.toString(d.ins_op[3]) + " " + Integer.toString(d.ins_op[4]) + "  ";
						d.pc++;
						break;
					}
				}
				break;
			case 10://BNE
				for (int i = 0; i < d.addrsi; i++) {
					if (d.rsi_busy[i] == 0) {
						d.rsi_busy[i] = 1;
						d.rsi_op[i] = 9;
						d.tt_num[d.pc] = 10;
						addr = d.addr;
						R = true;

						if (d.da_fi[d.ins_op[2]] != 0) {
							d.rsi_vjt[i] = 1;
							d.rsi_qj[i] = d.ins_op[2];
						} else {
							d.rsi_vjt[i] = 0;
							d.rsi_vj[i] = d.da_fdi[d.ins_op[2]];
						}

						if (d.da_fi[d.ins_op[3]] != 0) {
							d.rsi_vkt[i] = 1;
							d.rsi_qk[i] = d.ins_op[3];
						} else {
							d.rsi_vkt[i] = 0;
							d.rsi_vk[i] = d.da_fdi[d.ins_op[3]];
						}
						if (d.rsi_vjt[i] == 0 && d.rsi_vkt[i] == 0) {
							if(d.da_fei[d.ins_op[2]] == 1 || d.da_fei[d.ins_op[3]] == 1 ) {
								d.rsi_time[i] = d.addexi + 1;
								d.tt_exe[d.pc] = d.clock + 2;
							}else{
								d.rsi_time[i] = d.addexi;
							}
						} else {
							d.rsi_time[i] = 0;
						}
						if (B.get(d, c) == 0) {
							d.addr++;
							c.M[d.pc] = 1;
						} else {
							d.addr = d.addr + 1 + c.btb_target[B.get(d, c) - 1];
							c.M[d.pc] = 2;
						}
						d.da_fi[d.ins_op[1]] = 10 * 10 + i;
						d.rsi_pc[i] = d.pc;
						d.tt_issue[d.pc] = d.clock;
						d.tt_ins[d.pc] = "Bne " + "R" + Integer.toString(d.ins_op[2]) + ",R" + Integer.toString(d.ins_op[3]) + " " + Integer.toString(d.ins_op[4]) + "  ";
						d.pc++;
						break;
					}
				}
				break;
			default:
				break;
		}

		for(int i = 0; i < d.pc; i++) {
			if (d.tt_num[i] == 2) {
				if (d.tt_comr[i] == 1) {
					if (i == 0 && d.tt_comm[i] == 0) {
						d.v = 9;
						if (d.memo.isEmpty()) {
							d.tt_commit[i] = d.clock;
							d.tt_comm[i] = 1;
							d.tt_commitf[i] = d.clock + 3;
							com = 1;
							for (int j = 0; j < d.memt; j++) {
								d.memo.add(1);
							}
						}
					}
					if (i != 0 && d.tt_comm[i] == 0 && d.tt_comm[i - 1] == 1) {
						d.v = d.clock;
						if (d.memo.isEmpty()) {
							d.tt_commit[i] = d.clock;
							d.tt_comm[i] = 1;
							d.tt_commitf[i] = d.clock + 3;
							com = 1;
							for (int j = 0; j < d.memt; j++) {
								d.memo.add(1);
							}
						}
					}
				}
			}
		}

		for (int i = 0; i < d.pc; i++) {
			if (i == 0 && d.tt_res[i] == 1 && d.tt_comm[i] == 0 && d.tt_num[i] != 2) {
				if(d.tt_num[i] != 2) {
					d.tt_commit[i] = d.clock;
					d.tt_comm[i] = 1;
					if (d.tt_num[i] != 2) {
						for(int j = 0; j < d.rob; j++){
							if(d.rob_pc[j] == i){
								if (d.rob_r[j].substring(0, 1).equals("F") && d.rob_finish[j] == 1) {
									d.arf[Integer.parseInt(d.rob_r[j].substring(1, 2))] = d.rob_v[j];
									d.rat[Integer.parseInt(d.rob_r[j].substring(1, 2))] = "ARF" + d.rob_r[j].substring(1, 2);
									d.rob_r[j] = null;
									d.rob_v[j] = 0;
									d.rob_finish[j] = 0;
								} else if (d.rob_r[j].substring(0, 1).equals("R") && d.rob_finish[j] == 1) {
									d.arfi[Integer.parseInt(d.rob_r[j].substring(1, 2))] = (int) d.rob_v[j];
									d.rati[Integer.parseInt(d.rob_r[j].substring(1, 2))] = "ARF" + d.rob_r[j].substring(1, 2);
									d.rob_r[j] = null;
									d.rob_v[j] = 0;
									d.rob_finish[j] = 0;
								}
							}
						}
					}
					break;
				}
			}
			if (i > 0 && d.tt_comm[i - 1] == 1 && d.tt_res[i] == 1 && d.tt_comm[i] == 0 && com != 1) {
				if (d.tt_num[i] != 2) {
					d.tt_commit[i] = d.clock;
					d.tt_comm[i] = 1;
					if (d.tt_num[i] != 2) {
						for(int j = 0; j < d.rob; j++){
							if(d.rob_pc[j] == i){
								if (d.rob_r[j].substring(0, 1).equals("F") && d.rob_finish[j] == 1) {
									d.arf[Integer.parseInt(d.rob_r[j].substring(1, 2))] = d.rob_v[j];
									d.rat[Integer.parseInt(d.rob_r[j].substring(1, 2))] = "ARF" + d.rob_r[j].substring(1, 2);
									d.rob_r[j] = null;
									d.rob_v[j] = 0;
									d.rob_finish[j] = 0;
								} else if (d.rob_r[j].substring(0, 1).equals("R") && d.rob_finish[j] == 1) {
									d.arfi[Integer.parseInt(d.rob_r[j].substring(1, 2))] = (int) d.rob_v[j];
									d.rati[Integer.parseInt(d.rob_r[j].substring(1, 2))] = "ARF" + d.rob_r[j].substring(1, 2);
									d.rob_r[j] = null;
									d.rob_v[j] = 0;
									d.rob_finish[j] = 0;
								}
							}
						}
					}
					break;
				}
			}
		}

		for(int i = 0; i < d.pc; i++) {
			if (d.tt_num[i] == 1) {
				if (d.tt_exes[i] == 1 && d.tt_complete[i] == 0) {
					d.v = 2;
					for (int j = 0; j < d.seq; j++) {
						if (d.lsq_pc[j] == i) {
							int jug = 0;
							for (int l = 0; l < j; l++) {
								if (d.lsq_op[l] == 2 && d.lsq_as[l] == 0) {
									jug = 1;
								}
							}
							if (jug == 0) {
								d.v=4;
								int g = 0;
								for (int l = j; l >= 0; l--) {
									if (d.lsq_address[l] == d.lsq_address[j] && d.lsq_op[l] == 2) {
										g = l + 1;
										break;
									}
								}
								d.v=5;
								if (d.memo.isEmpty()) {
									d.v = 6;
									if (g != 0) {
										d.v = 7;
										if (d.lsq_vs[g - 1] == 1) {
												d.tt_mem[i] = d.clock;
												d.tt_complete[i] = d.clock;
												d.memo.add(1);
												d.lsq_value[j] = d.lsq_value[g - 1];
												d.lsq_vs[j] = 1;
										}
									} else {
										d.v = 3;
											d.tt_mem[i] = d.clock;
											if (d.tt_mem[i] == d.tt_exe[i]) {
												d.tt_mem[i]++;
												d.memo.add(1);
											}
											d.tt_complete[i] = d.tt_mem[i] + d.memt - 1;
											for(int l = 0; l < d.memt; l++) {
												d.memo.add(1);
											}
											d.lsq_value[j] = d.mem[d.lsq_address[j]];

									}
								}
							}
						}
					}
				}
			}
		}
		for(int i = 0; i < d.pc; i++){
			if(d.clock == d.tt_exe[i]){
				d.tt_exew[i] = d.clock;
				if(d.tt_num[i] == 1){
					d.tt_exes[i] = 1;
				}
			}
		}

		for(int i = 0; i < d.pc; i++){
			if(d.tt_num[i] == 1 && d.tt_complete[i] != 0 && d.tt_comr[i] == 0){
				if(d.clock == d.tt_complete[i]){
					d.tt_comr[i] = 1;
				}
			}
		}
		for(int i = 0; i < d.pc; i++){
			if(d.tt_num[i] == 2 && d.tt_comm[i] == 1){
				if(d.clock == d.tt_commitf[i]){
					for(int j = 0; j < d.seq; j++){
						if(d.lsq_pc[j] == i){
							d.lsq_vs[j] = 1;
							d.lsq_as[j] = 1;
							d.mem[d.lsq_address[j]] = d.lsq_value[j];
						}
					}
				}
			}
		}

		for (int i = 0; i < d.pc; i++) {
			if (tt_com[i] == 1 && d.tt_num[i] != 2) {
				if(tt_com[i] != 9 && tt_com[i] != 10) {
					d.cdb.add(i);
				}
			}
		}
		if (!d.cdb.isEmpty()) {
			int i = d.cdb.poll();
			if(d.tt_num[i] != 2) {
				d.tt_result[i] = d.clock;
				d.tt_res[i] = 1;
				if(d.tt_num[i] == 1){
					for(int j = 0; j < d.seq; j++) {
						if(d.lsq_pc[j] == i) {
							d.da_fd[d.ins_f1[i]] = d.lsq_value[j];
						}
					}
				}
			}
			if (d.tt_num[i] != 2) {
				for(int j = 0; j < d.rob; j++){
					if(d.rob_pc[j] == i){
						if (d.rob_r[j].substring(0, 1).equals("F")) {
							d.rob_v[j] = d.da_fd[Integer.parseInt(d.rob_r[j].substring(1, 2))];
						} else if (d.rob_r[j].substring(0, 1).equals("R")) {
							d.rob_v[j] = d.da_fdi[Integer.parseInt(d.rob_r[j].substring(1, 2))];
						}
						d.rob_finish[j] = 1;
					}
				}
			}
		}
		for(int i = 0; i < d.pc; i++){
			if(d.tt_num[i] == 9 || d.tt_num[i] == 10){
				d.tt_result[i] = 0;
			}
		}
		for(int i = 0; i < d.ldrs; i++){
			if(d.ld_busy[i] == 1 && d.ld_busy1[i] == 1){
				d.ld_busy[i] = 0;
				d.ld_busy1[i] = 0;
			}
		}
		for(int i = 0; i < d.addrs + d.multrs; i++){
			if(d.rs_busy[i] == 1 && d.rs_busy1[i] == 1){
				d.rs_busy[i] = 0;
				d.rs_busy1[i] = 0;
			}
		}
		for(int i = 0; i < d.addrsi; i++){
			if(d.rsi_busy[i] == 1 && d.rsi_busy1[i] == 1){
				d.rsi_busy[i] = 0;
				d.rsi_busy1[i] = 0;
			}
		}

		if(R){
				C.record(r, d, d.pc-1);
				r.addr[d.pc-1] = addr;
				R = false;
		}

		d.memo.poll();

		System.out.println("Cycle:" + d.clock);
		System.out.println(d.addr);
		System.out.println("Time Table:");
		for (int i = 0; i < d.pc; i++) {
			System.out.println(d.tt_ins[i] + " " + d.tt_issue[i] + " " + d.tt_exew[i] + " " + d.tt_mem[i]  + " " + d.tt_result[i] + " " + d.tt_commit[i] + " " + d.tt_exes[i]);
		}
		System.out.println();
		for (int i = 0; i < d.rob; i++) {
			System.out.println(d.rob_r[i] + " " + d.rob_v[i] + " " + d.rob_finish[i] + " " + d.rob_pc[i]);
		}
		System.out.println();
		for (int i = 0; i < d.addrs + d.multrs; i++) {
			System.out.println(d.rs_time[i] + " " + d.rs_busy[i] + " " + d.rs_op[i] + " " + d.rs_vjt[i] + " " + d.rs_vkt[i]);
		}
		System.out.println();
		for (int i = 0; i < d.addrsi; i++) {
			System.out.println(d.rsi_time[i] + " " + d.rsi_busy[i] + " " + d.rsi_op[i] + " " + d.rsi_vjt[i] + " " + d.rsi_vkt[i]);
		}
		System.out.println();
		for (int i = 0; i < d.ldrs; i++) {
			System.out.println(d.ld_time[i] + " " + d.ld_busy[i] + " " + d.ld_op[i] + " " + d.ld_vjt[i] + " " + d.ld_v1t[i]);
		}
		System.out.println();
		for (int i = 0; i < 11; i++) {
			System.out.println(d.rat[i] + " " + d.rati[i]);
		}
		System.out.println();
		for (int i = 0; i < d.seq; i++) {
			System.out.println(d.lsq_op[i] + " " + d.lsq_address[i] + " " + d.lsq_value[i] + " " + d.lsq_pc[i] + " " + d.lsq_as[i] + " " + d.lsq_vs[i]);
		}
		System.out.println();
		for(int i = 0; i < d.addrs + d.multrs + d.addrsi + d.ldrs;i++){
			System.out.println(run_order[i][0] + " " + run_order[i][1] + " " + run_order[i][3]);
		}
		System.out.println(d.v);
		System.out.println(d.memo.size());

		boolean skip=false;
		for (int i = 0; i < d.addrs + d.multrs + d.addrsi + d.ldrs; i++) {
			if(run_order[i][1] - 1 >= 0) {
				switch (x[run_order[i][1] - 1]) {
					case 3:
						C.back(d, r, run_order[i][1] - 1);
						B.add(d.addr, d.ins_f4[d.addr], c);
						d.addr = d.addr + 1 + d.ins_f4[d.addr];
						d.clock++;
						skip = true;
						c.M[run_order[i][1] - 1] = 0;
						d.J[run_order[i][1] - 1] = 0;
						break;
					case 4:
					case 6:
						c.M[run_order[i][1] - 1] = 0;
						break;
					case 7:
						C.back(d, r, run_order[i][1] - 1);
						B.del(d.addr, c);
						d.addr++;
						d.clock++;
						skip = true;
						c.M[run_order[i][1] - 1] = 0;
						d.J[run_order[i][1] - 1] = 0;
						break;
				}
			}
		}

		for(int i = 0;i < 8;i ++) {
			System.out.println(c.btb_id[i] + " " + c.btb_Pc[i] + " " + c.btb_target[i]);
		}
		System.out.println();
		System.out.println();

//		if(skip){
//			System.out.println("Cycle:" + d.clock);
//			System.out.println("Time Table:");
//			for (int i = 0; i < d.pc; i++) {
//				System.out.println(d.tt_ins[i] + " " + d.tt_issue[i] + " " + d.tt_exew[i] + " " + d.tt_mem[i]  + " " + d.tt_result[i] + " " + d.tt_commit[i]);
//			}
//			System.out.println();
//			for (int i = 0; i < d.rob; i++) {
//				System.out.println(d.rob_r[i] + " " + d.rob_v[i] + " " + d.rob_finish[i] + " " + d.rob_pc[i]);
//			}
//			System.out.println();
//			for (int i = 0; i < d.addrs + d.multrs; i++) {
//				System.out.println(d.rs_time[i] + " " + d.rs_busy[i] + " " + d.rs_op[i] + " " + d.rs_vjt[i] + " " + d.rs_vkt[i]);
//			}
//			System.out.println();
//			for (int i = 0; i < d.addrsi; i++) {
//				System.out.println(d.rsi_time[i] + " " + d.rsi_busy[i] + " " + d.rsi_op[i] + " " + d.rsi_vjt[i] + " " + d.rsi_vkt[i]);
//			}
//			System.out.println();
//			for (int i = 0; i < d.ldrs; i++) {
//				System.out.println(d.ld_time[i] + " " + d.ld_busy[i] + " " + d.ld_op[i] + " " + d.ld_vjt[i] + " " + d.ld_v1t[i]);
//			}
//			System.out.println();
//			for (int i = 0; i < 11; i++) {
//				System.out.println(d.rat[i] + " " + d.rati[i]);
//			}
//			System.out.println();
//			for (int i = 0; i < d.seq; i++) {
//				System.out.println(d.lsq_op[i] + " " + d.lsq_address[i] + " " + d.lsq_value[i] + " " + d.lsq_pc[i] + " " + d.lsq_as[i] + " " + d.lsq_vs[i]);
//			}
//			System.out.println();
//			for(int i = 0; i < d.addrs + d.multrs + d.addrsi + d.ldrs;i++){
//				System.out.println(run_order[i][0] + " " + run_order[i][1] + " " + run_order[i][3]);
//			}
//			System.out.println(d.v);
//			System.out.println(d.memo.size());
//		}
	}
}
		