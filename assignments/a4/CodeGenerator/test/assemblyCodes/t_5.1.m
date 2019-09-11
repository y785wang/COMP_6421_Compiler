
          entry
          addi r14, r0, topaddr
          addi r1, r0, 3
          sw -10000(r14), r1
          addi r1, r0, 4
          sw -10004(r14), r1
          addi r1, r0, 2
          sw -10008(r14), r1
          addi r1, r0, 2
          sw -10012(r14), r1
          addi r1, r0, 3
          sw -10016(r14), r1
          addi r1, r0, 11
          sw -10020(r14), r1
          addi r1, r0, 22
          sw -10024(r14), r1
          lw r1, -10020(r14)
          lw r2, -10024(r14)
          add r3, r1, r2
          sw -10028(r14), r3
          addi r1, r0, 66
          sw -10032(r14), r1
          lw r1, -10028(r14)
          lw r2, -10032(r14)
          add r3, r1, r2
          sw -10036(r14), r3
      %processing assignStat temp = 11
          lw r1, -10036(r14)
          sw -240(r14), r1
          lw r1, -240(r14)
          putc r1
          hlt
