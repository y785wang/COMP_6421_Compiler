
          entry
          addi r14, r0, topaddr
      %processing forStat
          addi r1, r0, 3
          sw -9996(r14), r1
beginFor
          lw r1, -9996(r14)
          bz r1, endFor
          subi r2, r1, 1
          sw -9996(r14), r2
          addi r3, r0, 5
          sw -10000(r14), r3
          lw r3, -10000(r14)
          putc r3
          j beginFor
endFor
          hlt
