from pydantic import BaseModel

class Player(BaseModel):
    name: str
    team: str
    gp: int
    goals: int
    assists: int