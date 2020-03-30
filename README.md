## HTTP Bench

Simple HTTP ping-pong benchmark

## Results

```
Client sig: apache
Server sig: undertow
  Memory: 16Mb
  Threads: 10
  Done: 200000
  RPS: 19929.2
  Elapsed: 10s

  Mean 50: 0.18ms
  Mean 90: 0.31ms
  Mean 95: 0.38ms
  Mean 98: 0.43ms
  Mean 99: 0.46ms

Client sig: apache
Server sig: undertow
  Memory: 256Mb
  Threads: 10
  Done: 200000
  RPS: 33218.6
  Elapsed: 6s

  Mean 50: 0.18ms
  Mean 90: 0.23ms
  Mean 95: 0.25ms
  Mean 98: 0.27ms
  Mean 99: 0.28ms

Client sig: google
Server sig: undertow
  Memory: 16Mb
  Threads: 10
  Done: 200000
  RPS: 5380.8
  Elapsed: 37s

  Mean 50: 1.48ms
  Mean 90: 1.73ms
  Mean 95: 1.78ms
  Mean 98: 1.82ms
  Mean 99: 1.84ms

Client sig: google
Server sig: undertow
  Memory: 256Mb
  Threads: 10
  Done: 200000
  RPS: 6031.8
  Elapsed: 33s

  Mean 50: 1.44ms
  Mean 90: 1.56ms
  Mean 95: 1.59ms
  Mean 98: 1.60ms
  Mean 99: 1.61ms

Client sig: j11
Server sig: undertow
  Memory: 16Mb
  Threads: 10
  Done: 200000
  RPS: 9484.5
  Elapsed: 21s

  Mean 50: 0.34ms
  Mean 90: 0.58ms
  Mean 95: 0.69ms
  Mean 98: 0.81ms
  Mean 99: 0.88ms

Client sig: j11
Server sig: undertow
  Memory: 256Mb
  Threads: 10
  Done: 200000
  RPS: 14229.4
  Elapsed: 14s

  Mean 50: 0.34ms
  Mean 90: 0.42ms
  Mean 95: 0.47ms
  Mean 98: 0.54ms
  Mean 99: 0.58ms

Client sig: j11_2_0
Server sig: undertow
  Memory: 256Mb
  Threads: 10
  Done: 200000
  RPS: 13294.1
  Elapsed: 15s

  Mean 50: 0.36ms
  Mean 90: 0.44ms
  Mean 95: 0.48ms
  Mean 98: 0.54ms
  Mean 99: 0.58ms
```