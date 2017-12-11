#!/usr/bin/env bash
set -x
set -e
#env

trap "{ pkill Xvfb && pkill chrome || true; }" EXIT

TESTS_TO_EXECUTE=$1
if [ -z "$1" ]
  then
    TESTS_TO_EXECUTE="*"
fi

START_TIME=$SECONDS
sudo pkill chrome || true
#sudo pkill -9 startx || true

#sudo startx &
export DISPLAY=:99
export DBUS_SESSION_BUS_ADDRESS=/dev/null
#Xvfb :99 -screen 0 1280x800x16 &
#./sbt -Dconfig.resource=acceptance.conf "testOnly $TESTS_TO_EXECUTE"
rm -fr target/screenshots
xvfb-run --server-arg="-screen 0, 1024x800x24" ./sbt -Dconfig.resource=base.conf -Dbrowser.useRemoteDriver=false "test-Only $TESTS_TO_EXECUTE" \
|| xvfb-run --server-arg="-screen 0, 1024x800x24" ./sbt -Dconfig.resource=base.conf  -Dbrowser.useRemoteDriver=false "testQuick $TESTS_TO_EXECUTE"
#xvfb-run --server-arg="-screen 0, 1440x900x16" ./sbt -Dconfig.resource=acceptance.conf "testOnly $TESTS_TO_EXECUTE"


ELAPSED_TIME=$(($SECONDS - $START_TIME))
echo "TOTAL TEST TIME: $ELAPSED_TIME seconds"


exit 0
