from g_cpu_functions import useOrder2SearchRs, useOrder2SearchLsq


def EX_stage(cpu):
    for ex_pc in range(1, len(cpu.timeTable), 1):
        order = ex_pc
        if cpu.timeTable[order][6] == -1 and 0 < cpu.timeTable[order][5] < cpu.cycle:
            op = cpu.timeTable[order][1]
            if op == 0:
                cpu.timeTable[order][6] = cpu.cycle
                cpu.timeTable[order][10] = cpu.cycle
                break
            elif op == 1 or op == 2:
                rs_position_row = useOrder2SearchRs(cpu.rs_ldsd, order)
                if cpu.rs_ldsd[rs_position_row][4] == 0:
                    cpu.timeTable[order][6] = cpu.cycle
                    cpu.timeTable[order][10] = cpu.cycle + cpu.ex_cycles_ldsd - 1
                    break

            elif op == 3 or op == 4:
                rs_position_row = useOrder2SearchRs(cpu.rs_branch, order)
                if cpu.rs_branch[rs_position_row][3] == 0 and cpu.rs_branch[rs_position_row][4] == 0:
                    cpu.timeTable[order][6] = cpu.cycle
                    cpu.timeTable[order][10] = cpu.cycle
                    break

            elif op == 5 or op == 7 or op == 8:
                rs_position_row = useOrder2SearchRs(cpu.rs_int, order)
                if cpu.rs_int[rs_position_row][3] == 0 and cpu.rs_int[rs_position_row][4] == 0:
                    cpu.timeTable[order][6] = cpu.cycle
                    cpu.timeTable[order][10] = cpu.cycle + cpu.ex_cycles_int - 1
                    break
            elif op == 6 or op == 9 or op == 8:
                rs_position_row = useOrder2SearchRs(cpu.rs_fpAdd, order)
                if cpu.rs_fpAdd[rs_position_row][3] == 0 and cpu.rs_fpAdd[rs_position_row][4] == 0:
                    cpu.timeTable[order][6] = cpu.cycle
                    cpu.timeTable[order][10] = cpu.cycle + cpu.ex_cycles_fpAdd - 1
                    break
            elif op == 10:
                rs_position_row = useOrder2SearchRs(cpu.rs_fpMult, order)
                if cpu.rs_fpMult[rs_position_row][3] == 0 and cpu.rs_fpMult[rs_position_row][4] == 0:
                    cpu.timeTable[order][6] = cpu.cycle
                    cpu.timeTable[order][10] = cpu.cycle + cpu.ex_cycles_fpMult - 1
                    break

    for ex_pc in range(1, len(cpu.timeTable), 1):
        order = ex_pc
        op = cpu.timeTable[order][1]
        if cpu.timeTable[order][10] == cpu.cycle:
            if op == 1 or op == 2:
                rs_position_row = useOrder2SearchRs(cpu.rs_ldsd, order)
                address = cpu.rs_ldsd[rs_position_row][5] + cpu.rs_ldsd[rs_position_row][6]
                cpu.rs_ldsd[rs_position_row][9] = address
                lsq_position_row = useOrder2SearchLsq(cpu.lsq, order)
                cpu.lsq[lsq_position_row][2] = address
            elif op == 3:
                rs_position_row = useOrder2SearchRs(cpu.rs_branch, order)
                if cpu.rs_branch[rs_position_row][5] == cpu.rs_branch[rs_position_row][6]:
                    cpu.pc = cpu.pc + 1 + cpu.timeTable[order][4]
                    cpu.pc_stall = 0
                else:
                    cpu.pc += 1
                    cpu.pc_stall = 0
            elif op == 4:
                rs_position_row = useOrder2SearchRs(cpu.rs_branch, order)
                if cpu.rs_branch[rs_position_row][5] != cpu.rs_branch[rs_position_row][6]:
                    cpu.pc = cpu.pc + 1 + cpu.timeTable[order][4]
                    cpu.pc_stall = 0
                else:
                    cpu.pc += 1
                    cpu.pc_stall = 0