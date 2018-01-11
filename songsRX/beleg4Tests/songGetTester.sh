#! /bin/sh

#
# Usage: ./songGetTester.sh 4c87dubofnfrheesom6t0833qa
#
# 4c87dubofnfrheesom6t0833qa - example of a token

echo "--- REQUESTING ALL JSON SONGS ------------"
curl -X GET \
     -H "Authorization: $1" \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/songs"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING ALL XML SONGS--------"
curl -X GET \
     -H "Authorization: $1" \
     -H "Accept: application/xml" \
     -v "http://localhost:8080/songsRX/rest/songs"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING SONG 82 IN XML--------"
curl -X GET \
     -H "Authorization: $1" \
     -H "Accept: application/xml" \
     -v "http://localhost:8080/songsRX/rest/songs/82"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING SONG 37 IN JSON WITHOUT A TOKEN SHOULD RETURN 401--------"
curl -X GET \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/songs/37"
echo " "
echo "-------------------------------------------------------------------------------------------------"
