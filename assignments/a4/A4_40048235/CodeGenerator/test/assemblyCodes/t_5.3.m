
          entry
          addi r14, r0, topaddr
          addi r1, r0, 3
          sw -10000(r14), r1
      %processing assignStat x = 3
          lw r1, -10000(r14)
          sw -4(r14), r1
          addi r1, r0, 5
          sw -10004(r14), r1
          addi r1, r0, 1
          sw -10008(r14), r1
          lw r1, -10004(r14)
          lw r2, -10008(r14)
          add r3, r1, r2
          sw -10012(r14), r3
          addi r1, r0, 2
          sw -10016(r14), r1
          addi r1, r0, 3
          sw -10020(r14), r1
          lw r1, -10016(r14)
          lw r2, -10020(r14)
          mul r3, r1, r2
          sw -10024(r14), r3
          lw r1, -10012(r14)
          lw r2, -10024(r14)
          ceq r3, r1, r2
          sw -10028(r14), r3
      %processing assignStat y = 5
          lw r1, -10028(r14)
          sw -8(r14), r1
          addi r1, r0, 5
          sw -10032(r14), r1
          lw r1, -8(r14)
          lw r2, -10032(r14)
          add r3, r1, r2
          sw -10036(r14), r3
      %processing assignStat y = y
          lw r1, -10036(r14)
          sw -8(r14), r1
          lw r1, -8(r14)
          lw r2, -8(r14)
          mul r3, r1, r2
          sw -10040(r14), r3
          addi r1, r0, 1
          sw -10044(r14), r1
          lw r1, -4(r14)
          lw r2, -10044(r14)
          add r3, r1, r2
          sw -10048(r14), r3
          lw r1, -10040(r14)
          lw r2, -10048(r14)
          div r3, r1, r2
          sw -10052(r14), r3
          addi r1, r0, 2
          sw -10056(r14), r1
          lw r1, -10052(r14)
          lw r2, -10056(r14)
          sub r3, r1, r2
          sw -10060(r14), r3
      %processing assignStat z = y
          lw r1, -10060(r14)
          sw -12(r14), r1
          lw r1, -12(r14)
          putc r1
          getc r1
          sw -12(r14), r1
          addi r2, r0, 97
          sw -10064(r14), r2
          lw r2, -12(r14)
          lw r3, -10064(r14)
          ceq r4, r2, r3
          sw -10068(r14), r4
      %processing ifStat
          lw r1, -10068(r14)
          bz r1, else1
          getc r2
          sw -12(r14), r2
          addi r3, r0, 97
          sw -10072(r14), r3
          lw r3, -12(r14)
          lw r4, -10072(r14)
          ceq r5, r3, r4
          sw -10076(r14), r5
      %processing ifStat
          lw r2, -10076(r14)
          bz r2, else2
          addi r3, r0, 8
          sw -10080(r14), r3
          lw r3, -10080(r14)
          putc r3
          j endif2
else2
          addi r3, r0, 9
          sw -10084(r14), r3
          lw r3, -10084(r14)
          putc r3
endif2
          j endif1
else1
          getc r2
          sw -12(r14), r2
          addi r3, r0, 97
          sw -10088(r14), r3
          lw r3, -12(r14)
          lw r4, -10088(r14)
          ceq r5, r3, r4
          sw -10092(r14), r5
      %processing ifStat
          lw r2, -10092(r14)
          bz r2, else3
          addi r3, r0, 10
          sw -10096(r14), r3
          lw r3, -10096(r14)
          putc r3
          j endif3
else3
          addi r3, r0, 11
          sw -10100(r14), r3
          lw r3, -10100(r14)
          putc r3
endif3
endif1
          hlt
