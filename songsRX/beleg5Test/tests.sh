#! /bin/sh

# arg #1: token of ${userA}
# arg #2: songlist_id of songlist that exists and belongs to ${userA}
# arg #3: token of ${userB}
# arg #4: songlist_id of songlist that exists and belongs to ${userB}

userA="osailer"
userB="eschuler"

if [ -z "$1" ]
	then 
		echo "You need exactly 4 Arguments:
# arg #1: token of ${userA}
# arg #2: songlist_id of songlist that exists and belongs to ${userA}
# arg #3: token of ${userB}
# arg #4: songlist_id of songlist that exists and belongs to ${userB}"
		exit 1
	else
		echo " "
fi

echo "user A is $userA
user B is $userB

token of $userA must be $1
token of $userB must be $3

songlist_id of songlist that exists and belongs to ${userA}: $2
songlist_id of songlist that exists and belongs to ${userB}: $4

standard Accept-Header is json
"


echo "GET a.1"
echo "--- <${userA}> REQUESTING all of <${userA}>'s songlists  (Should show all songlists) ------------"
curl -s \
	 -X GET \
	 -H "Authorization: $1" \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/userId/${userA}/songlists"
echo " "
echo "-----------------------------------------------------------------"

echo "GET a.2"
echo "--- <${userA}> REQUESTING all of <${userB}>'s songlists  (Should only show Public songlists) ------------"
curl -s \
	 -X GET \
	 -H "Authorization: $1" \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/userId/${userB}/songlists"
echo " "
echo "-----------------------------------------------------------------"

echo "GET b.1"
echo "--- <${userA}> REQUESTING one of <${userA}>'s songlists  (Should work) ------------"
curl -s \
	 -X GET \
	 -H "Authorization: $1" \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/userId/${userA}/songlists/$4"
echo " "
echo "-----------------------------------------------------------------"

echo "GET b.2"
echo "--- <${userA}> REQUESTING one of <${userB}>'s songlists  (Should not work) ------------"
curl -s \
	 -X GET \
	 -H "Authorization: $1" \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/userId/${userB}/songlists/$4"
echo " "
echo "-----------------------------------------------------------------"


echo "POST c.1"
echo "---SONGLIST WITHOUT TOKEN:------------------"
curl -s \
	 -X POST \
     -H "Content-Type: application/json" \
     -H "Accept: text/plain" \
     -d "@publicSonglist.json" \
     -v "http://localhost:8080/songsRX/rest/userId/${userA}/songlists"
echo " "
echo "-----------------------------------------------------------------"

echo "POST c.2"
echo "---PUBLIC SONGLIST: ------------------"
curl -s \
	 -X POST \
     -H "Authorization: $1" \
     -H "Content-Type: application/json" \
     -H "Accept: text/plain" \
     -d "@publicSonglist.json" \
     -v "http://localhost:8080/songsRX/rest/userId/${userA}/songlists"
echo " "
echo "-----------------------------------------------------------------"

echo "POST c.3"
echo "---PRIVATE SONGLIST: ------------------"
curl -s \
	 -X POST \
     -H "Authorization: $1" \
     -H "Content-Type: application/json" \
     -H "Accept: text/plain" \
     -d "@privateSonglist.json" \
     -v "http://localhost:8080/songsRX/rest/userId/${userA}/songlists"
echo " "
echo "-----------------------------------------------------------------"


echo "POST c.4"
echo "---PUBLIC SONGLIST: (XML)------------------"
curl -s \
	 -X POST \
     -H "Authorization: $1" \
     -H "Content-Type: application/xml" \
     -H "Accept: text/plain" \
     -d "@publicSonglist.xml" \
     -v "http://localhost:8080/songsRX/rest/userId/${userA}/songlists"
echo " "
echo "-----------------------------------------------------------------"

echo "POST c.5"
echo "---PUBLIC SONGLIST, CONTAINING MISSING SONG: ------------------"
curl -s \
	 -X POST \
     -H "Authorization: $1" \
     -H "Content-Type: application/json" \
     -H "Accept: text/plain" \
     -d "@publicSonglistContainingMissingSongs.json" \
     -v "http://localhost:8080/songsRX/rest/userId/${userA}/songlists"
echo " "
echo "-----------------------------------------------------------------"

echo "DELETE d.1"
echo "--- SONGLIST OF ${userA}, ID: $2 (SHOULD WORK) --------"
curl -s \
	 -X DELETE \
     -H "Accept: text/plain" \
     -v "http://localhost:8080/songsRX/rest/userId/${userA}/songlists/$2"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "DELETE d.2"
echo "--- SONGLIST OF ${userB}, ID: $2 (SHOULD NOT WORK)--------"
curl -s \
	 -X DELETE \
     -H "Accept: text/plain" \
     -v "http://localhost:8080/songsRX/rest/userId/${userB}/songlists/$2"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "DELETE d.3"
echo "--- SONGLIST THAT DOES NOT EXIST, ID: 99942 --------"
curl -s \
	 -X DELETE \
     -H "Accept: text/plain" \
     -v "http://localhost:8080/songsRX/rest/userId/${userA}/songlists/9942"
echo " "
echo "-------------------------------------------------------------------------------------------------"


echo "-------------------------------------------------------------------------------------------------
-----------------------------------------D-O-N-E-------------------------------------------------
-------------------------------------------------------------------------------------------------"
