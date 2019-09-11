
          entry
          addi r14, r0, topaddr
          addi r1, r0, 3
          sw -10000(r14), r1
          addi r1, r0, 4
          sw -10004(r14), r1
          addi r1, r0, 2
          sw -10008(r14), r1
          addi r1, r0, 0
          sw -10012(r14), r1
          addi r1, r0, 0
          sw -10016(r14), r1
          addi r1, r0, 0
          sw -10020(r14), r1
          addi r1, r0, 1
          sw -10024(r14), r1
      %processing assignStat x = 1
          lw r1, -10024(r14)
          sw -4(r14), r1
          addi r1, r0, 1
          sw -10028(r14), r1
          addi r1, r0, 1
          sw -10032(r14), r1
          addi r1, r0, 0
          sw -10036(r14), r1
          addi r1, r0, 2
          sw -10040(r14), r1
      %processing assignStat x = 2
          lw r1, -10040(r14)
          sw -44(r14), r1
          addi r1, r0, 1
          sw -10044(r14), r1
          addi r1, r0, 2
          sw -10048(r14), r1
          addi r1, r0, 1
          sw -10052(r14), r1
          addi r1, r0, 3
          sw -10056(r14), r1
      %processing assignStat x = 3
          lw r1, -10056(r14)
          sw -56(r14), r1
          addi r1, r0, 2
          sw -10060(r14), r1
          addi r1, r0, 3
          sw -10064(r14), r1
          addi r1, r0, 1
          sw -10068(r14), r1
          addi r1, r0, 4
          sw -10072(r14), r1
      %processing assignStat x = 4
          lw r1, -10072(r14)
          sw -96(r14), r1
          addi r1, r0, 0
          sw -10076(r14), r1
          addi r1, r0, 0
          sw -10080(r14), r1
          addi r1, r0, 0
          sw -10084(r14), r1
          lw r1, -4(r14)
          putc r1
          addi r1, r0, 1
          sw -10088(r14), r1
          addi r1, r0, 1
          sw -10092(r14), r1
          addi r1, r0, 0
          sw -10096(r14), r1
          lw r1, -44(r14)
          putc r1
          addi r1, r0, 1
          sw -10100(r14), r1
          addi r1, r0, 2
          sw -10104(r14), r1
          addi r1, r0, 1
          sw -10108(r14), r1
          lw r1, -56(r14)
          putc r1
          addi r1, r0, 2
          sw -10112(r14), r1
          addi r1, r0, 3
          sw -10116(r14), r1
          addi r1, r0, 1
          sw -10120(r14), r1
          lw r1, -96(r14)
          putc r1
          addi r1, r0, 5
          sw -10124(r14), r1
      %processing assignStat a = 5
          lw r1, -10124(r14)
          sw -100(r14), r1
          lw r1, -100(r14)
          putc r1
          addi r1, r0, 0
          sw -10128(r14), r1
          addi r1, r0, 0
          sw -10132(r14), r1
          addi r1, r0, 6
          sw -10136(r14), r1
      %processing assignStat a = 6
          lw r1, -10136(r14)
          sw -148(r14), r1
          addi r1, r0, 1
          sw -10140(r14), r1
          addi r1, r0, 2
          sw -10144(r14), r1
          addi r1, r0, 7
          sw -10148(r14), r1
      %processing assignStat a = 7
          lw r1, -10148(r14)
          sw -172(r14), r1
          addi r1, r0, 2
          sw -10152(r14), r1
          addi r1, r0, 3
          sw -10156(r14), r1
          addi r1, r0, 8
          sw -10160(r14), r1
      %processing assignStat a = 8
          lw r1, -10160(r14)
          sw -192(r14), r1
          addi r1, r0, 0
          sw -10164(r14), r1
          addi r1, r0, 0
          sw -10168(r14), r1
          lw r1, -148(r14)
          putc r1
          addi r1, r0, 1
          sw -10172(r14), r1
          addi r1, r0, 2
          sw -10176(r14), r1
          lw r1, -172(r14)
          putc r1
          addi r1, r0, 2
          sw -10180(r14), r1
          addi r1, r0, 3
          sw -10184(r14), r1
          lw r1, -192(r14)
          putc r1
          addi r1, r0, 9
          sw -10188(r14), r1
      %processing assignStat a = 9
          lw r1, -10188(r14)
          sw -60(r14), r1
          addi r1, r0, 10
          sw -10192(r14), r1
      %processing assignStat a = 10
          lw r1, -10192(r14)
          sw -64(r14), r1
          lw r1, -60(r14)
          putc r1
          lw r1, -64(r14)
          putc r1
          addi r1, r0, 11
          sw -10196(r14), r1
      %processing assignStat d = 11
          lw r1, -10196(r14)
          sw -4(r14), r1
          lw r1, -4(r14)
          putc r1
          hlt
