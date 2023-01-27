from g_cpu_functions import writeInstruction2timeTable, searchRatForRs, writeNewRobAndRat


def IS_stage(cpu):
    timeTable_len = len(cpu.timeTable)
    instruction_len = len(cpu.instructions)
    if cpu.pc < instruction_len and cpu.pc_stall == 0:
        if cpu.instructions[cpu.pc][0] == 0:  # nop
            writeInstruction2timeTable(cpu)
            cpu.timeTable[timeTable_len][5] = cpu.cycle
            cpu.pc += 1
        elif cpu.instructions[cpu.pc][0] == 1 or cpu.instructions[cpu.pc][0] == 2:
            if cpu.empty_number_rs_ldsd > 0:
                order, op, f1, f2, f3 = writeInstruction2timeTable(cpu)
                cpu.timeTable[timeTable_len][5] = cpu.cycle
                cpu.empty_number_rs_ldsd -= 1
                rob_len = len(cpu.rob)
                # Rs
                rs_order = order
                rs_op = op
                if op == 1:
                    rs_rob = rob_len
                    rs_tag0 = 0
                    rs_val0 = 0
                else:
                    rs_rob = 0
                    rs_tag0, rs_val0 = searchRatForRs("F", f1, cpu)
                rs_tag1 = 0
                rs_val1 = f2
                rs_tag2, rs_val2 = searchRatForRs("R", f3, cpu)
                cpu.rs_ldsd.append([rs_order, rs_op, rs_rob, rs_tag1, rs_tag2, rs_val1, rs_val2, rs_tag0, rs_val0, -1])
                # Rob&rat
                if cpu.timeTable[timeTable_len][1] == 1:
                    writeNewRobAndRat(rob_len, "F", f1, cpu)
                # lsq
                lsq_order = order
                lsq_op = op
                if op == 2 and rs_tag0 == 0:
                    lsq_value = rs_val0
                    cpu.lsq.append([lsq_order, lsq_op, -1, lsq_value, 1])
                else:
                    cpu.lsq.append([lsq_order, lsq_op, -1, 0, 0])
                cpu.pc += 1
        elif cpu.instructions[cpu.pc][0] == 3 or cpu.instructions[cpu.pc][0] == 4:
            order, op, f1, f2, f3 = writeInstruction2timeTable(cpu)
            cpu.timeTable[timeTable_len][5] = cpu.cycle
            # rs
            rs_order = order
            rs_op = op
            rs_rob = 0
            rs_tag1, rs_val1 = searchRatForRs("R", f1, cpu)
            rs_tag2, rs_val2 = searchRatForRs("R", f2, cpu)
            cpu.rs_branch.append([order, rs_op, rs_rob, rs_tag1, rs_tag2, rs_val1, rs_val2])
            cpu.pc_stall = 1
        elif cpu.instructions[cpu.pc][0] == 5 or cpu.instructions[cpu.pc][0] == 7 or cpu.instructions[cpu.pc][0] == 8:
            if cpu.empty_number_rs_int > 0:
                order, op, f1, f2, f3 = writeInstruction2timeTable(cpu)
                cpu.timeTable[timeTable_len][5] = cpu.cycle
                cpu.empty_number_rs_int -= 1
                rob_len = len(cpu.rob)
                # rs
                rs_order = order
                rs_op = op
                rs_rob = rob_len
                rs_tag1, rs_val1 = searchRatForRs("R", f2, cpu)
                if op == 7:
                    rs_tag2 = 0
                    rs_val2 = f3
                else:
                    rs_tag2, rs_val2 = searchRatForRs("R", f3, cpu)
                cpu.rs_int.append([order, rs_op, rs_rob, rs_tag1, rs_tag2, rs_val1, rs_val2])
                # Rob&rat
                writeNewRobAndRat(rob_len, "R", f1, cpu)
                cpu.pc += 1
        elif cpu.instructions[cpu.pc][0] == 6 or cpu.instructions[cpu.pc][0] == 9:
            if cpu.empty_number_rs_fpAdd > 0:
                order, op, f1, f2, f3 = writeInstruction2timeTable(cpu)
                cpu.timeTable[timeTable_len][5] = cpu.cycle
                cpu.empty_number_rs_fpAdd -= 1
                rob_len = len(cpu.rob)
                # rs
                rs_order = order
                rs_op = op
                rs_rob = rob_len
                rs_tag1, rs_val1 = searchRatForRs("F", f2, cpu)
                rs_tag2, rs_val2 = searchRatForRs("F", f3, cpu)
                cpu.rs_fpAdd.append([order, rs_op, rs_rob, rs_tag1, rs_tag2, rs_val1, rs_val2])
                writeNewRobAndRat(rob_len, "F", f1, cpu)
                cpu.pc += 1
        elif cpu.instructions[cpu.pc][0] == 10:
            if cpu.empty_number_rs_fpMult > 0:
                order, op, f1, f2, f3 = writeInstruction2timeTable(cpu)
                cpu.timeTable[timeTable_len][5] = cpu.cycle
                cpu.empty_number_rs_fpMult -= 1
                rob_len = len(cpu.rob)
                # rs
                rs_order = order
                rs_op = op
                rs_rob = rob_len
                rs_tag1, rs_val1 = searchRatForRs("F", f2, cpu)
                rs_tag2, rs_val2 = searchRatForRs("F", f3, cpu)
                cpu.rs_fpMult.append([order, rs_op, rs_rob, rs_tag1, rs_tag2, rs_val1, rs_val2])
                writeNewRobAndRat(rob_len, "F", f1, cpu)
                cpu.pc += 1