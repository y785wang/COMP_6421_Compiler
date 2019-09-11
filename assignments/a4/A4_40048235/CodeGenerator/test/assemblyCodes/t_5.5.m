
          entry
          addi r14, r0, topaddr
          addi r1, r0, 10
          sw -10000(r14), r1
          addi r1, r0, 1
          sw -10004(r14), r1
          lw r1, -10004(r14)
          not r2, r1
          sw -10004(r14), r2
          addi r1, r0, 2
          sw -10008(r14), r1
          addi r1, r0, 3
          sw -10012(r14), r1
          lw r1, -10008(r14)
          lw r2, -10012(r14)
          mul r3, r1, r2
          sw -10016(r14), r3
          lw r1, -10004(r14)
          lw r2, -10016(r14)
          add r3, r1, r2
          sw -10020(r14), r3
          addi r1, r0, 4
          sw -10024(r14), r1
          lw r1, -10024(r14)
          sub r2, r0, r1
          sw -10024(r14), r2
          addi r1, r0, 2
          sw -10028(r14), r1
          lw r1, -10024(r14)
          lw r2, -10028(r14)
          div r3, r1, r2
          sw -10032(r14), r3
          lw r1, -10020(r14)
          lw r2, -10032(r14)
          add r3, r1, r2
          sw -10036(r14), r3
          addi r1, r0, 4
          sw -10040(r14), r1
          lw r1, -10036(r14)
          lw r2, -10040(r14)
          cgt r3, r1, r2
          sw -10044(r14), r3
          lw r1, -10044(r14)
          putc r1
          addi r1, r0, 0
          sw -10048(r14), r1
          addi r1, r0, 1
          sw -10052(r14), r1
      %processing assignStat x = 1
          lw r1, -10052(r14)
          sw -4(r14), r1
          addi r1, r0, 1
          sw -10056(r14), r1
          addi r1, r0, 2
          sw -10060(r14), r1
      %processing assignStat x = 2
          lw r1, -10060(r14)
          sw -8(r14), r1
          addi r1, r0, 5
          sw -10064(r14), r1
          addi r1, r0, 3
          sw -10068(r14), r1
      %processing assignStat x = 3
          lw r1, -10068(r14)
          sw -24(r14), r1
          addi r1, r0, 0
          sw -10072(r14), r1
          lw r1, -4(r14)
          putc r1
          addi r1, r0, 1
          sw -10076(r14), r1
          lw r1, -8(r14)
          putc r1
          addi r1, r0, 5
          sw -10080(r14), r1
          lw r1, -24(r14)
          putc r1
          addi r1, r0, 1
          sw -10084(r14), r1
          addi r1, r0, 0
          sw -10088(r14), r1
          lw r1, -10084(r14)
          lw r2, -10088(r14)
          and r3, r1, r2
          sw -10092(r14), r3
          addi r1, r0, 4
          sw -10096(r14), r1
      %processing assignStat x = 4
          lw r1, -10096(r14)
          sw -4(r14), r1
          addi r1, r0, 0
          sw -10100(r14), r1
          lw r1, -10100(r14)
          not r2, r1
          sw -10100(r14), r2
          addi r1, r0, 1
          sw -10104(r14), r1
          lw r1, -10104(r14)
          not r2, r1
          sw -10104(r14), r2
          lw r1, -10100(r14)
          lw r2, -10104(r14)
          sub r3, r1, r2
          sw -10108(r14), r3
          addi r1, r0, 5
          sw -10112(r14), r1
      %processing assignStat x = 5
          lw r1, -10112(r14)
          sw -8(r14), r1
          addi r1, r0, 2
          sw -10116(r14), r1
          addi r1, r0, 5
          sw -10120(r14), r1
          lw r1, -10116(r14)
          lw r2, -10120(r14)
          mul r3, r1, r2
          sw -10124(r14), r3
          addi r1, r0, 10
          sw -10128(r14), r1
          lw r1, -10128(r14)
          sub r2, r0, r1
          sw -10128(r14), r2
          addi r1, r0, 2
          sw -10132(r14), r1
          lw r1, -10128(r14)
          lw r2, -10132(r14)
          div r3, r1, r2
          sw -10136(r14), r3
          lw r1, -10124(r14)
          lw r2, -10136(r14)
          add r3, r1, r2
          sw -10140(r14), r3
          addi r1, r0, 6
          sw -10144(r14), r1
      %processing assignStat x = 6
          lw r1, -10144(r14)
          sw -24(r14), r1
          addi r1, r0, 1
          sw -10148(r14), r1
          lw r1, -10148(r14)
          sub r2, r0, r1
          sw -10148(r14), r2
          addi r1, r0, 1
          sw -10152(r14), r1
          lw r1, -10148(r14)
          lw r2, -10152(r14)
          add r3, r1, r2
          sw -10156(r14), r3
          lw r1, -4(r14)
          putc r1
          addi r1, r0, 1
          sw -10160(r14), r1
          addi r1, r0, 0
          sw -10164(r14), r1
          lw r1, -10160(r14)
          lw r2, -10164(r14)
          or r3, r1, r2
          sw -10168(r14), r3
          lw r1, -8(r14)
          putc r1
          addi r1, r0, 5
          sw -10172(r14), r1
          addi r1, r0, 1
          sw -10176(r14), r1
          lw r1, -10176(r14)
          sub r2, r0, r1
          sw -10176(r14), r2
          addi r1, r0, 1
          sw -10180(r14), r1
          lw r1, -10180(r14)
          sub r2, r0, r1
          sw -10180(r14), r2
          lw r1, -10176(r14)
          lw r2, -10180(r14)
          div r3, r1, r2
          sw -10184(r14), r3
          addi r1, r0, 1
          sw -10188(r14), r1
          lw r1, -10188(r14)
          not r2, r1
          sw -10188(r14), r2
          lw r1, -10184(r14)
          lw r2, -10188(r14)
          mul r3, r1, r2
          sw -10192(r14), r3
          lw r1, -10172(r14)
          lw r2, -10192(r14)
          add r3, r1, r2
          sw -10196(r14), r3
          lw r1, -24(r14)
          putc r1
          addi r1, r0, 3
          sw -10200(r14), r1
      %processing assignStat a = 3
          lw r1, -10200(r14)
          sw -8(r14), r1
          addi r1, r0, 1
          sw -10204(r14), r1
          lw r1, -10204(r14)
          not r2, r1
          sw -10204(r14), r2
          addi r1, r0, 2
          sw -10208(r14), r1
          addi r1, r0, 3
          sw -10212(r14), r1
          lw r1, -10208(r14)
          lw r2, -10212(r14)
          mul r3, r1, r2
          sw -10216(r14), r3
          lw r1, -10204(r14)
          lw r2, -10216(r14)
          add r3, r1, r2
          sw -10220(r14), r3
          addi r1, r0, 4
          sw -10224(r14), r1
          lw r1, -10224(r14)
          sub r2, r0, r1
          sw -10224(r14), r2
          addi r1, r0, 2
          sw -10228(r14), r1
          lw r1, -10224(r14)
          lw r2, -10228(r14)
          div r3, r1, r2
          sw -10232(r14), r3
          lw r1, -10220(r14)
          lw r2, -10232(r14)
          add r3, r1, r2
          sw -10236(r14), r3
          lw r1, -10236(r14)
          lw r2, -8(r14)
          cgt r3, r1, r2
          sw -10240(r14), r3
          lw r1, -10240(r14)
          putc r1
          hlt
