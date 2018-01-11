#! /bin/sh

echo "--- REQUESTING A TOKEN ------------"
curl -X GET \
     -v "http://localhost:8080/songsRX/rest/auth?userId=eschuler"
echo " "
echo "-------------------------------------------------------------------------------------------------"

