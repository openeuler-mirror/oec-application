# CPU平台与OpenEuler版本映射关系

此文档根据[OpenEuler兼容性列表](https://www.openeuler.org/zh/compatibility/)，简单对OpenEuler操作系统不同版本所支持的不同CPU代次、CPU型号进行总结。

### 版本支持情况

注：

*● 表示支持cpu代次*

*○ 表示第一个支持此cpu代次的版本*

| CPU厂商 | CPU代次 | 20.03 LTS | 20.03 LTS SP1 | 20.03 LTS SP3 | 22.03 LTS | 22.03 LTS SP1 |
| --- | --- | --- | --- | --- | --- | --- |
| intel | Icelake | ●   | ● ○ | ●   | ●   |     |
|     | Cascade | ●   | ●   | ●   | ●   | ●   |
|     | Cooperlake | ●   | ●   | ●   | ●   |     |
|     | Skylake |     | ●○  |     |     |     |
|     | Tigerlake |     |     |     | ●○  |     |
|     | Avoton |     |     | ●○  |     |     |
|     | Sapphire rapids |     |     |     |     | ●○  |
|     | Haswell |     |     | ●○  |     |     |
| AMD | AMD EPYC2 |     | ●   | ●   |     |     |
|     | AMD EPYC3 |     |     |     | ●   | ●   |
| 鲲鹏  | Kunpeng-920 | ●   | ●   | ●   | ●   | ●   |
|     | Kunpeng-916 |     |     | ●   | ●   |     |
| 海光  | Hygon 1 |     | ●○  |     |     |     |
|     | Hygon 2 |     | ●   | ●   | ●   | ●   |
|     | Hygon 3 |     |     |     | ●○  |     |
| 飞腾  | S2500 |     | ●   | ●   | ●   | ●   |
|     | FT2000+ |     | ●   | ●   | ●   | ●   |
| 兆芯  | KH-37800D |     |     |     | ●   |     |

### 各个版本对CPU代次支持方式

注：

*A：版本发行初自带特性。*

*B：后期补丁合入对应特性。*

*C：待定。*

| CPU厂商 | CPU代次 | 20.03 LTS | 20.03 LTS SP1 | 20.03 LTS SP3 | 22.03 LTS | 22.03 LTS SP1 |
| --- | --- | --- | --- | --- | --- | --- |
| intel | Icelake |     |     |     |     |     |
|     | Cascade |     |     |     |     |     |
|     | Cooperlake |     |     |     |     |     |
|     | Skylake |     |     |     |     |     |
|     | Tigerlake |     |     |     |     |     |
|     | Avoton |     |     |     |     |     |
|     | Sapphire rapids |     |     |     |     |     |
|     | Haswell |     |     |     |     |     |
| AMD | AMD EPYC2 |     |     |     |     |     |
|     | AMD EPYC3 |     |     |     |     |     |
| 鲲鹏  | Kunpeng-920 |     |     |     |     |     |
|     | Kunpeng-916 |     |     |     |     |     |
| 海光  | Hygon 1 |     |     |     |     |     |
|     | Hygon 2 |     |     |     |     |     |
|     | Hygon 3 |     |     |     |     |     |
| 飞腾  | S2500 |     |     |     |     |     |
|     | FT2000+ |     |     |     |     |     |
| 兆芯  | KH-37800D |     |     |     |     |     |

### 兼容性列表中CPU代次包含的CPU型号

注：

*此部分内容仅包含部分整机信息，并非兼容性列表中全部整机。*

*此部分仅记录某个CPU型号第一次出现在哪个OpenEuler版本中。*

| CPU代次 | 20.03 LTS | 20.03 LTS SP1 | 20.03 LTS SP3 | 22.03 LTS | 22.03 LTS SP1 |
| --- | --- | --- | --- | --- | --- |
| Icelake | Intel Xeon Scalable Gold 6346 | Intel(R) Xeon(R) Gold 6330 | Intel(R) Xeon(R) Platinum 8352V |     |     |
|     | Intel Xeon Platinum 8352V |     | Intel(R) Xeon(R) Gold 6354 CPU @ 3.00GHz |     |     |
|     |     |     | Intel(R) Xeon(R) Platinum 8378A |     |     |
|     |     |     | Intel(R) Xeon(R) Platinum 8380 CPU @ 2.30GHz |     |     |
| Cascade | Intel(R) Xeon(R) Gold 5218 | Intel(R) Xeon(R) Gold 6266C CPU @ 3.00GHz | Intel(R) Xeon(R) Gold 6346 CPU @ 3.10GHz |     |     |
|     | Intel(R) Xeon(R) Silver 4110 | Intel(R) Xeon(R) Gold 5220R CPU @ 2.20GHz | Intel(R) Xeon(R) Gold 5218R @ 2.10GHz |     |     |
|     | Intel(R) Xeon(R) Gold 5215 | Intel(R) Xeon(R) Gold 6130 CPU @ 2.10GHz |     |     |     |
|     | Intel(R) Xeon(R) Gold 5115 |     |     |     |     |
| Cooperlake | Intel(R) Xeon(R) Platinum 8356H | Intel(R) Xeon(R) Gold 5318H CPU @ 2.5GHz |     |     |     |
| Skylake |     | Intel(R) Xeon(R) Gold 5120 @ 2.20GHz |     |     |     |
| Tigerlake |     |     | Intel(R) Pentium(R) Gold 7505 @ 2.00GHz |     |     |
| Avoton |     |     | Intel(R) Atom(R) P5322 processor |     |     |
| Sapphire rapids |     |     |     |     | Intel(R) Xeon(R) Platinum 8480+ |
| Haswell |     |     | Intel(R) Xeon(R) CPU E5-2630 v3 @ 2.40GHz |     |     |
| AMD EPYC2 |     | AMD EPYC 7H12 64-Core Processor |     |     |     |
| AMD EPYC3 |     |     |     | AMD EPYC 7513 32-Core Processor |     |
| Kunpeng-920 | Kunpeng-920 |     |     |     |     |
| Kunpeng-916 | Kunpeng-916 |     |     |     |     |
| Hygon 1 |     | ZHAOXIN KaiSheng KH-37800D @ 2.70GHz |     |     |     |
| Hygon 2 |     | Hygon C86 7265 24-Core Processor | Hygon C86 7280 32-Core Processor |     |     |
|     |     |     | Hygon 2 C86 7265 |     |     |
| Hygon 3 |     |     |     | Hygon C86 7265 24-Core Processor |     |
|     |     |     |     | Hygon C86 5380 16-Core Processor |     |
|     |     |     |     | Hygon C86 7365 24-Core Processor |     |
|     |     |     |     | Hygon QS 7391 32C 2.9G 225W CPU |     |
| S2500 |     | S2500/64C |     |     |     |
| FT2000+ |     | FT2000+ |     |     |     |
| KH-37800D |     | ZHAOXIN KaiShang KH-37800D @ 2.7GHz |     |     |     |

### 后续维护

- 此表将随OpenEuler操作系统版本发布共同更新。
  
- 期间新增CPU代次及型号将根据具体情况合入此文档。