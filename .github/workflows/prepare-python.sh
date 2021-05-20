#!/usr/bin/env bash

set -e

python -m pip install --upgrade pip
python -m pip install jep

PYTHONHOME="$(dirname "$(dirname "$(which python)")")"
echo PYTHONHOME="$PYTHONHOME" >> "$GITHUB_ENV"
echo PYTHONHOME="$PYTHONHOME"