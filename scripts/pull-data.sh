#! /bin/bash

# Package of the app for which you want to pull data
app_pkg=ddiehl.rxreddit.sample

echo "Pulling internal data for app: $app_pkg"

path=build/data
mkdir -p $path

adb backup -f $path/appdata.ab -noapk $app_pkg
cd $path \
    && dd if=appdata.ab bs=1 skip=24 \
        | python -c "import zlib,sys;sys.stdout.write(zlib.decompress(sys.stdin.read()))" \
        | tar -xvf - \
    && echo "Backup complete, opening directory with app data."; open .
