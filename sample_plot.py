import numpy as np
import matplotlib.pyplot as plt
import matplotlib.dates as mdates

x, y = np.loadtxt("out_process.txt", unpack=True)

fig = plt.figure()

ax1 = fig.add_subplot(111)

ax1.set_title("Throughput")
ax1.set_xlabel('Elapsed Time')
ax1.set_ylabel('Throughput')

ax1.plot(x,y, c='r', label='the data')

leg = ax1.legend()

plt.show()
