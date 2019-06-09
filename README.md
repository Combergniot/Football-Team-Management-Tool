# Football-Team-Management-Tool

| API name |HTTP method| Path |Status code| Description |
|---| --- | --- | --- |--- |
| POST createNewTeam |POST| `/team` | 201 (Created)| A new Team resource is created|
| GET teamByTeamIdentifier |GET| `/team/{teamIdentifier}` | 200 (OK)| One Team resource is fetched |
| GET allTeam |GET| `/team/all` | 200 (OK)| All Team resources are fetched |
| PATCH updateTeamData |PATCH| `/team/{teamIdentifier}` | 200 (OK)| Team resource is updated |
| DELETE team |DELETE| `/team/{teamIdentifier}` | 204 (No content)| Team resource is deleted |
| POST addPlayerToSquad |POST| `/squad/{teamIdentifier}` | 201 (Created)| A new Player resource is created|
| GET teamSquad |GET| `/squad/{teamIdentifier}` | 200 (OK)| All Players in one Team resource are fetched |
| GET allPlayers |GET| `/squad/all` | 200 (OK)| All Player resources are fetched |
| GET playerByName |GET| `/squad/name={name}` | 200 (OK)| All Players resource with specified name are fetched |
| GET player |GET| `/squad/{teamIdentifier}/{player_id}` | 200 (OK)| One Player resource is fetched |
| PATCH updatePlayerData |PATCH| `/squad/{teamIdentifier}/{player_id}` | 200 (OK)| Player resource is updated |
| DELETE player |DELETE| `/squad/{teamIdentifier}/{player_id}` | 204 (No content)| Player resource is deleted |
