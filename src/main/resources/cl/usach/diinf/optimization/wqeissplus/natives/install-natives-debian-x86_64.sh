#!/bin/bash

sudo apt-get update
sudo apt-get install lib64gfortran4 lib64gfortran5 lib64quadmath0 libgfortran4 libgfortran5 libquadmath0 libmkl-rt

su -c "cp -fv "$(dirname "$0")"/libgfortran.so.3 /usr/lib/x86_64-linux-gnu" root
ldconfig

exit 0
