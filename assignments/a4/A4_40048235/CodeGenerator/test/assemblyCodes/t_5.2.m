
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

          entry
          addi r14, r0, topaddr
          addi r1, r0, 3
          sw -10040(r14), r1
          addi r1, r0, 4
          sw -10044(r14), r1
          addi r14, r14, -56
          jl r15, f1
          subi r14, r14, -56
      %loading return value
          lw r1, -60(r14)
          sw -10048(r14), r1
      %processing assignStat x = f1
          lw r1, -10048(r14)
          sw -4(r14), r1
          lw r1, -4(r14)
          putc r1
          addi r1, r0, 1
          sw -10108(r14), r1
          addi r1, r0, 2
          sw -10112(r14), r1
          addi r1, r0, 22
          sw -10116(r14), r1
      %processing assignStat y = 22
          lw r1, -10116(r14)
          sw -32(r14), r1
          addi r1, r0, 1
          sw -10120(r14), r1
          addi r1, r0, 2
          sw -10124(r14), r1
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
          sw -10128(r14), r1
      %processing assignStat z = f3
          lw r1, -10128(r14)
          sw -56(r14), r1
          lw r1, -56(r14)
          putc r1
          addi r14, r14, -56
          jl r15, fc
          subi r14, r14, -56
      %loading return value
          lw r1, -60(r14)
          sw -10188(r14), r1
          lw r1, -10188(r14)
          putc r1
          hlt
