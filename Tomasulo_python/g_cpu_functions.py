def searchRatForRs(kind, number, cpu):
    if kind == "R":
        if cpu.rat_int[number][0] == "ARF":
            return 0, cpu.arf_int[number]
        else:
            if cpu.rob[cpu.rat_int[number][1]][3] == 1:
                return 0, cpu.rob[cpu.rat_int[number][1]][2]
            else:
                return cpu.rat_int[number][1], 0

    else:
        if cpu.rat_fp[number][0] == "ARF":
            return 0, cpu.arf_fp[number]
        else:
            if cpu.rob[cpu.rat_fp[number][1]][3] == 1:
                return 0, cpu.rob[cpu.rat_fp[number][1]][2]
            else:
                return cpu.rat_fp[number][1], 0


def useOrder2SearchRs(array, order):
    for i in range(0, len(array), 1):
        if array[i][0] == order:
            return i


def useOrder2SearchLsq(array, order):
    for i in range(0, len(array), 1):
        if array[i][0] == order:
            return i


def searchRatForRob(kind, result_rob, cpu):
    if kind == "R":
        for i in range(0, len(cpu.rat_int), 1):
            if cpu.rat_int[i][0] == "ROB" and cpu.rat_int[i][1] == result_rob:
                return 1, i
        return 0, 0
    elif kind == "F":
        for i in range(0, len(cpu.rat_fp), 1):
            if cpu.rat_fp[i][0] == "ROB" and cpu.rat_fp[i][1] == result_rob:
                return 1, i
        return 0, 0


def writeInstruction2timeTable(cpu):
    # global timeTable, pc
    timeTable_len = len(cpu.timeTable)
    order = timeTable_len
    op = cpu.instructions[cpu.pc][0]
    f1 = cpu.instructions[cpu.pc][1]
    f2 = cpu.instructions[cpu.pc][2]
    f3 = cpu.instructions[cpu.pc][3]
    cpu.timeTable.append([order, op, f1, f2, f3, -1, -1, -1, -1, -1, -1, -1, -1])
    return order, op, f1, f2, f3


def writeNewRobAndRat(position, kind, number, cpu):
    # global rob, rat_int, rat_fp
    cpu.rob.append([kind, number, 0, 0, 0])
    # cpu.rob[position][0] = kind
    # cpu.rob[position][1] = number
    # cpu.rob[position][3] = 0
    if kind == "R":
        cpu.rat_int[number][0] = "ROB"
        cpu.rat_int[number][1] = position
    else:
        cpu.rat_fp[number][0] = "ROB"
        cpu.rat_fp[number][1] = position


def robBroadcast2AllRs(position,value, cpu):
    for i in range(0, len(cpu.rs_int), 1):
        if cpu.rs_int[i][3] == position:
            cpu.rs_int[i][3] = 0
            cpu.rs_int[i][5] = value
        if cpu.rs_int[i][4] == position:
            cpu.rs_int[i][4] = 0
            cpu.rs_int[i][6] = value
    for i in range(0, len(cpu.rs_fpAdd), 1):
        if cpu.rs_fpAdd[i][3] == position:
            cpu.rs_fpAdd[i][3] = 0
            cpu.rs_fpAdd[i][5] = value
        if cpu.rs_fpAdd[i][4] == position:
            cpu.rs_fpAdd[i][4] = 0
            cpu.rs_fpAdd[i][6] = value
    for i in range(0, len(cpu.rs_fpMult), 1):
        if cpu.rs_fpMult[i][3] == position:
            cpu.rs_fpMult[i][3] = 0
            cpu.rs_fpMult[i][5] = value
        if cpu.rs_fpMult[i][4] == position:
            cpu.rs_fpMult[i][4] = 0
            cpu.rs_fpMult[i][6] = value
    for i in range(0, len(cpu.rs_branch), 1):
        if cpu.rs_branch[i][3] == position:
            cpu.rs_branch[i][3] = 0
            cpu.rs_branch[i][5] = value
        if cpu.rs_branch[i][4] == position:
            cpu.rs_branch[i][4] = 0
            cpu.rs_branch[i][6] = value
    for i in range(0, len(cpu.rs_ldsd), 1):
        if cpu.rs_ldsd[i][4] == position:
            cpu.rs_ldsd[i][4] = 0
            cpu.rs_ldsd[i][6] = value
        if cpu.rs_ldsd[i][7] == position:
            cpu.rs_ldsd[i][7] = 0
            cpu.rs_ldsd[i][8] = value
            rs_temp_order = cpu.rs_ldsd[i][0]
            lsq_temp_order = useOrder2SearchLsq(cpu.lsq, rs_temp_order)
            cpu.lsq[lsq_temp_order][3] = value
            cpu.lsq[lsq_temp_order][4] = 1


def writeBack2Rob(position, value, cpu):
    cpu.rob[position][2] = value
    cpu.rob[position][3] = 1
    robBroadcast2AllRs(position, value, cpu)


def commitRob2Arf(position, cpu):
    kind = cpu.rob[position][0]
    register = cpu.rob[position][1]
    value = cpu.rob[position][2]
    cpu.rob[position][4] = 1
    if kind == "R":
        cpu.arf_int[register] = value
    elif kind == "F":
        cpu.arf_fp[register] = value
    search_result, arf_position = searchRatForRob(kind, position, cpu)
    if search_result == 1:
        if kind == "R":
            cpu.rat_int[arf_position][0] = "ARF"
            cpu.rat_int[arf_position][1] = arf_position
        elif kind == "F":
            cpu.rat_fp[arf_position][0] = "ARF"
            cpu.rat_fp[arf_position][1] = arf_position

