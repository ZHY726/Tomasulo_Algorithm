instruction_order = ["NOP ", "Ld ", "Sd ", "Beq ", "Bne ", "Add ", "Add.d ", "Addi ", "Sub ", "Sub.d ", "Mult.d "]


def equalStrLength(str, num):
    if len(str) < num:
        spaceNeeded = num-len(str)
        for i in range(0, spaceNeeded, 1):
            str = str + " "
    return str


def ifNeedEndCycle(startCycle, endCycle):
    if int(startCycle) == int(endCycle) or int(endCycle) == -1:
        output = str(startCycle)
    else:
        output = str(startCycle) + "-" + str(endCycle)
    output = equalStrLength(output, 7)
    return output


def writeResult2Txt(cpu, filePath):
    outputFile = open(filePath, mode="w")
    print("       Time Table         IS  EX     MEM    WB  COM")
    outputFile.write("       Time Table         IS  EX     MEM    WB  COM\n")
    for i in range(1, len(cpu.timeTable)):
        op = cpu.timeTable[i][1]
        op_str = instruction_order[cpu.timeTable[i][1]]
        f1_str = str(cpu.timeTable[i][2])
        f2_str = str(cpu.timeTable[i][3])
        f3_str = str(cpu.timeTable[i][4])
        is_str = str(cpu.timeTable[i][5])
        ex_str = str(cpu.timeTable[i][6])
        mem_str = str(cpu.timeTable[i][7])
        wb_str = str(cpu.timeTable[i][8])
        com_str = str(cpu.timeTable[i][9])
        ex_end_str = str(cpu.timeTable[i][10])
        mem_end_str = str(cpu.timeTable[i][11])
        com_end_str = str(cpu.timeTable[i][12])

        op_str = equalStrLength(op_str, 7)
        f1_str = equalStrLength(f1_str, 2)
        is_str = equalStrLength(is_str, 4)
        wb_str = equalStrLength(wb_str, 4)

        if op == 0:
            pass
            outputLine_str_instruction = op_str
            mem_str = equalStrLength(" ", 7)
        elif op == 1:
            outputLine_str_instruction = op_str + "F" + f1_str + ", " + f2_str + "(R" + f3_str + ")"
            mem_str = ifNeedEndCycle(mem_str, mem_end_str)
        elif op == 2:
            outputLine_str_instruction = op_str + "F" + f1_str + ", " + f2_str + "(R" + f3_str + ")"
            mem_str = equalStrLength(" ", 7)
            wb_str = equalStrLength(" ", 4)
        elif op == 3 or op == 4:
            outputLine_str_instruction = op_str + "R" + f1_str + ", R" + f2_str + ", " + f3_str
            mem_str = equalStrLength(" ", 7)
            wb_str = equalStrLength(" ", 4)
        elif op == 5 or op == 8:
            outputLine_str_instruction = op_str + "R" + f1_str + ", R" + f2_str + ", R" + f3_str
            mem_str = equalStrLength(" ", 7)
        elif op == 7:
            outputLine_str_instruction = op_str + "R" + f1_str + ", R" + f2_str + ", " + f3_str
            mem_str = equalStrLength(" ", 7)
        elif op == 6 or op == 9 or op == 10:
            outputLine_str_instruction = op_str + "F" + f1_str + ", F" + f2_str + ", F" + f3_str
            mem_str = equalStrLength(" ", 7)
        else:
            outputLine_str_instruction = ""
        outputLine_str_instruction = equalStrLength(str(i), 3) + equalStrLength(outputLine_str_instruction, 23)
        ex_str = ifNeedEndCycle(ex_str, ex_end_str)
        com_str = ifNeedEndCycle(com_str, com_end_str)
        outputLine_str_cycles = is_str + ex_str + mem_str + wb_str + com_str
        outputLine_str = outputLine_str_instruction + outputLine_str_cycles
        print(outputLine_str)
        outputFile.write(outputLine_str+"\n")

    print("-----------------------------------------------------")
    outputFile.write("-----------------------------------------------------" + "\n")
    for i in range(1, len(cpu.arf_int)):
        if cpu.arf_int[i] != 0:
            outputLine_str = "R"+str(i)+" = "+str(cpu.arf_int[i])+"; "
            print(outputLine_str, end="")
            outputFile.write(outputLine_str)
    print("")
    outputFile.write("\n")
    for i in range(1, len(cpu.arf_fp)):
        if cpu.arf_fp[i] != 0:
            outputLine_str = "F"+str(i)+" = "+str(cpu.arf_fp[i])+"; "
            print(outputLine_str, end="")
            outputFile.write(outputLine_str)
    print("")
    outputFile.write("\n")
    for i in range(1, len(cpu.mem)):
        if cpu.mem[i] != 0:
            outputLine_str = "Mem["+str(i)+"] = "+str(cpu.mem[i])+"; "
            print(outputLine_str, end="")
            outputFile.write(outputLine_str)
    print("")
    outputFile.write("\n")
    outputFile.close()
