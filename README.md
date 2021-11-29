# ilp-benchmarks
A set of benchmarks to demonstrate the effects of memory layout on algorithm performance.

Time to lookup a single item in a list of 300
items. The measurement is provided by looking
up 300 random ids 100,000 times and choosing the
single fastest iteration.

|Language|Layout|Duration (ns)|
|--------|------|-------------|
| Java   | aor  | 34489       |
| Java   | soa  | 10031       |
| Java   | mor  | 2020        |
| C++    | aos  | 12026       |
| C++    | soa  | 11983       |
| C++    | mos  | 568         |
| Kotlin | aor  | 33271       |  
| Kotlin | soa  | 19659       |
| Kotlin | mor  | 1653        |
| C#     | aor  | 48768       |  
| C#     | soa  | 23430       |
| C#     | mor  | 2783        |

