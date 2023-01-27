from g_cpu_functions import useOrder2SearchRs, commitRob2Arf


def COM_stage(cpu):
    for com_pc in range(1, len(cpu.timeTable), 1):
        order = com_pc
        op = cpu.timeTable[order][1]
        if cpu.timeTable[order][9] == -1:
            if op == 0:
                if 0 < cpu.timeTable[order][8] < cpu.cycle:
                    cpu.timeTable[order][9] = cpu.cycle
                    cpu.timeTable[order][12] = cpu.cycle
                break
            elif op == 1:
                if 0 < cpu.timeTable[order][8] < cpu.cycle:
                    rs_position_row = useOrder2SearchRs(cpu.rs_ldsd, order)
                    result_rob = cpu.rs_ldsd[rs_position_row][2]
                    commitRob2Arf(result_rob, cpu)
                    cpu.timeTable[order][9] = cpu.cycle
                    cpu.timeTable[order][12] = cpu.cycle
                break
            elif op == 2:
                if 0 < cpu.timeTable[order][10] < cpu.cycle:
                    if cpu.mem_busy == 0:
                        rs_position_row = useOrder2SearchRs(cpu.rs_ldsd, order)
                        if cpu.rs_ldsd[rs_position_row][7] != 0:
                            break
                        cpu.mem_busy = 1
                        cpu.timeTable[order][9] = cpu.cycle
                        cpu.timeTable[order][12] = cpu.cycle + cpu.mem_cycles - 1
                    break
            elif op == 3 or op == 4:
                if 0 < cpu.timeTable[order][10] < cpu.cycle:
                    cpu.timeTable[order][9] = cpu.cycle
                    cpu.timeTable[order][12] = cpu.cycle
                break
            elif op == 5 or op == 7 or op == 8:
                if 0 < cpu.timeTable[order][8] < cpu.cycle:
                    rs_position_row = useOrder2SearchRs(cpu.rs_int, order)
                    result_rob = cpu.rs_int[rs_position_row][2]
                    commitRob2Arf(result_rob, cpu)
                    cpu.timeTable[order][9] = cpu.cycle
                    cpu.timeTable[order][12] = cpu.cycle
                break
            elif op == 6 or op == 9:
                if 0 < cpu.timeTable[order][8] < cpu.cycle:
                    rs_position_row = useOrder2SearchRs(cpu.rs_fpAdd, order)
                    result_rob = cpu.rs_fpAdd[rs_position_row][2]
                    commitRob2Arf(result_rob, cpu)
                    cpu.timeTable[order][9] = cpu.cycle
                    cpu.timeTable[order][12] = cpu.cycle
                break
            elif op == 10:
                if 0 < cpu.timeTable[order][8] < cpu.cycle:
                    rs_position_row = useOrder2SearchRs(cpu.rs_fpMult, order)
                    result_rob = cpu.rs_fpMult[rs_position_row][2]
                    commitRob2Arf(result_rob, cpu)
                    cpu.timeTable[order][9] = cpu.cycle
                    cpu.timeTable[order][12] = cpu.cycle
                break
    for com_pc in range(1, len(cpu.timeTable), 1):
        order = com_pc
        op = cpu.timeTable[order][1]
        if op == 2 and cpu.timeTable[order][12] == cpu.cycle:
            rs_position_row = useOrder2SearchRs(cpu.rs_ldsd, order)
            address = cpu.rs_ldsd[rs_position_row][9]
            cpu.mem[address] = cpu.rs_ldsd[rs_position_row][8]
            cpu.mem_will_be_freed = 1
            break

    if cpu.mem_will_be_freed == 1:
        cpu.mem_busy = 0
        cpu.mem_will_be_freed = 0
