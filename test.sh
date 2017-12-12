#!/usr/bin/env bash
set -x
set -e

trap "{ pkill Xvfb && pkill chrome || true; }" EXIT

TESTS_TO_EXECUTE=$1
if [ -z "$1" ]
  then
    TESTS_TO_EXECUTE="*"
fi

START_TIME=$SECONDS
sudo pkill chrome || true


export DISPLAY=:99
export DBUS_SESSION_BUS_ADDRESS=/dev/null

rm -fr target/screenshots



ELAPSED_TIME=$(($SECONDS - $START_TIME))
echo "TOTAL TEST TIME: $ELAPSED_TIME seconds"


exit 0
