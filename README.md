-----------------------------------------
-----------About-------------------------
-----------------------------------------
In ferromagnetic materials, neighboring dipoles tend to orient themselves based on the orientation of their neighbors. A ferromagnet can be modeled as being made up of many dipoles in a lattice, with each dipole being able to point 'up' or 'down'. In a system composed of just two dipoles, dipoles that are pointing opposite directions (antiparallel) have an energy ε, and dipoles that are pointing in the same direction (parallel) have an energy -ε. It is thus energetically favorable for the two dipoles to line up parallel to each other. This model of a ferromagnet, an array of dipoles that can only point up or down, is called the Ising model. The Ising model can be used, with the help of a computer, to calculate the expected energy, magnetization, and other thermodynamic properties of a ferromagnet.

This program begins by generating a two-dimensional array of dipoles of random orientation. It then picks a dipole in the array at random, and computes the change in energy, dU, that would result if the dipole were flipped. Only the neighbors immediately left, right, above, and below the dipole are considered. If the flip would decrease the energy of the system (that is, if dU is less than or equal to zero), then the program flips the dipole. Otherwise, the probability of a flip is determined by the Boltzmann factor exp[-dU/T], where T has units of ε/K, K being the Boltzmann constant (There is no need to worry about normalizing the probability, as the normalization constant (i.e., the partition function) cancels out; the probability is a ratio of the two transition probabilities). This is one iteration of the simulation. The program then moves on to another random dipole, up until a specified number of iterations have occurred. This algorithm is called the Metropolis algorithm (also called Monte Carlo summation with importance sampling).

By default, the program will generate an image of the array after 500000 iterations have occurred, although this can be easily changed in the source code and recompiling. The side length of the array can also be changed by modifying the source code. The images aren't too large; 1 billion iterations will produce about 5MB of images.


-----------------------------------------
-----------Running-----------------------
-----------------------------------------
To run the simulation, go to the command line, enter the directory where Ising.jar is located, and enter

java -jar Ising.jar [number of iterations] [temperature]

where the number of iterations is an integer number and the temperature is in units of ε/K. The temperature can be a decimal number, e.g., 2.27.


-----------------------------------------
-----------Further-reading---------------
-----------------------------------------
Daniel V. Schroeder, An Introduction to Thermal Physics. Addison Wesley, 1999.
