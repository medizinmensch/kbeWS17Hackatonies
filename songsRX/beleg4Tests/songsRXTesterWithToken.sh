#! /bin/sh
#
# Usage: ./songsRXTesterWithToken.sh 4c87dubofnfrheesom6t0833qa
#
# 4c87dubofnfrheesom6t0833qa - example of a token
#

echo "--- REQUESTING ALL JSON SONGS WITH TOKEN: ------------"
curl -X GET \
     -H "Authorization: $1" \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/songs"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING SONG 82 IN XML WITH TOKEN: ------------"
curl -X GET \
     -H "Authorization: $1" \
     -H "Accept: application/xml" \
     -v "http://localhost:8080/songsRX/rest/songs/82"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING SONG WITHOUT TOKEN SHOULD RETURN 401--------"
curl -X GET \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/songs/11"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- POSTING A JSON SONG WITH TOKEN:------------------"
curl -X POST \
     -H "Authorization: $1" \
     -H "Content-Type: application/json" \
     -H "Accept: text/plain" \
     -d "@einSong.json" \
     -v "http://localhost:8080/songsRX/rest/songs"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- POSTING A XML SONG WITHOUT TOKEN SHOULD RETURN 401 ------------------"
curl -X POST \
     -H "Content-Type: application/xml" \
     -H "Accept: text/plain" \
     -d "@einSong.xml" \
     -v "http://localhost:8080/songsRX/rest/songs"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- UPDATING JSON-SONG 9 WITH TOKEN ------------------"
curl -X PUT \
     -H "Authorization: $1" \
     -H "Content-Type: application/json" \
     -H "Accept: text/plain" \
     -d "@updatedSong.json" \
     -v "http://localhost:8080/songsRX/rest/songs/9"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- UPDATING XML-SONG WITHOUT TOKEN SHOULD RETURN 401------------------"
curl -X PUT \
     -H "Content-Type: application/xml" \
     -H "Accept: text/plain" \
     -d "@updatedSong.xml" \
     -v "http://localhost:8080/songsRX/rest/songs/10"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- DELETING SONG 82 WITH TOKEN--------"
curl -X DELETE \
     -H "Authorization: $1" \
     -H "Accept: text/plain" \
     -v "http://localhost:8080/songsRX/rest/songs/82"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- DELETING WITHOUT TOKEN SHOULD RETURN 401 --------"
curl -X DELETE \
     -H "Accept: text/plain" \
     -v "http://localhost:8080/songsRX/rest/songs/82"
echo " "
echo "-------------------------------------------------------------------------------------------------"

