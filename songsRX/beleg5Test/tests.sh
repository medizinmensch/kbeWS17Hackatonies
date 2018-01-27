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
# arg #2: songlist_id of private songlist that exists and belongs to ${userA}
# arg #3: token of ${userB}
# arg #4: songlist_id of public songlist that exists and belongs to ${userB}"
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
     -v "http://localhost:8080/songsRX/rest/userId/${userA}/songLists"
echo " "
echo "-----------------------------------------------------------------"

echo "GET a.2"
echo "--- <${userA}> REQUESTING all of <${userB}>'s songlists  (Should only show Public songlists) ------------"
curl -s \
	 -X GET \
	 -H "Authorization: $1" \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/userId/${userB}/songLists"
echo " "
echo "-----------------------------------------------------------------"

echo "GET b.1"
echo "--- <${userA}> REQUESTING one of <${userA}>'s private songlists  (Should work) ------------"
curl -s \
	 -X GET \
	 -H "Authorization: $1" \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/userId/${userA}/songLists/$2"
echo " "
echo "-----------------------------------------------------------------"

echo "GET b.2"
echo "--- <${userA}> REQUESTING one of <${userB}>'s public songlists  (Should work) ------------"
curl -s \
	 -X GET \
	 -H "Authorization: $1" \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/userId/${userB}/songLists/$4"
echo " "
echo "-----------------------------------------------------------------"


echo "POST c.1"
echo "--- SONGLIST WITHOUT TOKEN:	(Should be forbidden) ------------------"
curl -s \
	 -X POST \
     -H "Content-Type: application/json" \
     -H "Accept: text/plain" \
     -d "@publicSonglist.json" \
     -v "http://localhost:8080/songsRX/rest/userId/${userA}/songLists"
echo " "
echo "-----------------------------------------------------------------"

echo "POST c.2"
echo "--- PUBLIC SONGLIST: ------------------"
curl -s \
	 -X POST \
     -H "Authorization: $1" \
     -H "Content-Type: application/json" \
     -H "Accept: text/plain" \
     -d "@publicSonglist.json" \
     -v "http://localhost:8080/songsRX/rest/userId/${userA}/songLists"
echo " "
echo "-----------------------------------------------------------------"

echo "POST c.3"
echo "--- PRIVATE SONGLIST (JSON): ------------------"
curl -s \
	 -X POST \
     -H "Authorization: $1" \
     -H "Content-Type: application/json" \
     -H "Accept: text/plain" \
     -d "@privateSonglist.json" \
     -v "http://localhost:8080/songsRX/rest/userId/${userA}/songLists"
echo " "
echo "-----------------------------------------------------------------"


echo "POST c.4"
echo "--- PUBLIC SONGLIST (XML): ------------------"
curl -s \
	 -X POST \
     -H "Authorization: $1" \
     -H "Content-Type: application/xml" \
     -H "Accept: text/plain" \
     -d "@publicSonglist.xml" \
     -v "http://localhost:8080/songsRX/rest/userId/${userA}/songLists"
echo " "
echo "-----------------------------------------------------------------"

echo "POST c.5"
echo "--- PUBLIC SONGLIST (CONTAINING MISSING SONG): 	(Should be bad request) ------------------"
curl -s \
	 -X POST \
     -H "Authorization: $1" \
     -H "Content-Type: application/json" \
     -H "Accept: text/plain" \
     -d "@publicSonglistContainingMissingSongs.json" \
     -v "http://localhost:8080/songsRX/rest/userId/${userA}/songLists"
echo " "
echo "-----------------------------------------------------------------"

echo "DELETE d.1"
echo "--- SONGLIST OF ${userA}, ID: $2 (SHOULD WORK) --------"
curl -s \
	 -X DELETE \
	 -H "Authorization: $1" \
     -H "Accept: text/plain" \
     -v "http://localhost:8080/songsRX/rest/userId/${userA}/songLists/$2"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "DELETE d.2"
echo "--- SONGLIST OF ${userB}, ID: $4 (SHOULD BE FORBIDDEN)-------"
curl -s \
	 -X DELETE \
	 -H "Authorization: $1" \
     -H "Accept: text/plain" \
     -v "http://localhost:8080/songsRX/rest/userId/${userB}/songLists/$4"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "DELETE d.3"
echo "--- SONGLIST THAT DOES NOT EXIST, ID: 9042 	(SHOULD BE NOT FOUND)--------"
curl -s \
     -H "Authorization: $1" \
	 -X DELETE \
     -H "Accept: text/plain" \
     -v "http://localhost:8080/songsRX/rest/userId/${userA}/songLists/9042"
echo " "
echo "-------------------------------------------------------------------------------------------------"


echo "-------------------------------------------------------------------------------------------------
-----------------------------------------D-O-N-E-------------------------------------------------
-------------------------------------------------------------------------------------------------"
