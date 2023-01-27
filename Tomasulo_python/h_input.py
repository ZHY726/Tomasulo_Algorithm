import re


def getCleanListBySpace(line_str, split_time):
    if split_time == -1:
        list = line_str.split(' ')
    else:
        list = line_str.split(' ', split_time)
    while '' in list:
        list.remove('')
    return list


def getCleanListByEqual(line_str):
    line_str = line_str.replace(" ", "")
    list = line_str.split('=')
    while '' in list:
        list.remove('')
    return list


def getCleanListByComma(line_str):
    line_str = line_str.replace(" ", "")
    list = line_str.split(',')
    while '' in list:
        list.remove('')
    return list

def getCleanListByParenthese(line_str):
    line_str = line_str.replace(" ", "")
    list = line_str.split('(')
    while '' in list:
        list.remove('')
    return list


def readCpuInit(cpu, filepath):
    f = open(filepath, encoding="utf-8")
    content = f.readlines()
    f.close()
    for line in content:
        line = line.rstrip()
        if "Integer adder" in line:
            content_list = getCleanListBySpace(line, -1)
            cpu.number_rs_int = int(content_list[2])
            cpu.ex_cycles_int = int(content_list[3])
            if int(content_list[4]) == 0:
                cpu.number_fu_int = int(content_list[5])
            else:
                cpu.number_fu_int = int(content_list[4])
            cpu.empty_number_rs_int = cpu.number_rs_int * cpu.number_fu_int
        elif "FP adder" in line:
            content_list = getCleanListBySpace(line, -1)
            cpu.number_rs_fpAdd = int(content_list[2])
            cpu.ex_cycles_fpAdd = int(content_list[3])
            if int(content_list[4]) == 0:
                cpu.number_fu_fpAdd = int(content_list[5])
            else:
                cpu.number_fu_fpAdd = int(content_list[4])
            cpu.empty_number_rs_fpAdd = cpu.number_rs_fpAdd * cpu.number_fu_fpAdd
        elif "FP multiplier" in line:
            content_list = getCleanListBySpace(line, -1)
            cpu.number_rs_fpMult = int(content_list[2])
            cpu.ex_cycles_fpMult = int(content_list[3])
            if int(content_list[4]) == 0:
                cpu.number_fu_fpMult = int(content_list[5])
            else:
                cpu.number_fu_fpMult = int(content_list[4])
            cpu.empty_number_rs_fpMult = cpu.number_rs_fpMult * cpu.number_fu_fpMult
        elif "Load/store unit" in line:
            content_list = getCleanListBySpace(line, -1)
            cpu.number_rs_ldsd = int(content_list[2])
            cpu.ex_cycles_ldsd = int(content_list[3])
            cpu.mem_cycles = int(content_list[4])
            cpu.number_fu_fpMult = int(content_list[5])
            cpu.empty_number_rs_ldsd = cpu.number_rs_ldsd * cpu.number_fu_ldsd
        elif "ROB entries" in line:
            content_list = getCleanListByEqual(line)
            cpu.rob_entry = int(content_list[1])
        elif "CDB buffer entries" in line:
            content_list = getCleanListByEqual(line)
            cpu.cdb_buffer_entry = int(content_list[1])
        elif ("R0" in line) and ("=" in line):
            content_list = getCleanListByComma(line)
            for each_content in content_list:
                each_arf = getCleanListByEqual(each_content)
                if "R" in each_arf[0]:
                    arf_location = int(re.sub(r'\D', "", each_arf[0]))
                    arf_value = int(each_arf[1])
                    cpu.arf_int[arf_location] = arf_value
                elif "F" in each_arf[0]:
                    arf_location = int(re.sub(r'\D', "", each_arf[0]))
                    arf_value = float(each_arf[1])
                    cpu.arf_fp[arf_location] = arf_value
        elif ("Mem" in line) and ("=" in line):
            content_list = getCleanListByComma(line)
            for each_content in content_list:
                each_mem = getCleanListByEqual(each_content)
                mem_location = int(re.sub(r'\D', "", each_mem[0]))
                mem_value = float(each_mem[1])
                cpu.mem[mem_location] = mem_value

        elif "NOP" in line:
            op = 0
            f1 = 0
            f2 = 0
            f3 = 0
            cpu.instructions.append([op, f1, f2, f3])
            # print(op, f1, f2, f3)
        elif ("Ld" in line) and ("(" in line):
            op = 1
            content_list = getCleanListBySpace(line, 1)[1]
            f_list = getCleanListByComma(content_list)
            f1 = int(re.sub(r'\D', "", f_list[0]))
            f23_list = getCleanListByParenthese(f_list[1])
            f2 = int(f23_list[0])
            f3 = int(re.sub(r'\D', "", f23_list[1]))
            cpu.instructions.append([op, f1, f2, f3])
            # print(op, f1, f2, f3)
        elif ("Sd" in line) and ("(" in line):
            op = 2
            content_list = getCleanListBySpace(line, 1)[1]
            f_list = getCleanListByComma(content_list)
            f1 = int(re.sub(r'\D', "", f_list[0]))
            f23_list = getCleanListByParenthese(f_list[1])
            f2 = int(f23_list[0])
            f3 = int(re.sub(r'\D', "", f23_list[1]))
            cpu.instructions.append([op, f1, f2, f3])
            # print(op, f1, f2, f3)
        elif ("Beq" in line) and ("R" in line):
            op = 3
            content_list = getCleanListBySpace(line, 1)[1]
            f_list = getCleanListByComma(content_list)
            f1 = int(re.sub(r'\D', "", f_list[0]))
            f2 = int(re.sub(r'\D', "", f_list[1]))
            f3 = int(f_list[2])
            cpu.instructions.append([op, f1, f2, f3])
            # print(op, f1, f2, f3)
        elif ("Bne" in line) and ("R" in line):
            op = 4
            content_list = getCleanListBySpace(line, 1)[1]
            f_list = getCleanListByComma(content_list)
            f1 = int(re.sub(r'\D', "", f_list[0]))
            f2 = int(re.sub(r'\D', "", f_list[1]))
            f3 = int(f_list[2])
            cpu.instructions.append([op, f1, f2, f3])
            # print(op, f1, f2, f3)
        elif ("Add" in line) and ("R" in line) and ("Addi" not in line):
            op = 5
            content_list = getCleanListBySpace(line, 1)[1]
            f_list = getCleanListByComma(content_list)
            f1 = int(re.sub(r'\D', "", f_list[0]))
            f2 = int(re.sub(r'\D', "", f_list[1]))
            f3 = int(re.sub(r'\D', "", f_list[2]))
            cpu.instructions.append([op, f1, f2, f3])
            # print(op, f1, f2, f3)
        elif ("Add.d" in line) and ("F" in line):
            op = 6
            content_list = getCleanListBySpace(line, 1)[1]
            f_list = getCleanListByComma(content_list)
            f1 = int(re.sub(r'\D', "", f_list[0]))
            f2 = int(re.sub(r'\D', "", f_list[1]))
            f3 = int(re.sub(r'\D', "", f_list[2]))
            cpu.instructions.append([op, f1, f2, f3])
            # print(op, f1, f2, f3)
        elif ("Addi" in line) and ("R" in line):
            op = 7
            content_list = getCleanListBySpace(line, 1)[1]
            f_list = getCleanListByComma(content_list)
            f1 = int(re.sub(r'\D', "", f_list[0]))
            f2 = int(re.sub(r'\D', "", f_list[1]))
            f3 = int(f_list[2])
            cpu.instructions.append([op, f1, f2, f3])
            # print(op, f1, f2, f3)
        elif ("Sub" in line) and ("R" in line):
            op = 8
            content_list = getCleanListBySpace(line, 1)[1]
            f_list = getCleanListByComma(content_list)
            f1 = int(re.sub(r'\D', "", f_list[0]))
            f2 = int(re.sub(r'\D', "", f_list[1]))
            f3 = int(re.sub(r'\D', "", f_list[2]))
            cpu.instructions.append([op, f1, f2, f3])
            # print(op, f1, f2, f3)
        elif ("Sub.d" in line) and ("F" in line):
            op = 9
            content_list = getCleanListBySpace(line, 1)[1]
            f_list = getCleanListByComma(content_list)
            f1 = int(re.sub(r'\D', "", f_list[0]))
            f2 = int(re.sub(r'\D', "", f_list[1]))
            f3 = int(re.sub(r'\D', "", f_list[2]))
            cpu.instructions.append([op, f1, f2, f3])
            # print(op, f1, f2, f3)
        elif ("Mult.d" in line) and ("F" in line):
            op = 10
            content_list = getCleanListBySpace(line, 1)[1]
            f_list = getCleanListByComma(content_list)
            f1 = int(re.sub(r'\D', "", f_list[0]))
            f2 = int(re.sub(r'\D', "", f_list[1]))
            f3 = int(re.sub(r'\D', "", f_list[2]))
            cpu.instructions.append([op, f1, f2, f3])
            # print(op, f1, f2, f3)


# if __name__ == '__main__':
#     filePath = "./testcase-inst.txt"
#     cpu = 1
#     readCpuInit(cpu, filePath)
