from g_cpu_functions import useOrder2SearchLsq, useOrder2SearchRs


def MEM_stage(cpu):
    same_address_sd_found = 0
    empty_address_sd_found = 0
    same_address_lsq_pc = -1
    earlier_sd_can_commit = 0
    for mem_pc in range(1, len(cpu.timeTable), 1):
        order = mem_pc
        op = cpu.timeTable[order][1]

        if cpu.timeTable[order][11] == cpu.cycle:
            lsq_position_row = useOrder2SearchLsq(cpu.lsq, order)
            mem_address = cpu.lsq[lsq_position_row][2]
            mem_value = cpu.mem[mem_address]
            cpu.lsq[lsq_position_row][3] = mem_value
            cpu.lsq[lsq_position_row][4] = 1
            cpu.mem_will_be_freed = 1
            break

        if op == 1 and 0 < cpu.timeTable[order][10] < cpu.cycle and cpu.timeTable[order][7] == -1:
            if cpu.mem_busy == 0:
                lsq_position_row = useOrder2SearchLsq(cpu.lsq, order)
                for lsq_pc in range(lsq_position_row - 1, -1, -1):
                    if cpu.lsq[lsq_pc][2] == -1 and cpu.lsq[lsq_pc][1] == 2:
                        empty_address_sd_found = 1
                        break
                if empty_address_sd_found == 1:
                    break

                for lsq_pc in range(lsq_position_row - 1, -1, -1):
                    if cpu.lsq[lsq_pc][2] == cpu.lsq[lsq_position_row][2]:
                        if cpu.lsq[lsq_pc][4] == 1:
                            same_address_sd_found = 1
                            same_address_lsq_pc = lsq_pc
                            break
                        else:
                            same_address_sd_found = -1
                            break
                if same_address_sd_found == -1:
                    break

                for temp_order in range(1, order, 1):
                    temp_op = cpu.timeTable[temp_order][1]
                    if cpu.timeTable[temp_order][9] == -1 and temp_op == 2:
                        if 0 < cpu.timeTable[temp_order][10] < cpu.cycle:
                            if cpu.mem_busy == 0:
                                rs_position_row = useOrder2SearchRs(cpu.rs_ldsd, temp_order)
                                if cpu.rs_ldsd[rs_position_row][7] != 0:
                                    break
                                earlier_sd_can_commit = 1
                            break
                    elif cpu.timeTable[temp_order][9] == -1 and temp_op != 2:
                        break

                if earlier_sd_can_commit == 1:
                    break

                cpu.mem_busy = 1
                if same_address_sd_found == 1:
                    cpu.lsq[lsq_position_row][3] = cpu.lsq[same_address_lsq_pc][3]
                    cpu.lsq[lsq_position_row][4] = 1
                    cpu.timeTable[order][7] = cpu.cycle
                    cpu.timeTable[order][11] = cpu.cycle
                    cpu.mem_will_be_freed = 1
                else:
                    cpu.timeTable[order][7] = cpu.cycle
                    cpu.timeTable[order][11] = cpu.cycle + cpu.mem_cycles - 1
                break
