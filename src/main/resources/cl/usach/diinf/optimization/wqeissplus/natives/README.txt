*** WARNING ***
If you want to get full performance from WQEISSPLUS_<X> MOEA Framework Problem evaluation,
you have to install a high-performance BLAS / ARPACK / LAPACK implementation.

BLAS / ARPACK / LAPACK are well-defined libs for fast Matrix manipulation, including Linear solvers.

For reference: 

https://en.wikipedia.org/wiki/Basic_Linear_Algebra_Subprograms

As a helper, in this JAR, 
1) Intel Math Kernel Library installation script for Linux x86_64 (Debian/Ubuntu) is included, along with libfortran.so.3 dependency.
2) Intel Math Kernel Library binaries for Windows are included. They *should* be loaded out-of-the-box.

Intel Math Kernel Library (Intel MKL) implementation uses **full Hardware acceleration** (Intel CPUs only).

Other notable implementations (not tested): 

AMD Core Math Library: https://en.wikipedia.org/wiki/AMD_Core_Math_Library
OpenBLAS: https://en.wikipedia.org/wiki/OpenBLAS

*** WARNING ***