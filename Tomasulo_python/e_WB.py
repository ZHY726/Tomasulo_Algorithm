from g_cpu_functions import useOrder2SearchLsq, useOrder2SearchRs, writeBack2Rob
from decimal import Decimal


def WB_stage(cpu):
    for wb_pc in range(1, len(cpu.timeTable), 1):
        order = wb_pc
        op = cpu.timeTable[order][1]
        if cpu.timeTable[order][8] == -1:
            if op == 0 and 0 < cpu.timeTable[order][10] < cpu.cycle:
                cpu.timeTable[order][8] = cpu.cycle
                break
            elif op == 1 and 0 < cpu.timeTable[order][11] < cpu.cycle:
                lsq_position_row = useOrder2SearchLsq(cpu.lsq, order)
                ld_result = cpu.lsq[lsq_position_row][3]
                rs_position_row = useOrder2SearchRs(cpu.rs_ldsd, order)
                result_rob = cpu.rs_ldsd[rs_position_row][2]
                writeBack2Rob(result_rob, ld_result,cpu)
                cpu.timeTable[order][8] = cpu.cycle
                cpu.empty_number_rs_ldsd += 1
                break
            elif (op == 5 or op == 7) and 0 < cpu.timeTable[order][10] < cpu.cycle:
                rs_position_row = useOrder2SearchRs(cpu.rs_int, order)
                intAdd_result = cpu.rs_int[rs_position_row][5] + cpu.rs_int[rs_position_row][6]
                result_rob = cpu.rs_int[rs_position_row][2]
                writeBack2Rob(result_rob, intAdd_result,cpu)
                cpu.timeTable[order][8] = cpu.cycle
                cpu.empty_number_rs_int += 1
                break
            elif op == 8 and 0 < cpu.timeTable[order][10] < cpu.cycle:
                rs_position_row = useOrder2SearchRs(cpu.rs_int, order)
                intSub_result = cpu.rs_int[rs_position_row][5] - cpu.rs_int[rs_position_row][6]
                result_rob = cpu.rs_int[rs_position_row][2]
                writeBack2Rob(result_rob, intSub_result,cpu)
                cpu.timeTable[order][8] = cpu.cycle
                cpu.empty_number_rs_int += 1
                break
            elif op == 6 and 0 < cpu.timeTable[order][10] < cpu.cycle:
                rs_position_row = useOrder2SearchRs(cpu.rs_fpAdd, order)
                numberA = Decimal(str(cpu.rs_fpAdd[rs_position_row][5]))
                numberB = Decimal(str(cpu.rs_fpAdd[rs_position_row][6]))
                numberAns = numberA + numberB
                # fpAdd_result = cpu.rs_fpAdd[rs_position_row][5] + cpu.rs_fpAdd[rs_position_row][6]
                fpAdd_result = float(numberAns)
                result_rob = cpu.rs_fpAdd[rs_position_row][2]
                writeBack2Rob(result_rob, fpAdd_result, cpu)
                cpu.timeTable[order][8] = cpu.cycle
                cpu.empty_number_rs_fpAdd += 1
                break
            elif op == 9 and 0 < cpu.timeTable[order][10] < cpu.cycle:
                rs_position_row = useOrder2SearchRs(cpu.rs_fpAdd, order)
                numberA = Decimal(str(cpu.rs_fpAdd[rs_position_row][5]))
                numberB = Decimal(str(cpu.rs_fpAdd[rs_position_row][6]))
                numberAns = numberA - numberB
                # fpSub_result = cpu.rs_fpAdd[rs_position_row][5] - cpu.rs_fpAdd[rs_position_row][6]
                fpSub_result = float(numberAns)
                result_rob = cpu.rs_fpAdd[rs_position_row][2]
                writeBack2Rob(result_rob, fpSub_result, cpu)
                cpu.timeTable[order][8] = cpu.cycle
                cpu.empty_number_rs_fpAdd += 1
                break
            elif op == 10 and 0 < cpu.timeTable[order][10] < cpu.cycle:
                rs_position_row = useOrder2SearchRs(cpu.rs_fpMult, order)
                numberA = Decimal(str(cpu.rs_fpMult[rs_position_row][5]))
                numberB = Decimal(str(cpu.rs_fpMult[rs_position_row][6]))
                numberAns = numberA * numberB
                # fpMult_result = rs_fpMult[rs_position_row][5] * rs_fpMult[rs_position_row][6]
                fpMult_result = float(numberAns)
                result_rob = cpu.rs_fpMult[rs_position_row][2]
                writeBack2Rob(result_rob, fpMult_result, cpu)
                cpu.timeTable[order][8] = cpu.cycle
                cpu.empty_number_rs_fpMult += 1
                break