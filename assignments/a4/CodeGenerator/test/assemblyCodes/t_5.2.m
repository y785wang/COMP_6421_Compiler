
fa        sw -8(r14), r15
          addi r2, r0, 11
          sw -10000(r14), r2
      %saving return value
          lw r1, -10000(r14)
          sw -4(r14), r1
          lw r15, -8(r14)
          jr r15

fc        sw -8(r14), r15
          addi r2, r0, 4225
          sw -10004(r14), r2
      %saving return value
          lw r1, -10004(r14)
          sw -4(r14), r1
          lw r15, -8(r14)
          jr r15

f1        sw -8(r14), r15
          addi r14, r14, -12
          jl r15, f2
          subi r14, r14, -12
      %loading return value
          lw r1, -16(r14)
          sw -10008(r14), r1
      %processing assignStat x = f2
          lw r1, -10008(r14)
          sw -12(r14), r1
      %saving return value
          lw r1, -12(r14)
          sw -4(r14), r1
          lw r15, -8(r14)
          jr r15

f2        sw -8(r14), r15
          addi r14, r14, -8
          jl r15, fa
          subi r14, r14, -8
      %loading return value
          lw r2, -12(r14)
          sw -10024(r14), r2
      %saving return value
          lw r1, -10024(r14)
          sw -4(r14), r1
          lw r15, -8(r14)
          jr r15

f3        sw -8(r14), r15
          lw r2, -12(r14)
          lw r3, -16(r14)
          add r4, r2, r3
          sw -10036(r14), r4
      %saving return value
          lw r1, -10036(r14)
          sw -4(r14), r1
          lw r15, -8(r14)
          jr r15

f4        sw -8(r14), r15
          lw r1, -12(r14)
          putc r1
          addi r2, r0, 0
          sw -10040(r14), r2
          lw r2, -10040(r14)
          lw r3, -12(r14)
          ceq r4, r2, r3
          sw -10044(r14), r4
      %processing ifStat
          lw r1, -10044(r14)
          bz r1, else1
          addi r3, r0, 0
          sw -10048(r14), r3
      %saving return value
          lw r2, -10048(r14)
          sw -4(r14), r2
          j endif1
else1
          addi r3, r0, 1
          sw -10052(r14), r3
          lw r3, -12(r14)
          lw r4, -10052(r14)
          sub r5, r3, r4
          sw -10056(r14), r5
      %loading function f4's parameter 0
          lw r3, -10056(r14)
          sw -24(r14), r3
          addi r14, r14, -12
          jl r15, f4
          subi r14, r14, -12
      %loading return value
          lw r3, -16(r14)
          sw -10060(r14), r3
      %saving return value
          lw r2, -10060(r14)
          sw -4(r14), r2
endif1
          lw r15, -8(r14)
          jr r15

          entry
          addi r14, r0, topaddr
          addi r1, r0, 3
          sw -10076(r14), r1
          addi r1, r0, 4
          sw -10080(r14), r1
          addi r14, r14, -56
          jl r15, f1
          subi r14, r14, -56
      %loading return value
          lw r1, -60(r14)
          sw -10084(r14), r1
      %processing assignStat x = f1
          lw r1, -10084(r14)
          sw -4(r14), r1
          lw r1, -4(r14)
          putc r1
          addi r1, r0, 1
          sw -10144(r14), r1
          addi r1, r0, 2
          sw -10148(r14), r1
          addi r1, r0, 22
          sw -10152(r14), r1
      %processing assignStat y = 22
          lw r1, -10152(r14)
          sw -32(r14), r1
          addi r1, r0, 1
          sw -10156(r14), r1
          addi r1, r0, 2
          sw -10160(r14), r1
      %loading function f3's parameter 0
          lw r1, -4(r14)
          sw -68(r14), r1
      %loading function f3's parameter 1
          lw r1, -32(r14)
          sw -72(r14), r1
          addi r14, r14, -56
          jl r15, f3
          subi r14, r14, -56
      %loading return value
          lw r1, -60(r14)
          sw -10164(r14), r1
      %processing assignStat z = f3
          lw r1, -10164(r14)
          sw -56(r14), r1
          lw r1, -56(r14)
          putc r1
          addi r14, r14, -56
          jl r15, fc
          subi r14, r14, -56
      %loading return value
          lw r1, -60(r14)
          sw -10224(r14), r1
          lw r1, -10224(r14)
          putc r1
          addi r1, r0, 3
          sw -10284(r14), r1
      %loading function f4's parameter 0
          lw r1, -10284(r14)
          sw -68(r14), r1
          addi r14, r14, -56
          jl r15, f4
          subi r14, r14, -56
      %loading return value
          lw r1, -60(r14)
          sw -10288(r14), r1
      %processing assignStat z = f4
          lw r1, -10288(r14)
          sw -56(r14), r1
          hlt
