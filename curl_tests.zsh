#!/usr/bin/env zsh

# creates a user
createUser() {
  curl -ik -H "Content-Type: application/x-www-form-urlencoded" -XPOST https://localhost:8443/users/ -d "email=chris1" -d "password=blah"
}

# generates a token
createToken() {
  local token=$(curl -k -XPOST https://localhost:8443/token/ -d "email=chris1" -d "password=blah")
  echo "${token}"
}

# creates a game
createGame() {
  local token=$1
  curl -k -H "Authorization: Bearer ${token}" -XPOST https://localhost:8443/games/ | jq
}

# attempts to list a protected resource
listGames() {
  local token=$1
  curl -k -H "Authorization: Bearer ${token}" -XGET https://localhost:8443/games/ | jq
}

createUser
local token=$(createToken)
createGame ${token}
createGame ${token}
listGames ${token}

