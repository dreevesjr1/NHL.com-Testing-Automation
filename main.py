from fastapi import FastAPI
from models import Player

app = FastAPI()

players = []

@app.get("/")
def home():
    return {"message": "NHL Stats API running"}

@app.post("/players")
def add_player(player: Player):
    if player.gp < 0:
        return{"error":"Games played cannot be null"}
    players.append(player)
    return{"message": "Player added", "player": player}

#json conversion
@app.get("/players")
def get_players():
    return [p.dict() for p in players]

@app.get("\players")
def get_player(name:str):
    for p in players:
        if p.name.lower() == name.lower():
            return p
    return {"error":"Player not found"}
