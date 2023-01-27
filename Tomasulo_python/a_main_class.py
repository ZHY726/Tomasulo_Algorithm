from h_input import readCpuInit
from i_output import writeResult2Txt
from b_IS import IS_stage
from c_EX import EX_stage
from d_MEM import MEM_stage
from e_WB import WB_stage
from f_COMMIT import COM_stage
import time

# instruction_order = ["NOP", "Ld", "Sd", "Beq", "Bne", "Add", "Add.d", "Addi", "Sub", "Sub.d", "Mult.d"]

class tomasulo:
    def __init__(self):
        self.instructions = [[-1 for column in range(4)] for row in range(1)]
        self.timeTable = [[-1 for column in range(13)] for row in range(1)]

        self.pc = 0
        self.mem_busy = 0
        self.pc_stall = 0

        self.rob = [[-1 for column in range(5)] for row in range(1)]

        self.lsq = [[-1 for column in range(5)] for row in range(1)]

        self.rat_int = [[0 for column in range(2)] for row in range(32)]
        self.rat_fp = [[0 for column in range(2)] for row in range(32)]

        self.arf_int = [0 for row in range(32)]
        self.arf_fp = [0 for row in range(32)]

        self.mem = [0 for row in range(65)]

        self.rs_int = [[-1 for column in range(7)] for row in range(1)]
        self.rs_fpAdd = [[-1 for column in range(7)] for row in range(1)]
        self.rs_fpMult = [[-1 for column in range(7)] for row in range(1)]
        self.rs_ldsd = [[-1 for column in range(10)] for row in range(1)]
        self.rs_branch = [[-1 for column in range(7)] for row in range(1)]

        self.rob_entry = 128
        self.cdb_buffer_entry = 1

        self.number_rs_int = 2
        self.number_rs_fpAdd = 3
        self.number_rs_fpMult = 2
        self.number_rs_ldsd = 3

        self.number_fu_int = 1
        self.number_fu_fpAdd = 1
        self.number_fu_fpMult = 1
        self.number_fu_ldsd = 1

        self.ex_cycles_int = 1
        self.ex_cycles_fpAdd = 3
        self.ex_cycles_fpMult = 20
        self.ex_cycles_ldsd = 1

        self.mem_cycles = 4

        self.empty_number_rs_int = self.number_rs_int * self.number_fu_int
        self.empty_number_rs_fpAdd = self.number_rs_fpAdd * self.number_fu_fpAdd
        self.empty_number_rs_fpMult = self.number_rs_fpMult * self.number_fu_fpMult
        self.empty_number_rs_ldsd = self.number_rs_ldsd * self.number_fu_ldsd

        self.cycle = 0
        self.mem_will_be_freed = 0

        for i in range(32):
            self.rat_int[i][0] = "ARF"
            self.rat_int[i][1] = i
            self.rat_fp[i][0] = "ARF"
            self.rat_fp[i][1] = i
        self.lsq.pop()
        self.instructions.pop()


def initialize(cpu, inputFilePath):
    readCpuInit(cpu, inputFilePath)
    # cpu.instructions.append([9, 3, 1, 2])
    # cpu.arf_int[1] = 12
    # cpu.arf_fp[1] = 10.1
    # cpu.mem[32] = 30.3


if __name__ == '__main__':
    cpu = tomasulo()
    inputFilePath = "./testcase.txt"
    currentTime = str(time.strftime('%m_%d_%Y_%H_%M_%S', time.localtime(time.time())))
    outputFilePath = "./Outputs/output_" + currentTime + ".txt"
    initialize(cpu, inputFilePath)
    for cycle in range(1, 999, 1):
        cpu.cycle = cycle

        IS_stage(cpu)
        EX_stage(cpu)
        MEM_stage(cpu)
        WB_stage(cpu)
        COM_stage(cpu)

        if cpu.timeTable[len(cpu.timeTable)-1][12] == cpu.cycle-1:
            break
    writeResult2Txt(cpu, outputFilePath)

