#!/bin/bash
cd /mnt/c/dev/repo/dictionary-test/out/production/dictionary-test/
java -Ddict.filepath=/mnt/c/dev/repo/dictionary-test/data/english5.txt CountAlphaMapAsync $1
cd -
